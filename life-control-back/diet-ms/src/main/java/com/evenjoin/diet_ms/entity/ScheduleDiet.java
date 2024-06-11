package com.evenjoin.diet_ms.entity;

import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "schedule_diet")
public class ScheduleDiet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_schedule_diet")
	private Long idScheduleDite;
	
	@Column(nullable = false, unique = true)
	private LocalTime time;
	
	@Column(name = "is_checked", nullable = false)
	private Boolean isChecked;
	
	@ManyToOne
	@JoinColumn(name = "diet", nullable = false)
	private Diet diet;
	
	@ManyToOne
	@JoinColumn(name = "recipe", nullable = false)
	private Recipe recipe;
	
}
