package com.trivago.casestudy.repository;

import com.trivago.casestudy.entity.Accommodation;
import com.trivago.casestudy.entity.key.composite.AccommodationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, AccommodationId>, AccommodationRepositoryCustom {

    @Query("SELECT a FROM Accommodation a WHERE a.id = :id")
    List<Accommodation> findAllByAccommodationId(@Param("id") Long id);

}
