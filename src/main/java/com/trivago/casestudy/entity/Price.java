package com.trivago.casestudy.entity;

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
    private Double price;
}