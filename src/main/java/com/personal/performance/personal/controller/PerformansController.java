package com.personal.performance.personal.controller;

import java.util.List;
import java.util.Map;
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
	public List<PerformansEntity> updatePerformans(@RequestParam Integer haftaId){
		return this.performansService.updateBakilanCagriTamCcs(haftaId);
	}
	
	@PostMapping("/updateYoneticiPuaniYpd")
	public List<PerformansEntity> updateYoneticiPuaniYpd(@RequestBody List<PerformansYoneticiPuaniDto> performansYoneticiPuaniList){
		return this.performansService.updateYoneticiPuaniYpd(performansYoneticiPuaniList);
	}
	
	@PostMapping("/updateYenidenAcilanCagriPuaniYcs")
	public List<PerformansEntity> updateYenidenAcilanCagriPuaniYcs(@RequestBody YenidenAcilanCagriDto yenidenAcilanCagri){
		return this.performansService.updateYenidenAcilanCagriPuaniYcs(yenidenAcilanCagri);
	}
	
	@GetMapping("/getCcsYcsYpdEkipPersonal")
	public Map<Integer, List<PerformansEntity>> getCcsYcsYpdEkipPersonal(@RequestParam Integer hafta1, @RequestParam Integer hafta2, @RequestParam String ekip){
		return this.performansService.getCcsYcsYpdEkipPersonal(hafta1, hafta2, ekip);
	}
	
	@GetMapping("/allPerformansByHafta")
	public List<PerformansEntity> getAllPerformansByHafta(@RequestParam Integer hafta){
		return this.performansService.getAllPerformansByHafta(hafta);
	}
	
	@GetMapping("/getPersonalByHaftalar")
	public List<PerformansEntity> getPersonalByHaftalar(@RequestParam Integer hafta1, @RequestParam Integer hafta2, @RequestParam Integer personelId){
		return this.performansService.getPersonalByHaftalar(hafta1, hafta2, personelId);
	}

	@GetMapping("/getPersonalCagriSayiSureTahmin")
	public List<Map<String, Double>> getPersonalCagriSayiSureTahmin(@RequestParam Integer personelId){
		return this.performansService.getPersonalCagriSayiSureTahmin(personelId);
	}
}
