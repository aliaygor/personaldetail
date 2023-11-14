package com.personal.performance.personal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "personal_detail")
public class PersonalDetailEntity {
	
	@Id
	@Column(name="personal_id")
	private String personal_id;
	@Column(name="personal_name")
	private String personal_name;
	public String getPersonal_id() {
		return personal_id;
	}
	public void setPersonal_id(String personal_id) {
		this.personal_id = personal_id;
	}
	public String getPersonal_name() {
		return personal_name;
	}
	public void setPersonal_name(String personal_name) {
		this.personal_name = personal_name;
	}
	

}
