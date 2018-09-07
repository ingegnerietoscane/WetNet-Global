package net.wedjaa.wetnet.business.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author massimo ricci
 * @author alessandro vincelli
 *
 */
@XmlRootElement
public class Events implements Comparable<Events> {
    @XmlAttribute(required = false)
    private long districts_id_districts;
    @XmlAttribute(required = false)
    private String district_name;
    @XmlAttribute(required = false)
    private String description;
    @XmlAttribute(required = false)
    private String type;
    @XmlAttribute(required = false)
    private double ranking;
    @XmlAttribute(required = false)
    private double delta_value;
    @XmlAttribute(required = false)
    private Date day;
    @XmlAttribute(required = false)
    private long duration;
    @XmlAttribute(required = false)
    private double value;
    
    /* GC 11/11/2015*/
    @XmlAttribute(required = false)
    private int district_ev_variable_type;
    
    public long getDistricts_id_districts() {
        return districts_id_districts;
    }

    public void setDistricts_id_districts(long districts_id_districts) {
        this.districts_id_districts = districts_id_districts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRanking() {
        return ranking;
    }

    public void setRanking(double ranking) {
        this.ranking = ranking;
    }

    public double getDelta_value() {
        return delta_value;
    }

    public void setDelta_value(double delta_value) {
        this.delta_value = delta_value;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int compareTo(Events o) {
        if (o != null) {
            return getDay().compareTo(o.getDay());
        }
        return 0;
    }

	public int getDistrict_ev_variable_type() {
		return district_ev_variable_type;
	}

	public void setDistrict_ev_variable_type(int district_ev_variable_type) {
		this.district_ev_variable_type = district_ev_variable_type;
	}
}
