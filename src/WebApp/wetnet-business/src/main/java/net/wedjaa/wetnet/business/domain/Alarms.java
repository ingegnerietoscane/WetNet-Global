package net.wedjaa.wetnet.business.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author graziella cipolletti
 *
 */
@XmlRootElement
public class Alarms implements Comparable<Alarms> {
    @XmlAttribute(required = false)
    private long measures_id_measures;
    @XmlAttribute(required = false)
    private Date timestamp;
    @XmlAttribute(required = false)
    private int alarm_type;
    @XmlAttribute(required = false)
    private int event_type;
    @XmlAttribute(required = false)
    private double alarm_value;
    @XmlAttribute(required = false)
    private double reference_value;
     @XmlAttribute(required = false)
    private String duration;
     @XmlAttribute(required = false)
     private String measures_name;
    
  
	public long getMeasures_id_measures() {
		return measures_id_measures;
	}



	public void setMeasures_id_measures(long measures_id_measures) {
		this.measures_id_measures = measures_id_measures;
	}



	public Date getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}



	public int getAlarm_type() {
		return alarm_type;
	}



	public void setAlarm_type(int alarm_type) {
		this.alarm_type = alarm_type;
	}



	public int getEvent_type() {
		return event_type;
	}



	public void setEvent_type(int event_type) {
		this.event_type = event_type;
	}



	public double getAlarm_value() {
		return alarm_value;
	}



	public void setAlarm_value(double alarm_value) {
		this.alarm_value = alarm_value;
	}



	public double getReference_value() {
		return reference_value;
	}



	public void setReference_value(double reference_value) {
		this.reference_value = reference_value;
	}



	public String getDuration() {
		return duration;
	}



	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	
	 

    @Override
    public int compareTo(Alarms o) {
        if (o != null) {
        	return getTimestamp().compareTo(o.getTimestamp());
        }
        return 0;
    }



	public String getMeasures_name() {
		return measures_name;
	}



	public void setMeasures_name(String measures_name) {
		this.measures_name = measures_name;
	}



}
