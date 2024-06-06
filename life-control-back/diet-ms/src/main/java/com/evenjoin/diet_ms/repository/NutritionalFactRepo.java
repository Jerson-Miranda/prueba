package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evenjoin.diet_ms.entity.NutritionalFact;

public interface NutritionalFactRepo extends JpaRepository<NutritionalFact, Long> {

}
