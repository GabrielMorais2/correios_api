package br.com.gm.correios;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.gm.correios.model.Address;
import br.com.gm.correios.service.CorreiosService;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpStatusCode;
import org.mockserver.springtest.MockServerTest;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@MockServerTest({"correios.base.url=http://localhost:${mockServerPort}/ceps.csv"})
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class CorreiosApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private MockServerClient mockServerClient;

	@Autowired
	private CorreiosService correiosService;

	@Test
	@Order(1)
	public void testGetZipCodeWhenNotReady() throws Exception {
		mockMvc.perform(get("/zipcode/53150590")).andExpect(status().isServiceUnavailable());
	}
	@Test
	@Order(2)
	public void testSetupNotOk() throws Exception {
		mockServerClient.when(request()
						.withPath("/ceps.csv")
						.withMethod("GET"))
				.respond(response()
						.withStatusCode(500)
						.withBody("ERROR"));

		assertThrows(Exception.class, () -> correiosService.setup());
	}
	@Test
	@Order(3)
	public void testSetupOk() throws Exception {
		String bodyStr = "PE,Olinda,Rio Doce,53150590,Avenida Jules Rimet,,,,,,,,,,";

		mockServerClient.when(request()
				.withPath("/ceps.csv")
				.withMethod("GET"))
		.respond(response()
				.withStatusCode(200)
				.withBody(bodyStr));

		correiosService.setup();
	}
	@Test
	@Order(4)
	public void testGetZipcodeThetDoesentExist() throws Exception {
		mockMvc.perform(get("/zipcode/99999999")).andExpect(status().isNoContent());
	}
	@Test
	@Order(5)
	public void testGetZipcodeOk() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/zipcode/53150590")).andExpect(status().isOk()).andReturn();
		String resultStr = mvcResult.getResponse().getContentAsString();

		String addressTocompareStr = new ObjectMapper().writeValueAsString(
						Address.builder()
							.city("Olinda")
							.district("Rio Doce")
							.state("PE")
							.street("Avenida Jules Rimet")
							.zipcode("53150590")
							.build());



		JSONAssert.assertEquals(addressTocompareStr, resultStr, false);

	}

}
