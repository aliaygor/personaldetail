package com.personal.performance.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.personal.performance.personal.entity.DepartmentEntity;

@RepositoryRestResource
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>{

}
