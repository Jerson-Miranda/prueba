package com.evenjoin.diet_ms.entity;

import java.util.Date;

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
@Table(name = "diet")
public class Diet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_diet")
	private Long idDite;
	
	@Column(nullable = false, unique = true)
	private Date date;
	
	@Column(name = "time_minute", nullable = false)
	private int timeMinute;
	
}
