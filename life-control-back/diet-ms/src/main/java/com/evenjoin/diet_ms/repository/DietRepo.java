package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evenjoin.diet_ms.entity.Diet;

public interface DietRepo extends JpaRepository<Diet, Long> {

}
