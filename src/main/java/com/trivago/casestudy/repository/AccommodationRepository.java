package com.trivago.casestudy.repository;

import com.trivago.casestudy.entity.Accommodation;
import com.trivago.casestudy.entity.key.composite.AccommodationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, AccommodationId> {
}
