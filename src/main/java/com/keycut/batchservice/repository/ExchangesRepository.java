package com.keycut.batchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keycut.batchservice.entity.ExchangesEntity;

@Repository
public interface ExchangesRepository extends JpaRepository<ExchangesEntity, Integer> {

}
