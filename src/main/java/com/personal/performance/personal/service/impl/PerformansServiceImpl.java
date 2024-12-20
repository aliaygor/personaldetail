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
			performansEntityList.stream().forEach(performansEntity -> {
				
				if(performansEntity.getBakilanCagri() == 0) {
					performansEntity.setBakilanCagriTam(performansEntity.getBakilanCagri());
				}else {
					BigDecimal bakilanCagriTam = (new BigDecimal(haftaEntity.get().getCalisma_saati()).multiply(new BigDecimal(performansEntity.getBakilanCagri())))
							.divide(new BigDecimal(performansEntity.getKisiCalismaSaati()), 2, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP);
					performansEntity.setBakilanCagriTam(bakilanCagriTam.intValue());
				}
				
				this.performansRepository.save(performansEntity);
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
	        	
	        	if(performansEntity.getKisiCalismaSaati() == 0) {
	        		if(performansEntity.getHaftaSira() == 1) {
	        			performansEntity.setCcsPuani(75L);
	        		}else {
	        			PerformansEntity performans = this.performansRepository.findPerformansByPersonelIdAndHaftaSira(Long.valueOf(performansEntity.getPersonelId()), performansEntity.getHaftaSira().longValue()-1);
	        			performansEntity.setCcsPuani(performans.getCcsPuani());
	        		}
	        	}else {
		        	BigDecimal ccsPuani = (((new BigDecimal(performansEntity.getBakilanCagriTam()).subtract(minBakilanCagriTam)).divide(maxBakilanCagriTam.subtract(minBakilanCagriTam), 10, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("25"))).add(new BigDecimal("75")).setScale(0, BigDecimal.ROUND_HALF_UP);
		        	performansEntity.setCcsPuani(ccsPuani.longValue());
	        	}
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

	        	if(performansEntity.getKisiCalismaSaati() == 0) {
	        		if(performansEntity.getHaftaSira() == 1) {
	        			performansEntity.setYpdPuani(75L);
	        		}else {
	        			PerformansEntity performans = this.performansRepository.findPerformansByPersonelIdAndHaftaSira(Long.valueOf(performansEntity.getPersonelId()), performansEntity.getHaftaSira().longValue()-1);
	        			performansEntity.setYpdPuani(performans.getYpdPuani());
	        		}
	        	}else {
					BigDecimal ypdPuani = ((agirlik.multiply(yoneticiPuaniDegeri)).add((new BigDecimal(100).subtract(agirlik)).multiply(ccsPuani))).divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP);
					performansEntity.setYpdPuani(ypdPuani.longValue());
	        	}
				
				this.performansRepository.save(performansEntity);
			}
		}); 
		return this.performansRepository.findPerformansByHaftaSira(performansYoneticiPuaniList.get(0).getHaftaId().intValue());
	}
	
	@Override
	public List<PerformansEntity> updateYenidenAcilanCagriPuaniYcs(YenidenAcilanCagriDto yenidenAcilanCagriDto) {

		Optional<HaftalarEntity> haftaEntity = this.haftalarRepository.findById(Long.valueOf(yenidenAcilanCagriDto.getHaftaId()));
		List<PerformansEntity> performansEntityList = this.performansRepository.findPerformansByHaftaSira(yenidenAcilanCagriDto.getHaftaId());
		
		if(performansEntityList != null && !performansEntityList.isEmpty()) {
			
		BigDecimal minYenidenAcilanCagriTam = performansEntityList.stream()
                .map(PerformansEntity::calculateYenidenAcilanCagriTam)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal maxYenidenAcilanCagriTam = performansEntityList.stream()
                .map(PerformansEntity::calculateYenidenAcilanCagriTam)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

			performansEntityList.stream().forEach(performansEntity -> {				
				BigDecimal calismaSaati = new BigDecimal(haftaEntity.get().getCalisma_saati());
				BigDecimal yenidenAcilanCagri = new BigDecimal(performansEntity.getYenidenAcilanCagri());
				BigDecimal kisiCalismaSaati = new BigDecimal(performansEntity.getKisiCalismaSaati());

				BigDecimal yenidenAcilanCagriTam = new BigDecimal(0);
				if(performansEntity.getYenidenAcilanCagri() == 0) {
					performansEntity.setYenidenAcilanCagriTam(performansEntity.getYenidenAcilanCagri());
				}else {
					yenidenAcilanCagriTam = (calismaSaati.multiply(yenidenAcilanCagri)).divide(kisiCalismaSaati, 2, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP);
					performansEntity.setYenidenAcilanCagriTam(yenidenAcilanCagriTam.intValue());
				}
			
				if(performansEntity.getKisiCalismaSaati() == 0) {
	        		if(performansEntity.getHaftaSira() == 1) {
	        			performansEntity.setYenidenAcilanCagriPuani(75L);
	        			performansEntity.setYcsPuani(75L);
	        		}else {
	        			PerformansEntity performans = this.performansRepository.findPerformansByPersonelIdAndHaftaSira(Long.valueOf(performansEntity.getPersonelId()), performansEntity.getHaftaSira().longValue()-1);
	        			performansEntity.setYenidenAcilanCagriPuani(performans.getYenidenAcilanCagriPuani());
	        			performansEntity.setYcsPuani(performans.getYcsPuani());
	        		}
	        	}else {
					BigDecimal yenidenAcilanCagriPuani = (((yenidenAcilanCagriTam.subtract(minYenidenAcilanCagriTam)).divide(maxYenidenAcilanCagriTam.subtract(minYenidenAcilanCagriTam), 2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(-25))).add(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);				
					performansEntity.setYenidenAcilanCagriPuani(yenidenAcilanCagriPuani.longValue());
					
					BigDecimal ycsPuani = ((new BigDecimal(yenidenAcilanCagriDto.getYenidenAcilanPuan()).multiply(new BigDecimal(yenidenAcilanCagriPuani.intValue())))
							.add(new BigDecimal(yenidenAcilanCagriDto.getYoneticiPuan()).multiply(new BigDecimal(performansEntity.getYoneticiPuani().intValue())))
							.add((new BigDecimal(100).subtract(new BigDecimal(yenidenAcilanCagriDto.getYenidenAcilanPuan())).subtract(new BigDecimal(yenidenAcilanCagriDto.getYoneticiPuan())))
							.multiply(new BigDecimal(performansEntity.getCcsPuani()))))
							.divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP);
					
					performansEntity.setYcsPuani(ycsPuani.longValue());
	        	}

				if(performansEntity.getHaftaSira() == 1 || performansEntity.getHaftaSira() == 2) {
					performansEntity.setTahminCozulmeCagri(0);
					performansEntity.setTahminYenidenAcilmaCagri(0);
				}else {
        			PerformansEntity performansOldHafta = this.performansRepository.findPerformansByPersonelIdAndHaftaSira(Long.valueOf(performansEntity.getPersonelId()), performansEntity.getHaftaSira().longValue()-1);
        			performansEntity.setTahminCozulmeCagri(Math.abs(performansEntity.getBakilanCagriTam() - performansOldHafta.getBakilanCagriTam() + performansEntity.getBakilanCagriTam()));
					performansEntity.setTahminYenidenAcilmaCagri(Math.abs(performansEntity.getYenidenAcilanCagriTam() - performansOldHafta.getYenidenAcilanCagriTam() + performansEntity.getYenidenAcilanCagriTam()));
				}
				
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
	public List<PerformansEntity> getPersonalByHaftalarBtwHafta(Integer hafta1, Integer hafta2, Integer personelId) {
		return this.performansRepository.findByPerformansByHaftaSiraAndPersonelIdBtw(hafta1, hafta2, personelId);
	}
	
	@Override
	public List<PerformansEntity> getAllPersonelData(Integer personelId) {
		return this.performansRepository.findPerformansByPersonelId(personelId);
	}
	
	
	@Override
	public List<Map<String, Integer>> getPersonalCagriSayiSureTahmin(Integer personelId) {
		
		List<Map<String, Integer>> tahminiCagriMapList = new ArrayList<>();
		Map<String, Integer> tahminCagriSayiMap = new HashMap<>();
		Map<String, Integer> tahminBeklenenCagriAdedi = new HashMap<>();
		
		List<PerformansEntity> performansList = this.performansRepository.findPerformansByPersonelId(personelId);
		int performansListSize = performansList != null ? performansList.size() : 0;
		Integer tahminiCozulenCagriSayisi = 0;
		Integer tahminiBeklenenCagriAdedi = 0;
		if (performansListSize > 1) {
			PerformansEntity performansSonHafta = performansList.get(performansListSize - 1);
            PerformansEntity performansSonHaftadanOnceki = performansList.get(performansListSize - 2);
            tahminiCozulenCagriSayisi = Math.abs(performansSonHafta.getBakilanCagriTam() - performansSonHaftadanOnceki.getBakilanCagriTam() + performansSonHafta.getBakilanCagriTam());
            tahminiBeklenenCagriAdedi = Math.abs(performansSonHafta.getYenidenAcilanCagriTam() - performansSonHaftadanOnceki.getYenidenAcilanCagriTam() + performansSonHafta.getYenidenAcilanCagriTam());

		}

		tahminCagriSayiMap.put("Tahmini Çözülen Çağrı Sayısı", tahminiCozulenCagriSayisi);
		tahminiCagriMapList.add(tahminCagriSayiMap);
		
		tahminBeklenenCagriAdedi.put("Tahmini Açılması Beklenen Çağrı Adedi", tahminiBeklenenCagriAdedi);
		tahminiCagriMapList.add(tahminBeklenenCagriAdedi);
	
		return tahminiCagriMapList;
	}
	
}
