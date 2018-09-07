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
public class DistrictsBandsHistory {

    @XmlAttribute(required = false)
    private long districtsIdDistricts;
    @XmlAttribute(required = false)
    private Date timestamp;
    @XmlAttribute(required = false)
    private Double highBand;
    @XmlAttribute(required = false)
    private Double lowBand;
    
    
    /* GC 13/11/2015*/
    @XmlAttribute(required = false)
    private String districtName;
    
    public DistrictsBandsHistory(long districtsIdDistricts, Date timestamp, Double highBand, Double lowBand, String name) {
        super();
        this.districtsIdDistricts = districtsIdDistricts;
        this.timestamp = timestamp;
        this.highBand = highBand;
        this.lowBand = lowBand;
        this.districtName = name;
    }
      
    public DistrictsBandsHistory() {
        super();
    }
    public DistrictsBandsHistory(long districtsIdDistricts, Date timestamp, Double highBand, Double lowBand) {
        super();
        this.districtsIdDistricts = districtsIdDistricts;
        this.timestamp = timestamp;
        this.highBand = highBand;
        this.lowBand = lowBand;
    }
    public long getDistrictsIdDistricts() {
        return districtsIdDistricts;
    }
    public void setDistrictsIdDistricts(long districtsIdDistricts) {
        this.districtsIdDistricts = districtsIdDistricts;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public Double getHighBand() {
        return highBand;
    }
    public void setHighBand(Double highBand) {
        this.highBand = highBand;
    }
    public Double getLowBand() {
        return lowBand;
    }
    public void setLowBand(Double lowBand) {
        this.lowBand = lowBand;
    }
    
    /* GC 13/11/2015*/
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
    
}
