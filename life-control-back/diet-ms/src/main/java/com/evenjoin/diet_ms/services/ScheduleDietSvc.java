package com.evenjoin.diet_ms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evenjoin.diet_ms.entity.ScheduleDiet;
import com.evenjoin.diet_ms.repository.ScheduleDietRepo;
import com.evenjoin.diet_ms.services.interfaces.IScheduleDietSvc;

@Service
public class ScheduleDietSvc implements IScheduleDietSvc {

    @Autowired
    private ScheduleDietRepo scheduleDietRepo;

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDiet> getScheduleDiets() {
        return scheduleDietRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleDiet getScheduleDiet(Long idScheduleDiet) {
        return scheduleDietRepo.findById(idScheduleDiet).orElse(null);
    }

    @Override
    @Transactional
    public ScheduleDiet addScheduleDiet(ScheduleDiet scheduleDiet) {
        return scheduleDietRepo.save(scheduleDiet);
    }

    @Override
    @Transactional
    public void deleteScheduleDiet(Long idScheduleDiet) {
        scheduleDietRepo.deleteById(idScheduleDiet);
    }
    
}
