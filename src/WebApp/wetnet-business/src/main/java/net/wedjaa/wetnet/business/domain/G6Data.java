package net.wedjaa.wetnet.business.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author massimo ricci
 *
 */
@XmlRootElement
public class G6Data {

    @XmlAttribute(required = false)
    private List<Districts> districtsSelected;
    @XmlAttribute(required = false)
    private List<String> zoneSelected;
    @XmlAttribute(required = false)
    private List<String> municipalitySelected;
    @XmlAttribute(required = false)
    private Date startDate;
    @XmlAttribute(required = false)
    private Date endDate;
    @XmlAttribute(required = false)
    private List<Object> columns;

    public G6Data() {
        super();
        startDate = new Date();
        endDate = new Date();
        this.districtsSelected = new ArrayList<Districts>();
        this.zoneSelected = new ArrayList<String>();
        this.municipalitySelected = new ArrayList<String>();
        this.columns = new ArrayList<Object>();
    }

    public List<Districts> getDistrictsSelected() {
        return districtsSelected;
    }

    public void setDistrictsSelected(List<Districts> districtsSelected) {
        this.districtsSelected = districtsSelected;
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

    public List<String> getZoneSelected() {
        return zoneSelected;
    }

    public void setZoneSelected(List<String> zoneSelected) {
        this.zoneSelected = zoneSelected;
    }

    public List<String> getMunicipalitySelected() {
        return municipalitySelected;
    }

    public void setMunicipalitySelected(List<String> municipalitySelected) {
        this.municipalitySelected = municipalitySelected;
    }

    public List<Object> getColumns() {
        return columns;
    }

    public void setColumns(List<Object> columns) {
        this.columns = columns;
    }
}
