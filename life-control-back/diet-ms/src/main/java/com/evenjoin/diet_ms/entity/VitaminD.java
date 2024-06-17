package com.evenjoin.diet_ms.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vitamin_d")
public class VitaminD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vitamin_d")
    private Long idVitaminD;

    @Column(name = "vitamin_d2", nullable = false, precision = 10, scale = 2)
    private BigDecimal vitaminD2;

    @Column(name = "vitamin_d3", nullable = false, precision = 10, scale = 2)
    private BigDecimal vitaminD3;
    
}
