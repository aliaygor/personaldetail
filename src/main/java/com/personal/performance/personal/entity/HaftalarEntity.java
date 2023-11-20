package com.personal.performance.personal.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "haftalar")
public class HaftalarEntity {
	
	@Id
	@Column(name="hafta_sira")
	private Integer hafta_sira;

	@Column(name="tarih")
	private String tarih;
	
	@Column(name="calisma_saati")
	private Integer calisma_saati;
	
	@Column(name="createdate")
	private Date createdate;
	
	@Column(name="updatedate")
	private Date updatedate;

	public Integer getHafta_sira() {
		return hafta_sira;
	}

	public void setHafta_sira(Integer hafta_sira) {
		this.hafta_sira = hafta_sira;
	}

	public String getTarih() {
		return tarih;
	}

	public void setTarih(String tarih) {
		this.tarih = tarih;
	}

	public Integer getCalisma_saati() {
		return calisma_saati;
	}

	public void setCalisma_saati(Integer calisma_saati) {
		this.calisma_saati = calisma_saati;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	
}
