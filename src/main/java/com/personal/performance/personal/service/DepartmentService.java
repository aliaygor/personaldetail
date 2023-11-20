package com.personal.performance.personal.service;

import java.util.List;
import java.util.Optional;

import com.personal.performance.personal.entity.DepartmentEntity;

public interface DepartmentService {
	
	public Optional<DepartmentEntity> getDepartmentById(Long departmentId);
	
	public List<DepartmentEntity> getAllDepartment();

}
