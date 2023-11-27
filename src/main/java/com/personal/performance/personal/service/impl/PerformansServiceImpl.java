package com.personal.performance.personal.service.impl;

import java.math.BigDecimal;
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
		List<PerformansEntity> performansEntityList = this.performansRepository.findPerformansByHaftaSira(haftaId);
		
		if(performansEntityList != null && !performansEntityList.isEmpty()) {
			performansEntityList.stream().forEach(performans -> {
			
			BigDecimal calismaSaati = new BigDecimal(haftaEntity.get().getCalisma_saati());
			BigDecimal bakilanCagri = new BigDecimal(performans.getBakilanCagri());
			BigDecimal kisiCalismaSaati = new BigDecimal(performans.getKisiCalismaSaati());

			BigDecimal bakilanCagriTam = (calismaSaati.multiply(bakilanCagri)).divide(kisiCalismaSaati, 2, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP);
			
			performans.setBakilanCagriTam(bakilanCagriTam.intValue());
			this.performansRepository.save(performans);
			});
			
			BigDecimal minBakilanCagriTam = performansEntityList.stream()
	                .map(PerformansEntity::calculateBakilanCagriTam)
	                .min(BigDecimal::compareTo)
	                .orElse(BigDecimal.ZERO);

	        BigDecimal maxBakilanCagriTam = performansEntityList.stream()
	                .map(PerformansEntity::calculateBakilanCagriTam)
	                .max(BigDecimal::compareTo)
	                .orElse(BigDecimal.ZERO);

	        performansEntityList.stream().forEach(performansEntity -> {
	        	BigDecimal ccsPuani = (((new BigDecimal(performansEntity.getBakilanCagriTam()).subtract(minBakilanCagriTam)).divide(maxBakilanCagriTam.subtract(minBakilanCagriTam), 10, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("25"))).add(new BigDecimal("75")).setScale(0, BigDecimal.ROUND_HALF_UP);
	        	performansEntity.setCcsPuani(ccsPuani.longValue());
				this.performansRepository.save(performansEntity);
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
				
				BigDecimal agirlik = new BigDecimal(yoneticiPuani.getAgirlik());
				BigDecimal yoneticiPuaniDegeri = new BigDecimal(yoneticiPuani.getYoneticiPuani());
				BigDecimal ccsPuani = new BigDecimal(performansEntity.getCcsPuani());

				BigDecimal ypdPuani = ((agirlik.multiply(yoneticiPuaniDegeri)).add((new BigDecimal(100).subtract(agirlik)).multiply(ccsPuani))).divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP);
				
				performansEntity.setYpdPuani(ypdPuani.longValue());
				
				this.performansRepository.save(performansEntity);
			}
		}); 
		return this.performansRepository.findPerformansByHaftaSira(performansYoneticiPuaniList.get(0).getHaftaId().intValue());
	}
	
	@Override
	public List<PerformansEntity> updateYenidenAcilanCagriPuaniYcs(YenidenAcilanCagriDto yenidenAcilanCagriDto) {

		Optional<HaftalarEntity> haftaEntity = this.haftalarRepository.findById(Long.valueOf(yenidenAcilanCagriDto.getHaftaId()));
		List<PerformansEntity> performansEntityList = this.performansRepository.findPerformansByHaftaSira(yenidenAcilanCagriDto.getHaftaId());
		
		BigDecimal minYenidenAcilanCagriTam = performansEntityList.stream()
                .map(PerformansEntity::calculateYenidenAcilanCagriTam)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal maxYenidenAcilanCagriTam = performansEntityList.stream()
                .map(PerformansEntity::calculateYenidenAcilanCagriTam)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

		if(performansEntityList != null && !performansEntityList.isEmpty()) {
			performansEntityList.stream().forEach(performansEntity -> {				
				BigDecimal calismaSaati = new BigDecimal(haftaEntity.get().getCalisma_saati());
				BigDecimal yenidenAcilanCagri = new BigDecimal(performansEntity.getYenidenAcilanCagri());
				BigDecimal kisiCalismaSaati = new BigDecimal(performansEntity.getKisiCalismaSaati());

				BigDecimal yenidenAcilanCagriTam = (calismaSaati.multiply(yenidenAcilanCagri)).divide(kisiCalismaSaati, 2, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP);

				BigDecimal yenidenAcilanCagriPuani = (((yenidenAcilanCagriTam
				        .subtract(minYenidenAcilanCagriTam))
				        .divide(maxYenidenAcilanCagriTam.subtract(minYenidenAcilanCagriTam), 2, BigDecimal.ROUND_HALF_UP))
				        .multiply(new BigDecimal(-25)))
				        .add(new BigDecimal(75)).setScale(0, BigDecimal.ROUND_HALF_UP);;				
				
				performansEntity.setYenidenAcilanCagriTam(yenidenAcilanCagriTam.intValue());
				performansEntity.setYenidenAcilanCagriPuani(yenidenAcilanCagriPuani.longValue());

				BigDecimal yenidenAcilanPuan = new BigDecimal(yenidenAcilanCagriDto.getYenidenAcilanPuan());
				BigDecimal yenidenAcilanCagriPuaniBigDecimal = new BigDecimal(yenidenAcilanCagriPuani.intValue());
				BigDecimal yoneticiPuan = new BigDecimal(yenidenAcilanCagriDto.getYoneticiPuan());
				BigDecimal performansYoneticiPuani = new BigDecimal(performansEntity.getYoneticiPuani().intValue());
				BigDecimal ccsPuani = new BigDecimal(performansEntity.getCcsPuani());


				BigDecimal ycsPuani = ((yenidenAcilanPuan.multiply(yenidenAcilanCagriPuaniBigDecimal))
						.add(yoneticiPuan.multiply(performansYoneticiPuani))
						.add((new BigDecimal(100).subtract(yenidenAcilanPuan).subtract(yoneticiPuan))
						.multiply(ccsPuani)))
						.divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP);
				
				performansEntity.setYcsPuani(ycsPuani.longValue());
				
				this.performansRepository.save(performansEntity);
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
	
	@Override
	public List<PerformansEntity> getPersonalByHaftalar(Integer hafta1, Integer hafta2, Integer personelId) {
		return this.performansRepository.findByPerformansByHaftaSiraAndPersonelId(hafta1, hafta2, personelId);
	}
	
	@Override
	public List<Map<String, Double>> getPersonalCagriSayiSureTahmin(Integer personelId) {
		
		List<Map<String, Double>> tahminiCagriMapList = new ArrayList<>();
		Map<String, Double> tahminCagriSayiMap = new HashMap<>();
		Map<String, Double> tahminCagriSureMap = new HashMap<>();

		List<PerformansEntity> performansList = this.performansRepository.findPerformansByPersonelId(personelId);
		
		Double averageBakilanCagriTam = performansList.stream().filter(performans -> performans.getBakilanCagriTam() != null).mapToDouble(PerformansEntity::getBakilanCagriTam).average().orElse(Double.NaN);
		Double averageKisiCalismaSaati = performansList.stream().filter(performans -> performans.getKisiCalismaSaati() != null).mapToDouble(PerformansEntity::getKisiCalismaSaati).average().orElse(Double.NaN);

		tahminCagriSayiMap.put("Tahmini Çözülen Çağrı Sayısı", Double.valueOf(Math.round(averageBakilanCagriTam * 100) / 100));
		tahminiCagriMapList.add(tahminCagriSayiMap);
		
		tahminCagriSureMap.put("Tahmini Çözülen Çağrı Süre", Double.valueOf(Math.round(averageKisiCalismaSaati * 100) / 100));
		tahminiCagriMapList.add(tahminCagriSureMap);
	
		return tahminiCagriMapList;
	}
	
}
