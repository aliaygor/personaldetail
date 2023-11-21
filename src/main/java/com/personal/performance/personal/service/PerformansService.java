package com.personal.performance.personal.service;

import java.util.List;
import java.util.Optional;

import com.personal.performance.personal.dto.EkipPersonalCcsYcsYpd;
import com.personal.performance.personal.dto.PerformansYoneticiPuaniDto;
import com.personal.performance.personal.dto.YenidenAcilanCagriDto;
import com.personal.performance.personal.entity.PerformansEntity;

public interface PerformansService {
	
	public Optional<PerformansEntity> getPerformansById(Long performansId);
	
	public List<PerformansEntity> getAllPerformans();
	
	public List<PerformansEntity> savePerformansList(List<PerformansEntity> performansEntityList);
	
	public PerformansEntity savePerformans(PerformansEntity performansEntity);
	
	public void updateBakilanCagriTamCcs(Integer haftaId);
	
	public void updateYoneticiPuaniYpd(List<PerformansYoneticiPuaniDto> performansYoneticiPuaniList);
	
	public void updateYenidenAcilanCagriPuaniYcs(YenidenAcilanCagriDto yenidenAcilanCagriDto);
	
	public List<EkipPersonalCcsYcsYpd> getCcsYcsYpdEkipPersonal(Long hafta1, Long hafta2, String ekip);

}
