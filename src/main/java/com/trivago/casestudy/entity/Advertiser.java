package com.trivago.casestudy.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Advertiser {
    @Id
    private Long id;
    private String name;

    @JsonProperty("accommodation")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "advertiser_id")
    private List<Accommodation> accommodations;

    public void setAccommodations(List<Accommodation> accommodations) {
        this.accommodations = accommodations;
        if (accommodations != null) {
            for (Accommodation accommodation : accommodations) {
                accommodation.setAdvertiserId(this.id);
            }
        }
    }
}
