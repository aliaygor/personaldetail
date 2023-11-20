package com.personal.performance.personal.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "calisan")
public class CalisanEntity {
    @Id
    @Column(name = "personel_id")
    private int personelId;

    @Column(name = "personel_adi", nullable = false)
    private String personelAdi;

    @Column(name = "personel_soyadi", nullable = false)
    private String personelSoyadi;

    @Column(name = "ekip_id", nullable = false)
    private int ekipId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "is_active")
    private boolean isActive = false;

    @Column(name = "createdate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Column(name = "updatedate")
    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "ekip_id", insertable = false, updatable = false)
    private DepartmentEntity department;

	public int getPersonelId() {
		return personelId;
	}

	public void setPersonelId(int personelId) {
		this.personelId = personelId;
	}

	public String getPersonelAdi() {
		return personelAdi;
	}

	public void setPersonelAdi(String personelAdi) {
		this.personelAdi = personelAdi;
	}

	public String getPersonelSoyadi() {
		return personelSoyadi;
	}

	public void setPersonelSoyadi(String personelSoyadi) {
		this.personelSoyadi = personelSoyadi;
	}

	public int getEkipId() {
		return ekipId;
	}

	public void setEkipId(int ekipId) {
		this.ekipId = ekipId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}   
    
}
