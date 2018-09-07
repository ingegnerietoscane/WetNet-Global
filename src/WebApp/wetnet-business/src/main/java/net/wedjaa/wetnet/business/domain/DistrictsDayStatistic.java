package net.wedjaa.wetnet.business.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author massimo ricci
 *
 */
@XmlRootElement
public class DistrictsDayStatistic {

    @XmlAttribute(required = false)
	private long idDistricts;
    @XmlAttribute(required = false)
	private String day;
    @XmlAttribute(required = false)
	private long dayType;
    @XmlAttribute(required = false)
	private Double minNight;
    @XmlAttribute(required = false)
	private Double minDay;
    @XmlAttribute(required = false)
	private Double maxDay;
    @XmlAttribute(required = false)
	private Double standardDeviation;
    @XmlAttribute(required = false)
	private Double volumeRealLosses;
    @XmlAttribute(required = false)
	private Double avgDay;
    @XmlAttribute(required = false)
	private Double realLeakage;
    @XmlAttribute(required = false)
	private Double range;
    @XmlAttribute(required = false)
	private Double mnfPressure;
    @XmlAttribute(required = false)
	private Double minNightPressure;
    @XmlAttribute(required = false)
    private Date date;
	
	public long getIdDistricts() {
		return idDistricts;
	}
	public void setIdDistricts(long idDistricts) {
		this.idDistricts = idDistricts;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Double getMinNight() {
		return minNight;
	}
	public void setMinNight(Double minNight) {
		this.minNight = minNight;
	}
	public Double getAvgDay() {
		return avgDay;
	}
	public void setAvgDay(Double avgDay) {
		this.avgDay = avgDay;
	}
	public Double getRealLeakage() {
		return realLeakage;
	}
	public void setRealLeakage(Double realLeakage) {
		this.realLeakage = realLeakage;
	}
    public Double getMnfPressure() {
        return mnfPressure;
    }
    public void setMnfPressure(Double mnfPressure) {
        this.mnfPressure = mnfPressure;
    }
    public long getDayType() {
        return dayType;
    }
    public void setDayType(long dayType) {
        this.dayType = dayType;
    }
    public Double getMinDay() {
        return minDay;
    }
    public void setMinDay(Double minDay) {
        this.minDay = minDay;
    }
    public Double getMaxDay() {
        return maxDay;
    }
    public void setMaxDay(Double maxDay) {
        this.maxDay = maxDay;
    }
    public Double getStandardDeviation() {
        return standardDeviation;
    }
    public void setStandardDeviation(Double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
    public Double getVolumeRealLosses() {
        return volumeRealLosses;
    }
    public void setVolumeRealLosses(Double volumeRealLosses) {
        this.volumeRealLosses = volumeRealLosses;
    }
    public Double getRange() {
        return range;
    }
    public void setRange(Double range) {
        this.range = range;
    }
    public Double getMinNightPressure() {
        return minNightPressure;
    }
    public void setMinNightPressure(Double minNightPressure) {
        this.minNightPressure = minNightPressure;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
	
}
