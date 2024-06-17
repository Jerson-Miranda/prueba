package com.evenjoin.diet_ms.services.interfaces;

import java.util.List;
import com.evenjoin.diet_ms.entity.ScheduleDiet;

public interface IScheduleDietSvc {
    
    public List<ScheduleDiet> getScheduleDiets();
	public ScheduleDiet getScheduleDiet(Long idScheduleDiet);
	public ScheduleDiet addScheduleDiet(ScheduleDiet scheduleDiet);
	public void deleteScheduleDiet(Long idScheduleDiet);
}
