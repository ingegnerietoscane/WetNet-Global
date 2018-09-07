package net.wedjaa.wetnet.business.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author massimo ricci
 * @author alessandro vincelli
 *
 */
@XmlRootElement
public class MeasuresDayStatistic implements Comparable<MeasuresDayStatistic> {

    private Date day;
    private int day_type;
    private double min_night;
    private double min_day;
    private double max_day;
    private double avg_day;
    private double standard_deviation;
    private double range;
    private int measures_id_measures;
    
    /* GC - 22/10/2015
    private int measures_connections_id_odbcdsn;
	*/
    
    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public int getDay_type() {
        return day_type;
    }

    public void setDay_type(int day_type) {
        this.day_type = day_type;
    }

    public double getMin_night() {
        return min_night;
    }

    public void setMin_night(double min_night) {
        this.min_night = min_night;
    }

    public double getMin_day() {
        return min_day;
    }

    public void setMin_day(double min_day) {
        this.min_day = min_day;
    }

    public double getMax_day() {
        return max_day;
    }

    public void setMax_day(double max_day) {
        this.max_day = max_day;
    }

    public double getAvg_day() {
        return avg_day;
    }

    public void setAvg_day(double avg_day) {
        this.avg_day = avg_day;
    }

    public double getStandard_deviation() {
        return standard_deviation;
    }

    public void setStandard_deviation(double standard_deviation) {
        this.standard_deviation = standard_deviation;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public int getMeasures_id_measures() {
        return measures_id_measures;
    }

    public void setMeasures_id_measures(int measures_id_measures) {
        this.measures_id_measures = measures_id_measures;
    }

    /* GC - 22/10/2015
    public int getMeasures_connections_id_odbcdsn() {
        return measures_connections_id_odbcdsn;
    }

    public void setMeasures_connections_id_odbcdsn(int measures_connections_id_odbcdsn) {
        this.measures_connections_id_odbcdsn = measures_connections_id_odbcdsn;
    }
	*/
    
    @Override
    public int compareTo(MeasuresDayStatistic o) {
        if (o != null) {
            return getDay().compareTo(o.getDay());
        }
        return 0;
    }
}
