package com.trivago.casestudy.repository;

import com.trivago.casestudy.entity.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertiserRepository extends JpaRepository<Advertiser, Long> {

}
