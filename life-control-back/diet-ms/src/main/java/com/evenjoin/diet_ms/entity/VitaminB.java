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
@Table(name = "vitamin_b")
public class VitaminB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vitamin_b")
    private Long idVitaminB;

    @Column(name = "vitamin_b1", nullable = false, precision = 10, scale = 2)
    private BigDecimal vitaminB1;

    @Column(name = "vitamin_b2", nullable = false, precision = 10, scale = 2)
    private BigDecimal vitaminB2;

    @Column(name = "vitamin_b3", nullable = false, precision = 10, scale = 2)
    private BigDecimal vitaminB3;

    @Column(name = "vitamin_b4", nullable = false, precision = 10, scale = 2)
    private BigDecimal vitaminB4;

    @Column(name = "vitamin_b5", nullable = false, precision = 10, scale = 2)
    private BigDecimal vitaminB5;

    @Column(name = "vitamin_b6", nullable = false, precision = 10, scale = 2)
    private BigDecimal vitaminB6;

    @Column(name = "vitamin_b7", nullable = false, precision = 10, scale = 2)
    private BigDecimal vitaminB7;

    @Column(name = "vitamin_b8", nullable = false, precision = 10, scale = 2)
    private BigDecimal vitaminB8;

    @Column(name = "vitamin_b9", nullable = false, precision = 10, scale = 2)
    private BigDecimal vitaminB9;

    @Column(name = "vitamin_b12", nullable = false, precision = 10, scale = 2)
    private BigDecimal vitaminB12;

}
