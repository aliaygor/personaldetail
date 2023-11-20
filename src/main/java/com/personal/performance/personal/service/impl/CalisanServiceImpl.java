package com.personal.performance.personal.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.performance.personal.entity.CalisanEntity;
import com.personal.performance.personal.repository.CalisanRepository;
import com.personal.performance.personal.service.CalisanService;

@Service
public class CalisanServiceImpl implements CalisanService{
	
	@Autowired
	private CalisanRepository calisanRepository;

	@Override
	public Optional<CalisanEntity> getCalisanById(Long calisanId) {
		return this.calisanRepository.findById(calisanId);
	}

	@Override
	public List<CalisanEntity> getAllCalisan() {
		return this.calisanRepository.findAll();
	}
	


}
