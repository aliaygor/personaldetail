package com.personal.performance.personal.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.personal.performance.personal.dto.PerformansYoneticiPuaniDto;
import com.personal.performance.personal.dto.YenidenAcilanCagriDto;
import com.personal.performance.personal.entity.PerformansEntity;

public interface PerformansService {
	
	public Optional<PerformansEntity> getPerformansById(Long performansId);
	
	public List<PerformansEntity> getAllPerformans();
	
	public List<PerformansEntity> savePerformansList(List<PerformansEntity> performansEntityList);
	
	public PerformansEntity savePerformans(PerformansEntity performansEntity);
	
	public List<PerformansEntity> updateBakilanCagriTamCcs(Integer haftaId);
	
	public List<PerformansEntity> updateYoneticiPuaniYpd(List<PerformansYoneticiPuaniDto> performansYoneticiPuaniList);
	
	public List<PerformansEntity> updateYenidenAcilanCagriPuaniYcs(YenidenAcilanCagriDto yenidenAcilanCagriDto);
	
	public Map<Integer, List<PerformansEntity>> getCcsYcsYpdEkipPersonal(Integer hafta1, Integer hafta2, String ekip);
	
	public List<PerformansEntity> getAllPerformansByHafta(Integer hafta);
	
	public List<PerformansEntity> getPersonalByHaftalar(Integer hafta1, Integer hafta2, Integer personelId);
	
	public List<Map<String, Double>> getPersonalCagriSayiSureTahmin(Integer personelId);

}
