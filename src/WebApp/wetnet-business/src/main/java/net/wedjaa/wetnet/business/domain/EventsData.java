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
public class EventsData {

    @XmlAttribute(required = false)
    private Districts districtsSelected;
    @XmlAttribute(required = false)
    private String zoneSelected;
    @XmlAttribute(required = false)
    private String municipalitySelected;
    @XmlAttribute(required = false)
    private String itemFlagged;
    @XmlAttribute(required = false)
    private Date startDate;
    @XmlAttribute(required = false)
    private Date endDate;
    @XmlAttribute(required = false)
    private List<Events> data;

    public EventsData() {
        super();
        startDate = new Date();
        endDate = new Date();
        data = new ArrayList<Events>();
    }

    public Districts getDistrictsSelected() {
        return districtsSelected;
    }

    public void setDistrictsSelected(Districts districtsSelected) {
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

    public List<Events> getData() {
        return data;
    }

    public void setData(List<Events> data) {
        this.data = data;
    }

    public String getZoneSelected() {
        return zoneSelected;
    }

    public void setZoneSelected(String zoneSelected) {
        this.zoneSelected = zoneSelected;
    }

    public String getMunicipalitySelected() {
        return municipalitySelected;
    }

    public void setMunicipalitySelected(String municipalitySelected) {
        this.municipalitySelected = municipalitySelected;
    }

    public String getItemFlagged() {
        return itemFlagged;
    }

    public void setItemFlagged(String itemFlagged) {
        this.itemFlagged = itemFlagged;
    }
}
