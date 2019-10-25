package com.zwirownia.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CennikCurrencyDto {
    private String rodzajTowaruNazwa;
    private Integer cena;
    private double currencyCena;
}
