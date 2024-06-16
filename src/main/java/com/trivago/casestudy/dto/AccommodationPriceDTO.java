package com.trivago.casestudy.dto;

import com.trivago.casestudy.constant.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccommodationPriceDTO {
    private Long id;
    private Long partnerId;
    private String partner;
    private Double price;
    private Currency currency;
}
