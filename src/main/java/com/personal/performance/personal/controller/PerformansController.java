package com.personal.performance.personal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personal.performance.personal.dto.PerformansYoneticiPuaniDto;
import com.personal.performance.personal.dto.YenidenAcilanCagriDto;
import com.personal.performance.personal.entity.PerformansEntity;
import com.personal.performance.personal.service.PerformansService;

@RestController
@RequestMapping("/performans")
public class PerformansController {
	
	@Autowired
	private PerformansService performansService;

	@GetMapping("/performansById")
	public Optional<PerformansEntity> getPerformans(@RequestParam Long performansId){
		return this.performansService.getPerformansById(performansId);
	}

	@GetMapping("/allPerformans")
	public List<PerformansEntity> getAllPerformans(){
		return this.performansService.getAllPerformans();
	}
	
	@PostMapping("/savePerformansList")
	public List<PerformansEntity> savePerformansList(@RequestBody List<PerformansEntity> performansEntitiesList){
		return this.performansService.savePerformansList(performansEntitiesList);
	}
	
	@PostMapping("/savePerformans")
	public PerformansEntity savePerformans(@RequestBody PerformansEntity performansEntities){
		return this.performansService.savePerformans(performansEntities);
	}
	
	@PostMapping("/updateBakilanCagriTamCcs")
	public void updatePerformans(@RequestParam Integer haftaId){
		this.performansService.updateBakilanCagriTamCcs(haftaId);
	}
	
	@PostMapping("/updateYoneticiPuaniYpd")
	public void updateYoneticiPuaniYpd(@RequestBody List<PerformansYoneticiPuaniDto> performansYoneticiPuaniList){
		this.performansService.updateYoneticiPuaniYpd(performansYoneticiPuaniList);
	}
	
	@PostMapping("/updateYenidenAcilanCagriPuaniYcs")
	public void updateYenidenAcilanCagriPuaniYcs(@RequestBody YenidenAcilanCagriDto yenidenAcilanCagri){
		this.performansService.updateYenidenAcilanCagriPuaniYcs(yenidenAcilanCagri);
	}

}