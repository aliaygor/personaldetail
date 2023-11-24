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
			Integer	bakilanCagriTam = (haftaEntity.get().getCalisma_saati() * performans.getBakilanCagri()) / performans.getKisiCalismaSaati();
			performans.setBakilanCagriTam(bakilanCagriTam);
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

	        performansEntityList.stream().forEach(performans -> {
				//Long ccsPuani = Long.valueOf(((performans.getBakilanCagriTam() - minBakilanCagriTam.longValue()) / (maxBakilanCagriTam.longValue() - minBakilanCagriTam.longValue())) * 25 + 75);
				BigDecimal ccsPuani = new BigDecimal(performans.getBakilanCagriTam())
				        .subtract(minBakilanCagriTam)
				        .divide(maxBakilanCagriTam.subtract(minBakilanCagriTam), 10, BigDecimal.ROUND_HALF_UP)
				        .multiply(new BigDecimal("-25"))
				        .add(new BigDecimal("75"))
				        .setScale(0, BigDecimal.ROUND_HALF_UP);

				performans.setCcsPuani(ccsPuani.longValue());
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
				
				//Long ypdPuani = (yoneticiPuani.getAgirlik() * yoneticiPuani.getYoneticiPuani()) +  ((100 - yoneticiPuani.getAgirlik())*performansEntity.getCcsPuani()) / 100;
				
				BigDecimal agirlik = new BigDecimal(yoneticiPuani.getAgirlik());
				BigDecimal yoneticiPuaniDegeri = new BigDecimal(yoneticiPuani.getYoneticiPuani());
				BigDecimal ccsPuani = new BigDecimal(performansEntity.getCcsPuani());

				BigDecimal ypdPuani = agirlik.multiply(yoneticiPuaniDegeri)
				        .add(new BigDecimal(100).subtract(agirlik)
				                .multiply(ccsPuani).divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP))
				        .setScale(0, BigDecimal.ROUND_HALF_UP);
				
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
			performansEntityList.stream().forEach(performans -> {
				Integer yenidenAcilanCagriTam = (haftaEntity.get().getCalisma_saati() * performans.getYenidenAcilanCagri()) / performans.getKisiCalismaSaati();			
				
				BigDecimal yenidenAcilanCagriPuani = (((BigDecimal.valueOf(yenidenAcilanCagriTam)
				        .subtract(minYenidenAcilanCagriTam))
				        .divide(maxYenidenAcilanCagriTam.subtract(minYenidenAcilanCagriTam), 2, BigDecimal.ROUND_HALF_UP))
				        .multiply(new BigDecimal(-25)))
				        .add(new BigDecimal(75));				
				
				performans.setYenidenAcilanCagriTam(yenidenAcilanCagriTam);
				performans.setYenidenAcilanCagriPuani(yenidenAcilanCagriPuani.longValue());

				BigDecimal yenidenAcilanPuan = new BigDecimal(yenidenAcilanCagriDto.getYenidenAcilanPuan());
				BigDecimal yenidenAcilanCagriPuaniBigDecimal = new BigDecimal(yenidenAcilanCagriPuani.intValue());
				BigDecimal yoneticiPuan = new BigDecimal(yenidenAcilanCagriDto.getYoneticiPuan());
				BigDecimal performansYoneticiPuani = new BigDecimal(performans.getYoneticiPuani().intValue());
				BigDecimal ccsPuani = new BigDecimal(performans.getCcsPuani());

				BigDecimal ycsPuani = ((yenidenAcilanPuan.multiply(yenidenAcilanCagriPuaniBigDecimal))
						.add(yoneticiPuan.multiply(performansYoneticiPuani))
						.add((new BigDecimal(100).subtract(yenidenAcilanPuan).subtract(yoneticiPuan)).multiply(ccsPuani)))
						.divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP);
				performans.setYcsPuani(ycsPuani.longValue());
				
				this.performansRepository.save(performans);
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
		
		Integer maxHaftaSira = performansList.stream().map(PerformansEntity::getHaftaSira).max(Integer::compareTo).orElse(null);
		Double averageBakilanCagriTam = performansList.stream().filter(performans -> performans.getBakilanCagri() != null).mapToDouble(PerformansEntity::getBakilanCagri).average().orElse(Double.NaN);
		Double averageYenidenAcilanCagriTam = performansList.stream().filter(performans -> performans.getYenidenAcilanCagri() != null).mapToDouble(PerformansEntity::getYenidenAcilanCagri).average().orElse(Double.NaN);
		Double tahminCagriSayi = (averageBakilanCagriTam - averageYenidenAcilanCagriTam) / maxHaftaSira;
		
		tahminCagriSayiMap.put("Tahmini Çözülen Çağrı Sayısı", Double.valueOf(Math.round(tahminCagriSayi * 100) / 100));
		tahminiCagriMapList.add(tahminCagriSayiMap);
		
		Double averageKisiCalismaSaati = performansList.stream().filter(performans -> performans.getKisiCalismaSaati() != null).mapToDouble(PerformansEntity::getKisiCalismaSaati).average().orElse(Double.NaN);
		Double tahminCagriSure = ((averageBakilanCagriTam - averageYenidenAcilanCagriTam) * averageKisiCalismaSaati) / (averageBakilanCagriTam - averageYenidenAcilanCagriTam);
		
		tahminCagriSureMap.put("Tahmini Çözülen Çağrı Süre", tahminCagriSure);
		tahminiCagriMapList.add(tahminCagriSureMap);
	
		return tahminiCagriMapList;
	}
	
}
