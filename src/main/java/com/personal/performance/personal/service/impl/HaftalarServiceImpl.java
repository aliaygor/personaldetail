package com.personal.performance.personal.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.performance.personal.entity.HaftalarEntity;
import com.personal.performance.personal.repository.HaftalarRepository;
import com.personal.performance.personal.service.HaftalarService;

@Service
public class HaftalarServiceImpl implements HaftalarService{
	
	@Autowired
	private HaftalarRepository haftalarRepository;
	
	@Override
	public Optional<HaftalarEntity> getHaftaById(Long haftaId) {
		return this.haftalarRepository.findById(haftaId);
	}

	@Override
	public List<HaftalarEntity> getAllHaftalar() {
		return this.haftalarRepository.findAll();
	}

}
