/**
 * 
 */
package net.wedjaa.wetnet.business.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author massimo ricci
 *
 */
public class DayStatisticJoinDistrictsJoinEnergy {

    private Date day;
    private double minNight;
    private double avgDay;
    private double volumeRealLosses;
    private double maxDay;
    private double minDay;
    private double range;
    private double standardDeviation;
    private double realLeakage;
    private double ied;
    private double epd;
    private double iela;
    private double evLowBand;
    private double evHighBand;
    private double householdNightUse;
    private double notHouseholdNightUse;
    
    /*GC 16/11/2015*/
    @XmlAttribute(required = false)
    private long idDistricts;
    @XmlAttribute(required = false)
    private String nameDistricts;
    /*
     * GC 04/11/2015
     */
    private double rangeAvg;
    
    public Date getDay() {
        return day;
    }
    public void setDay(Date day) {
        this.day = day;
    }
    public double getMinNight() {
        return minNight;
    }
    public void setMinNight(double minNight) {
        this.minNight = minNight;
    }
    public double getAvgDay() {
        return avgDay;
    }
    public void setAvgDay(double avgDay) {
        this.avgDay = avgDay;
    }
    public double getVolumeRealLosses() {
        return volumeRealLosses;
    }
    public void setVolumeRealLosses(double volumeRealLosses) {
        this.volumeRealLosses = volumeRealLosses;
    }
    public double getMaxDay() {
        return maxDay;
    }
    public void setMaxDay(double maxDay) {
        this.maxDay = maxDay;
    }
    public double getMinDay() {
        return minDay;
    }
    public void setMinDay(double minDay) {
        this.minDay = minDay;
    }
    public double getRange() {
        return range;
    }
    public void setRange(double range) {
        this.range = range;
    }
    public double getStandardDeviation() {
        return standardDeviation;
    }
    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
    public double getRealLeakage() {
        return realLeakage;
    }
    public void setRealLeakage(double realLeakage) {
        this.realLeakage = realLeakage;
    }
    public double getIed() {
        return ied;
    }
    public void setIed(double ied) {
        this.ied = ied;
    }
    public double getEpd() {
        return epd;
    }
    public void setEpd(double epd) {
        this.epd = epd;
    }
    public double getIela() {
        return iela;
    }
    public void setIela(double iela) {
        this.iela = iela;
    }
    public double getEvLowBand() {
        return evLowBand;
    }
    public void setEvLowBand(double evLowBand) {
        this.evLowBand = evLowBand;
    }
    public double getEvHighBand() {
        return evHighBand;
    }
    public void setEvHighBand(double evHighBand) {
        this.evHighBand = evHighBand;
    }
    public double getHouseholdNightUse() {
        return householdNightUse;
    }
    public void setHouseholdNightUse(double householdNightUse) {
        this.householdNightUse = householdNightUse;
    }
    public double getNotHouseholdNightUse() {
        return notHouseholdNightUse;
    }
    public void setNotHouseholdNightUse(double notHouseholdNightUse) {
        this.notHouseholdNightUse = notHouseholdNightUse;
    }
    
    /*
     * GC 04/11/2015
     */
	public double getRangeAvg() {
		return rangeAvg;
	}
	public void setRangeAvg(double rangeAvg) {
		this.rangeAvg = rangeAvg;
	}
	
	
	 /*GC 16/11/2015*/
	public long getIdDistricts() {
		return idDistricts;
	}
	public void setIdDistricts(long idDistricts) {
		this.idDistricts = idDistricts;
	}
	public String getNameDistricts() {
		return nameDistricts;
	}
	public void setNameDistricts(String nameDistricts) {
		this.nameDistricts = nameDistricts;
	}
    
}
