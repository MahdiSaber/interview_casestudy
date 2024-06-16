package com.trivago.casestudy.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.trivago.casestudy.configuration.PriceDeserializer;
import com.trivago.casestudy.constant.Currency;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Embeddable
public class Price {
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @JsonDeserialize(using = PriceDeserializer.class)
    private Double price;
}