package com.personal.performance.personal.dto;

import java.util.List;

public class EkipPersonalCcsYcsYpd {
	
	private String ekipId;
	private List<PersonalCcsYcsYpd> personalCcsYcsYpd;
	
	public String getEkipId() {
		return ekipId;
	}
	public void setEkipId(String ekipId) {
		this.ekipId = ekipId;
	}
	public List<PersonalCcsYcsYpd> getPersonalCcsYcsYpd() {
		return personalCcsYcsYpd;
	}
	public void setPersonalCcsYcsYpd(List<PersonalCcsYcsYpd> personalCcsYcsYpd) {
		this.personalCcsYcsYpd = personalCcsYcsYpd;
	}
	
}
