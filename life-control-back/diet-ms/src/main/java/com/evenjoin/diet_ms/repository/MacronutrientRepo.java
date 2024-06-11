package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evenjoin.diet_ms.entity.Macronutrient;

public interface MacronutrientRepo extends JpaRepository<Macronutrient, Long> {

}
