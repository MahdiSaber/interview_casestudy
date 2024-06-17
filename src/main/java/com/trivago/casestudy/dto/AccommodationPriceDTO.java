package com.trivago.casestudy.dto;

import com.trivago.casestudy.entity.Price;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class AccommodationPriceDTO {
    private Long partnerId;
    private Collection<Price> prices;
}
