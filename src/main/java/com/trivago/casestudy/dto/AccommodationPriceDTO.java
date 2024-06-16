package com.trivago.casestudy.dto;

import com.trivago.casestudy.constant.Currency;
import com.trivago.casestudy.entity.Price;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccommodationPriceDTO {
    private Long partnerId;
    private Collection<Price> prices;
}
