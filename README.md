# Sobre o projeto

É uma API Rest para buscar qualquer cep Brasileiro, ao subir o container, é baixado um csv com 900k de endereços e salva na tabela 'correios.address' dentro do MariaDB.

# Testes

100% coberto por testes de integrações, utilizando:

* org.mock-server
* com.h2databas
 
 # Tecnologias
 
* Java 17
* Spring Boot 3
* Maven
* MariaDB
* Hibernate
* Lombok
* Docker Compose

# Compilar e Testar

Faça o clone do projeto e execute no terminal: docker-compose up

Importante: Ao buildar e subir a API, ela pode demora de 5 a 10 minutos para baixar todos os CEPs e inserir no MariaDB. Enquato o setup não finalizar, você irá receber um erro 503, o setup só estará finalizado quando você receber no terminal um SERVICE READY.

# Exemplos de Teste

503 Service Unavailable - http://localhost:9091/zipcode/53150590

![WhatsApp Image 2023-01-28 at 18 50 16](https://user-images.githubusercontent.com/68476116/215292836-d29752a9-50d9-4331-8a3e-9d872ab40cde.jpeg)

200 OK - http://localhost:9091/zipcode/53150590

![WhatsApp Image 2023-01-28 at 18 50 47](https://user-images.githubusercontent.com/68476116/215292859-ccc15747-7aab-485a-b3d4-bddba5183838.jpeg)



