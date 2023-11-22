package com.personal.performance.personal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.performance.personal.dto.PerformansYoneticiPuaniDto;
import com.personal.performance.personal.dto.YenidenAcilanCagriDto;
import com.personal.performance.personal.entity.HaftalarEntity;
import com.personal.performance.personal.entity.PerformansEntity;
import com.personal.performance.personal.repository.HaftalarRepository;
import com.personal.performance.personal.repository.PerformansRepository;
import com.personal.performance.personal.service.PerformansService;

@Service
public class PerformansServiceImpl implements PerformansService{
	
	@Autowired
	private PerformansRepository performansRepository;
	@Autowired
	private HaftalarRepository haftalarRepository;

	@Override
	public Optional<PerformansEntity> getPerformansById(Long performansId) {
		return this.performansRepository.findById(performansId);
	}

	@Override
	public List<PerformansEntity> getAllPerformans() {
		return this.performansRepository.findAll();
	}

	@Override
	public List<PerformansEntity> savePerformansList(List<PerformansEntity> performansEntityList) {
		return this.performansRepository.saveAll(performansEntityList);
	}
	
	@Override
	public PerformansEntity savePerformans(PerformansEntity performansEntity) {
		return this.performansRepository.save(performansEntity);
	}
	
	@Override
	public List<PerformansEntity> updateBakilanCagriTamCcs(Integer haftaId) {
		
		Optional<HaftalarEntity> haftaEntity = this.haftalarRepository.findById(Long.valueOf(haftaId));
		List<PerformansEntity> performanList = this.performansRepository.findPerformansByHaftaSira(haftaId);
		
		if(performanList != null && !performanList.isEmpty()) {
			performanList.stream().forEach(performans -> {
			Integer	bakilanCagriTam = (haftaEntity.get().getCalisma_saati() * performans.getBakilanCagri()) / performans.getKisiCalismaSaati();
			performans.setBakilanCagriTam(bakilanCagriTam);
			this.performansRepository.save(performans);
			});
			
			Integer minBakilanCagriTam = this.performansRepository.findFirstByOrderByBakilanCagriTamAsc().getBakilanCagriTam();
			Integer maxBakilanCagriTam = this.performansRepository.findFirstByOrderByBakilanCagriTamDesc().getBakilanCagriTam();

			performanList.stream().forEach(performans -> {
				Long ccsPuani = Long.valueOf((performans.getBakilanCagriTam() - minBakilanCagriTam) / (maxBakilanCagriTam - minBakilanCagriTam) * 25 + 75);
				performans.setCcsPuani(ccsPuani);
				this.performansRepository.save(performans);
			});
		}
		
		return this.performansRepository.findPerformansByHaftaSira(haftaId);
	}

	@Override
	public List<PerformansEntity> updateYoneticiPuaniYpd(List<PerformansYoneticiPuaniDto> performansYoneticiPuaniList) {
		performansYoneticiPuaniList.stream().forEach(yoneticiPuani -> {
			PerformansEntity performansEntity = this.performansRepository.findPerformansByPersonelIdAndHaftaSira(yoneticiPuani.getPersonalId(), yoneticiPuani.getHaftaId());
			if(Objects.nonNull(performansEntity)) {
				performansEntity.setYoneticiPuani(yoneticiPuani.getYoneticiPuani());
				
				Long ypdPuani = (yoneticiPuani.getAgirlik() * yoneticiPuani.getYoneticiPuani()) +  ((100 - yoneticiPuani.getAgirlik())*performansEntity.getCcsPuani()) / 100;
				
				performansEntity.setYpdPuani(ypdPuani);
				
				this.performansRepository.save(performansEntity);
			}
		}); 
		return this.performansRepository.findPerformansByHaftaSira(performansYoneticiPuaniList.get(0).getHaftaId().intValue());
	}

	@Override
	public List<PerformansEntity> updateYenidenAcilanCagriPuaniYcs(YenidenAcilanCagriDto yenidenAcilanCagriDto) {

		Optional<HaftalarEntity> haftaEntity = this.haftalarRepository.findById(Long.valueOf(yenidenAcilanCagriDto.getHaftaId()));
		List<PerformansEntity> performansEntityList = this.performansRepository.findPerformansByHaftaSira(yenidenAcilanCagriDto.getHaftaId());
		
		Integer minYenidenAcilanCagriTam = this.performansRepository.findFirstByOrderByYenidenAcilanCagriTamAsc().getBakilanCagriTam();
		Integer maxYenidenAcilanCagriTam = this.performansRepository.findFirstByOrderByYenidenAcilanCagriTamDesc().getBakilanCagriTam();
		
		if(performansEntityList != null && !performansEntityList.isEmpty()) {
			performansEntityList.stream().forEach(performans -> {
				Integer yenidenAcilanCagriTam = (haftaEntity.get().getCalisma_saati() * performans.getYenidenAcilanCagri()) / performans.getKisiCalismaSaati();
				Integer yenidenAcilanCagriPuani = (((yenidenAcilanCagriTam - minYenidenAcilanCagriTam) / (maxYenidenAcilanCagriTam - minYenidenAcilanCagriTam))*(-25)) + 75; 
			
				performans.setYenidenAcilanCagriTam(maxYenidenAcilanCagriTam);
				performans.setYenidenAcilanCagriPuani(Long.valueOf(yenidenAcilanCagriPuani));
				
				Long ycsPuani = (yenidenAcilanCagriDto.getYenidenAcilanPuan() * yenidenAcilanCagriPuani) +
						(yenidenAcilanCagriDto.getYoneticiPuan() * performans.getYoneticiPuani().intValue()) + 
						((100 - yenidenAcilanCagriDto.getYenidenAcilanPuan() - yenidenAcilanCagriDto.getYoneticiPuan()) * performans.getCcsPuani())/100;
								
				performans.setYcsPuani(ycsPuani);
			});
		}
		
		return this.performansRepository.findPerformansByHaftaSira(yenidenAcilanCagriDto.getHaftaId());

	}
	
	@Override
	public Map<Integer, List<PerformansEntity>> getCcsYcsYpdEkipPersonal(Integer hafta1, Integer hafta2, String ekip) {
		List<PerformansEntity> performansList = new ArrayList<>();
		if("all".equals(ekip)) {
			performansList = this.performansRepository.findByPerformansByHaftaSira(hafta1, hafta2);
		}else {
			performansList = this.performansRepository.findByPerformansByHaftaSiraAndEkipId(hafta1, hafta2, Integer.valueOf(ekip));
		}
		
		Map<Integer, List<PerformansEntity>> groupedByEkipId = new HashMap<>();
		if(performansList != null && !performansList.isEmpty()) {
	        for (PerformansEntity performans : performansList) {
	            groupedByEkipId.computeIfAbsent(performans.getEkipId(), k -> new ArrayList<>()).add(performans);
	        }
		}
		return groupedByEkipId;
	}
	
	@Override
	public List<PerformansEntity> getAllPerformansByHafta(Integer hafta) {
		return this.performansRepository.findPerformansByHaftaSira(hafta);
	}
	
}
