package com.personal.performance.personal.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "department")
public class DepartmentEntity {
    @Id
    @Column(name = "ekip_id")
    private int ekipId;

    @Column(name = "ekip_adi", nullable = false)
    private String ekipAdi;

    @Column(name = "createdate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Column(name = "updatedate")
    @Temporal(TemporalType.DATE)
    private Date updateDate;

	public int getEkipId() {
		return ekipId;
	}

	public void setEkipId(int ekipId) {
		this.ekipId = ekipId;
	}

	public String getEkipAdi() {
		return ekipAdi;
	}

	public void setEkipAdi(String ekipAdi) {
		this.ekipAdi = ekipAdi;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
    
    
}
