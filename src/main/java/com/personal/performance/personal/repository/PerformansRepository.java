package com.personal.performance.personal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
