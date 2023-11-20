package com.personal.performance.personal.service;

import java.util.List;
import java.util.Optional;

import com.personal.performance.personal.entity.HaftalarEntity;

public interface HaftalarService {
	
	public Optional<HaftalarEntity> getHaftaById(Long haftaId);
	
	public List<HaftalarEntity> getAllHaftalar();

}
