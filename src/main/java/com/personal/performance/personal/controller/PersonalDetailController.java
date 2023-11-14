package com.personal.performance.personal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.performance.personal.entity.PersonalDetailEntity;
import com.personal.performance.personal.service.PersonalDetailService;
 
@RestController
@RequestMapping("/personalDetail")
public class PersonalDetailController {
	
	@Autowired
	private PersonalDetailService personalDetailService;
	

	@GetMapping("/allPersonalDetail")
	public List<PersonalDetailEntity> getAllPersonel(){

		return this.personalDetailService.getAllPersonel();
		
	}

}
