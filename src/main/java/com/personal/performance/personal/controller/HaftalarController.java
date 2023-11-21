package com.personal.performance.personal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personal.performance.personal.entity.HaftalarEntity;
import com.personal.performance.personal.service.HaftalarService;

@RestController
@RequestMapping("/haftalar")
public class HaftalarController {
	
	@Autowired
	private HaftalarService haftalarService;

	@GetMapping("/haftaByHaftaNo")
	public Optional<HaftalarEntity> getHafta(@RequestParam Long haftaId){
		return this.haftalarService.getHaftaById(haftaId);
	}

	@GetMapping("/allHaftalar")
	//@CrossOrigin(origins = "http://personaldetail.onrender.com/haftalar/allHaftalar")
	public List<HaftalarEntity> getAllHaftalar(){
		return this.haftalarService.getAllHaftalar();
	}

}
