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
	
	@Query("SELECT p FROM PerformansEntity p WHERE (p.haftaSira =:hafta1 or p.haftaSira =:hafta2) and p.ekipId =:ekip")
    List<PerformansEntity> findByPerformansByHaftaSiraAndEkipId(@Param("hafta1") Integer hafta1, @Param("hafta2") Integer hafta2, @Param("ekip") Integer ekip);

	@Query("SELECT p FROM PerformansEntity p WHERE (p.haftaSira =:hafta1 or p.haftaSira =:hafta2)")
    List<PerformansEntity> findByPerformansByHaftaSira(@Param("hafta1") Integer hafta1, @Param("hafta2") Integer hafta2);
	
	@Query("SELECT p FROM PerformansEntity p WHERE (p.haftaSira =:hafta1 or p.haftaSira =:hafta2) and p.personelId =:personelId")
    List<PerformansEntity> findByPerformansByHaftaSiraAndPersonelId(@Param("hafta1") Integer hafta1, @Param("hafta2") Integer hafta2, @Param("personelId") Integer personelId);
	
	@Query("SELECT p FROM PerformansEntity p WHERE p.personelId =:personelId")
	List<PerformansEntity> findPerformansByPersonelId(@Param("personelId") Integer personelId);

}
