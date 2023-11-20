package com.personal.performance.personal.service;

import java.util.List;
import java.util.Optional;

import com.personal.performance.personal.entity.CalisanEntity;

public interface CalisanService {
	
	public Optional<CalisanEntity> getCalisanById(Long calisanId);
	
	public List<CalisanEntity> getAllCalisan();

}
