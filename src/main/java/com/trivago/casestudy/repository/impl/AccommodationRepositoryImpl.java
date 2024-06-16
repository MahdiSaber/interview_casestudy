package com.trivago.casestudy.repository.impl;

import com.trivago.casestudy.repository.AccommodationRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AccommodationRepositoryImpl implements AccommodationRepositoryCustom {
    private final EntityManager entityManager;

    @Autowired
    public AccommodationRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
