package com.personal.performance.personal.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "performans")
public class PerformansEntity {
	
    @Id
    @Column(name = "performans_id")
    private Integer performansId;
    
    @Column(name = "ekip_id")
    private Integer ekipId;

    @Column(name = "hafta_sira", nullable = false)
    private Integer haftaSira;

    @Column(name = "tarih", nullable = false)
    private String tarih;

    @Column(name = "personel_id", nullable = false)
    private int personelId;

    @Column(name = "kisi_calisma_saati", nullable = false)
    private Integer kisiCalismaSaati;

    @Column(name = "bakilan_cagri", nullable = false)
    private Integer bakilanCagri;

    @Column(name = "yenidenacilan_cagri", nullable = false)
    private Integer yenidenAcilanCagri;

    @Column(name = "bakilan_cagri_tam")
    private Integer bakilanCagriTam;

    @Column(name = "yeniden_acilan_cagri_tam")
    private Integer yenidenAcilanCagriTam;

    @Column(name = "yeniden_acilan_cagri_puani")
    private Long yenidenAcilanCagriPuani;

    @Column(name = "yonetici_puani")
    private Long yoneticiPuani;

    @Column(name = "ccs_puani")
    private Long ccsPuani; 

    @Column(name = "ypd_puani")
    private Long ypdPuani; 

    @Column(name = "ycs_puani")
    private Long ycsPuani; 

    @ManyToOne
    @JoinColumn(name = "personel_id", insertable = false, updatable = false)
    private CalisanEntity calisan;

    @ManyToOne
    @JoinColumn(name = "hafta_sira", insertable = false, updatable = false)
    private HaftalarEntity haftalar;
    
    
    
    public PerformansEntity() {
    }

	public PerformansEntity(Integer kisiCalismaSaati, Integer yenidenAcilanCagri, HaftalarEntity haftalar) {
		this.kisiCalismaSaati = kisiCalismaSaati;
		this.yenidenAcilanCagri = yenidenAcilanCagri;
		this.haftalar = haftalar;
	}

	public BigDecimal calculateYenidenAcilanCagriTam() {
//        BigDecimal yenidenAcilanCagriTam = BigDecimal.valueOf(haftalar.getCalisma_saati())
//                .multiply(BigDecimal.valueOf(yenidenAcilanCagri))
//                .divide(BigDecimal.valueOf(kisiCalismaSaati), 2, BigDecimal.ROUND_HALF_UP);
		BigDecimal yenidenAcilanCagriTam = new BigDecimal(0);
        if (!kisiCalismaSaati.equals(0) || !yenidenAcilanCagri.equals(0)) {
        	yenidenAcilanCagriTam = BigDecimal.valueOf(haftalar.getCalisma_saati())
                  .multiply(BigDecimal.valueOf(yenidenAcilanCagri))
                  .divide(BigDecimal.valueOf(kisiCalismaSaati), 2, BigDecimal.ROUND_HALF_UP);
        }
        return yenidenAcilanCagriTam;
    }
	
	public BigDecimal calculateBakilanCagriTam() {
//        BigDecimal cagriTam = BigDecimal.valueOf(haftalar.getCalisma_saati())
//                .multiply(BigDecimal.valueOf(bakilanCagri))
//                .divide(BigDecimal.valueOf(kisiCalismaSaati), 2, BigDecimal.ROUND_HALF_UP);
		BigDecimal bakilanCagriTam = new BigDecimal(0);
        if (!kisiCalismaSaati.equals(0) || !bakilanCagri.equals(0)) {
        	bakilanCagriTam = BigDecimal.valueOf(haftalar.getCalisma_saati())
                    .multiply(BigDecimal.valueOf(bakilanCagri))
                    .divide(BigDecimal.valueOf(kisiCalismaSaati), 2, BigDecimal.ROUND_HALF_UP);
        }
        return bakilanCagriTam;
    }

	public Integer getPerformansId() {
		return performansId;
	}

	public void setPerformansId(Integer performansId) {
		this.performansId = performansId;
	}

	public Integer getEkipId() {
		return ekipId;
	}

	public void setEkipId(Integer ekipId) {
		this.ekipId = ekipId;
	}

	public Integer getHaftaSira() {
		return haftaSira;
	}

	public void setHaftaSira(Integer haftaSira) {
		this.haftaSira = haftaSira;
	}

	public String getTarih() {
		return tarih;
	}

	public void setTarih(String tarih) {
		this.tarih = tarih;
	}

	public int getPersonelId() {
		return personelId;
	}

	public void setPersonelId(int personelId) {
		this.personelId = personelId;
	}

	public Integer getKisiCalismaSaati() {
		return kisiCalismaSaati;
	}

	public void setKisiCalismaSaati(Integer kisiCalismaSaati) {
		this.kisiCalismaSaati = kisiCalismaSaati;
	}

	public Integer getBakilanCagri() {
		return bakilanCagri;
	}

	public void setBakilanCagri(Integer bakilanCagri) {
		this.bakilanCagri = bakilanCagri;
	}

	public Integer getYenidenAcilanCagri() {
		return yenidenAcilanCagri;
	}

	public void setYenidenAcilanCagri(Integer yenidenAcilanCagri) {
		this.yenidenAcilanCagri = yenidenAcilanCagri;
	}

	public Integer getBakilanCagriTam() {
		return bakilanCagriTam;
	}

	public void setBakilanCagriTam(Integer bakilanCagriTam) {
		this.bakilanCagriTam = bakilanCagriTam;
	}

	public Integer getYenidenAcilanCagriTam() {
		return yenidenAcilanCagriTam;
	}

	public void setYenidenAcilanCagriTam(Integer yenidenAcilanCagriTam) {
		this.yenidenAcilanCagriTam = yenidenAcilanCagriTam;
	}

	public Long getYenidenAcilanCagriPuani() {
		return yenidenAcilanCagriPuani;
	}

	public void setYenidenAcilanCagriPuani(Long yenidenAcilanCagriPuani) {
		this.yenidenAcilanCagriPuani = yenidenAcilanCagriPuani;
	}

	public Long getYoneticiPuani() {
		return yoneticiPuani;
	}

	public void setYoneticiPuani(Long yoneticiPuani) {
		this.yoneticiPuani = yoneticiPuani;
	}

	public Long getCcsPuani() {
		return ccsPuani;
	}

	public void setCcsPuani(Long ccsPuani) {
		this.ccsPuani = ccsPuani;
	}

	public Long getYpdPuani() {
		return ypdPuani;
	}

	public void setYpdPuani(Long ypdPuani) {
		this.ypdPuani = ypdPuani;
	}

	public Long getYcsPuani() {
		return ycsPuani;
	}

	public void setYcsPuani(Long ycsPuani) {
		this.ycsPuani = ycsPuani;
	}

	public CalisanEntity getCalisan() {
		return calisan;
	}

	public void setCalisan(CalisanEntity calisan) {
		this.calisan = calisan;
	}

	public HaftalarEntity getHaftalar() {
		return haftalar;
	}

	public void setHaftalar(HaftalarEntity haftalar) {
		this.haftalar = haftalar;
	}
        
}
