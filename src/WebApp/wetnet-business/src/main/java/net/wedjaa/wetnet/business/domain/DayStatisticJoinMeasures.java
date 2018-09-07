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
public class DayStatisticJoinMeasures {

    private Date day;
    private double minNight;
    private double avgDay;
    private double maxDay;
    private double minDay;
    private double range;
    private double standardDeviation;
    private double alarmMinThreshold;
    private double alarmMaxThreshold;
    
    /*
     * GC 04/11/2015
     */
    private double rangeAvg;
    
    
    /*GC 16/11/2015*/
    @XmlAttribute(required = false)
    private long idMeasures;
    @XmlAttribute(required = false)
    private String nameMeasures;
    
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
    public double getAlarmMinThreshold() {
        return alarmMinThreshold;
    }
    public void setAlarmMinThreshold(double alarmMinThreshold) {
        this.alarmMinThreshold = alarmMinThreshold;
    }
    public double getAlarmMaxThreshold() {
        return alarmMaxThreshold;
    }
    public void setAlarmMaxThreshold(double alarmMaxThreshold) {
        this.alarmMaxThreshold = alarmMaxThreshold;
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
	public long getIdMeasures() {
		return idMeasures;
	}
	public void setIdMeasures(long idMeasures) {
		this.idMeasures = idMeasures;
	}
	public String getNameMeasures() {
		return nameMeasures;
	}
	public void setNameMeasures(String nameMeasures) {
		this.nameMeasures = nameMeasures;
	}
	
	
}
