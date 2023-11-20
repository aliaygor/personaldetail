package com.personal.performance.personal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personal.performance.personal.entity.CalisanEntity;
import com.personal.performance.personal.service.CalisanService;

@RestController
@RequestMapping("/calisan")
public class CalisanController {
	
	@Autowired
	private CalisanService calisanService;

	@GetMapping("/calisanById")
	public Optional<CalisanEntity> getCalisan(@RequestParam Long calisanId){
		return this.calisanService.getCalisanById(calisanId);
	}

	@GetMapping("/allCalisan")
	public List<CalisanEntity> getAllCalisan(){
		return this.calisanService.getAllCalisan();
	}

}
