package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evenjoin.diet_ms.entity.ScheduleDiet;

public interface ScheduleDietRepo extends JpaRepository<ScheduleDiet, Long> {
    
}
