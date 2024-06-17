package com.trivago.casestudy.entity;

import com.trivago.casestudy.entity.key.composite.AccommodationId;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@IdClass(AccommodationId.class)
public class Accommodation {
    @Id
    private Long id;

    @Id
    @Column(name = "partner_id")
    private Long partnerId;

    @ElementCollection
    @CollectionTable(name = "items", joinColumns = {
            @JoinColumn(name = "partner_id", referencedColumnName = "partner_id"),
            @JoinColumn(name = "id", referencedColumnName = "id")
    })
    private List<Price> prices;
}