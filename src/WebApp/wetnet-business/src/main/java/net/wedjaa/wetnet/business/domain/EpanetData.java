package net.wedjaa.wetnet.business.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author alessandro vincelli
 *
 */
@XmlRootElement
public class EpanetData {

    @XmlAttribute(required = false)
    private List<Object> columns;
    @XmlAttribute(required = false)
    private List<Integer> years;
    @XmlAttribute(required = false)
    private List<Integer> month;
    @XmlAttribute(required = false)
    private Integer yearSelected;
    @XmlAttribute(required = false)
    private List<Integer> monthsSelected;
    @XmlAttribute(required = false)
    private Districts districtsSelected;
    @XmlAttribute(required = false)
    private List<Measures> measuresSelected;
    @XmlAttribute(required = false)
    private Date startDate;
    @XmlAttribute(required = false)
    private Date endDate;
    //utilizzato per restituire i dati del file generato
    @XmlAttribute(required = false)
    private String responseDataFile;
    //utilizzato per restituire il nome del file generato
    @XmlAttribute(required = false)
    private String responseFileName;

    public EpanetData() {
        super();
        columns = new ArrayList<Object>();
        years = new ArrayList<Integer>();
        month = new ArrayList<Integer>();
        monthsSelected = new ArrayList<Integer>();
        measuresSelected = new ArrayList<Measures>();
        startDate = new Date();
        endDate = new Date();
        yearSelected = 0;
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

    public List<Integer> getMonth() {
        return month;
    }

    public void setMonth(List<Integer> month) {
        this.month = month;
    }

    public Integer getYearSelected() {
        return yearSelected;
    }

    public void setYearSelected(Integer yearSelected) {
        this.yearSelected = yearSelected;
    }

    public List<Integer> getMonthsSelected() {
        return monthsSelected;
    }

    public void setMonthsSelected(List<Integer> monthsSelected) {
        this.monthsSelected = monthsSelected;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Districts getDistrictsSelected() {
        return districtsSelected;
    }

    public void setDistrictsSelected(Districts districtsSelected) {
        this.districtsSelected = districtsSelected;
    }

    public List<Measures> getMeasuresSelected() {
        return measuresSelected;
    }

    public void setMeasuresSelected(List<Measures> measuresSelected) {
        this.measuresSelected = measuresSelected;
    }

    public String getResponseDataFile() {
        return responseDataFile;
    }

    public void setResponseDataFile(String responseDataFile) {
        this.responseDataFile = responseDataFile;
    }

    public String getResponseFileName() {
        return responseFileName;
    }

    public void setResponseFileName(String responseFileName) {
        this.responseFileName = responseFileName;
    }

}
