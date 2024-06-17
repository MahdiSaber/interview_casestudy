package com.trivago.casestudy.repository;

import com.trivago.casestudy.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertiserRepository extends JpaRepository<Partner, Long> {

}
