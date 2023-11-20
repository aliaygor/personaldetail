package com.personal.performance.personal.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.performance.personal.entity.DepartmentEntity;
import com.personal.performance.personal.repository.DepartmentRepository;
import com.personal.performance.personal.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{
	
	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public Optional<DepartmentEntity> getDepartmentById(Long departmentId) {
		return this.departmentRepository.findById(departmentId);
	}

	@Override
	public List<DepartmentEntity> getAllDepartment() {
		return this.departmentRepository.findAll();
	}
	


}
