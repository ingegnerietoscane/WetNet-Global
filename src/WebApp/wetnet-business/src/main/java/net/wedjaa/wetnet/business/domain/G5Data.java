/**
 * 
 */
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
public class G5Data {

    @XmlAttribute(required = false)
    private Districts districtsSelected;
    @XmlAttribute(required = false)
    private String zoneSelected;
    @XmlAttribute(required = false)
    private String municipalitySelected;
    @XmlAttribute(required = false)
    private Date startDate;
    @XmlAttribute(required = false)
    private Date endDate;
    @XmlAttribute(required = false)
    private List<Object> columns;
    
    public G5Data() {
        super();
        this.startDate = new Date();
        this.endDate = new Date();
        this.columns = new ArrayList<Object>();
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
    
    public List<Object> getColumns() {
        return columns;
    }

    public void setColumns(List<Object> columns) {
        this.columns = columns;
    }
}
