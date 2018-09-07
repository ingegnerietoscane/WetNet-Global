package net.wedjaa.wetnet.web.rest.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class C3Object {

    public C3Object() {
        super();
        this.columns = new ArrayList<Object>();
        this.years = new ArrayList<Integer>();
        this.months = new ArrayList<Integer>();
        this.weeks = new ArrayList<Integer>();
        this.days = new ArrayList<Integer>();
    }

    @XmlAttribute
    private String x;

    @XmlAttribute
    private List<Object> columns;

    @XmlAttribute
    private List<Integer> years;
    @XmlAttribute
    private List<Integer> months;
    @XmlAttribute
    private List<Integer> weeks;
    @XmlAttribute
    private List<Integer> days;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public List<Object> getColumns() {
        return columns;
    }

    public void setColumns(List<Object> columns) {
        this.columns = columns;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public List<Integer> getMonths() {
        return months;
    }

    public void setMonths(List<Integer> months) {
        this.months = months;
    }

    public List<Integer> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Integer> weeks) {
        this.weeks = weeks;
    }

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }

}
