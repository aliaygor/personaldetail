package com.personal.performance.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.personal.performance.personal.entity.CalisanEntity;

@RepositoryRestResource
public interface CalisanRepository extends JpaRepository<CalisanEntity, Long>{

}
