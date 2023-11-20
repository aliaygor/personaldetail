package com.personal.performance.personal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personal.performance.personal.entity.DepartmentEntity;
import com.personal.performance.personal.service.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;

	@GetMapping("/departmentById")
	public Optional<DepartmentEntity> getDepartment(@RequestParam Long departmentId){
		return this.departmentService.getDepartmentById(departmentId);
	}

	@GetMapping("/allDepartments")
	public List<DepartmentEntity> getAllDepartments(){
		return this.departmentService.getAllDepartment();
	}

}
