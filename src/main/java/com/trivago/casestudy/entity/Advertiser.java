package com.trivago.casestudy.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Advertiser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "advertiser_id")
    private List<Item> accommodations;
}
