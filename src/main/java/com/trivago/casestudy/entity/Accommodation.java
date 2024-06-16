package com.trivago.casestudy.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "item_prices", joinColumns = @JoinColumn(name = "item_id"))
    private List<Price> prices;
}