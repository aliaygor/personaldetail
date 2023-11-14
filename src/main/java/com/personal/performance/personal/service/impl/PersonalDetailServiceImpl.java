package com.personal.performance.personal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.performance.personal.entity.PersonalDetailEntity;
import com.personal.performance.personal.repository.PersonalDetailRepository;
import com.personal.performance.personal.service.PersonalDetailService;

@Service
public class PersonalDetailServiceImpl implements PersonalDetailService{
	
	@Autowired
	private PersonalDetailRepository personalDetailRepository;

	@Override
	public List<PersonalDetailEntity> getAllPersonel() {
		return this.personalDetailRepository.findAll();
	}


}
