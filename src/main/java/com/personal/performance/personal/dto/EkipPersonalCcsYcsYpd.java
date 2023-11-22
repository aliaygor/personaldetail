package com.personal.performance.personal.dto;

import java.util.List;

public class EkipPersonalCcsYcsYpd {
	
	private Integer ekipId;
	private List<PersonalCcsYcsYpd> personalCcsYcsYpd;
	
	public EkipPersonalCcsYcsYpd(Integer ekipId, List<PersonalCcsYcsYpd> personalCcsYcsYpd) {
		super();
		this.ekipId = ekipId;
		this.personalCcsYcsYpd = personalCcsYcsYpd;
	}
	
	public Integer getEkipId() {
		return ekipId;
	}
	public void setEkipId(Integer ekipId) {
		this.ekipId = ekipId;
	}
	public List<PersonalCcsYcsYpd> getPersonalCcsYcsYpd() {
		return personalCcsYcsYpd;
	}
	public void setPersonalCcsYcsYpd(List<PersonalCcsYcsYpd> personalCcsYcsYpd) {
		this.personalCcsYcsYpd = personalCcsYcsYpd;
	}
	
}
