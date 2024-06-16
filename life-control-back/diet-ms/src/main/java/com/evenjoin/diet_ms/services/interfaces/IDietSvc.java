package com.evenjoin.diet_ms.services.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.evenjoin.diet_ms.entity.Diet;

public interface IDietSvc {

	public List<Diet> getDiets();
	public Diet getDiet(Long idDiet);
	public Diet addDiet(Diet diet);
	public void deleteDiet(Long idDiet);
	public BigDecimal getPriceByDiet(Long idDiet);
	public BigDecimal getPriceByDietRange(Date startDate, Date endDate);
	public List<Object> getPricesByDietRange(Date startDate, Date endDate);
	public Integer getTimeByDiet(Long idDiet);
}
