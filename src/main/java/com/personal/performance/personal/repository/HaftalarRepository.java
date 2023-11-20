package com.personal.performance.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.personal.performance.personal.entity.HaftalarEntity;

@RepositoryRestResource
public interface HaftalarRepository extends JpaRepository<HaftalarEntity, Long>{

}
