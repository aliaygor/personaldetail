package com.personal.performance.personal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.personal.performance.personal.entity.PerformansEntity;

@RepositoryRestResource
public interface PerformansRepository extends JpaRepository<PerformansEntity, Long> {

	PerformansEntity findPerformansByPersonelIdAndHaftaSira(Long personelId, Long haftaSira);

	List<PerformansEntity> findPerformansByHaftaSira(Integer haftaId);

	PerformansEntity findFirstByOrderByBakilanCagriTamAsc();

	PerformansEntity findFirstByOrderByBakilanCagriTamDesc();

	PerformansEntity findFirstByOrderByYenidenAcilanCagriTamAsc();

	PerformansEntity findFirstByOrderByYenidenAcilanCagriTamDesc();
	
	//@Query("SELECT * FROM performans p WHERE (p.hafta_sira :hafta1 or p.hafta_sira :hafta2) and p.ekip_id :ekip")
    //List<PerformansEntity> findByPerformansByHaftaSiraAndEkipId(@Param("hafta1") String hafta1, @Param("hafta2") String hafta2, @Param("ekip") String ekip);

	//@Query("SELECT * FROM performans p WHERE (p.hafta_sira :hafta1 or p.hafta_sira :hafta2)")
    //List<PerformansEntity> findByPerformansByHaftaSira(@Param("hafta1") String hafta1, @Param("hafta2") String hafta2);

}
