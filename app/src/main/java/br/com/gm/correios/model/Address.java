package br.com.gm.correios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
    public static final Long DEFAULT_ID = 1L;
    @Id
    private String zipcode;
    private String street;
    private String district;
    private String city;
    private String state;

}
