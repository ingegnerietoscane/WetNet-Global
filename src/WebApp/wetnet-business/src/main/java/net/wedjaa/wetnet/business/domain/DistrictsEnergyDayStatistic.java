/**
 * 
 */
package net.wedjaa.wetnet.business.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author massimo ricci
 *
 */
@XmlRootElement
public class DistrictsEnergyDayStatistic {

    @XmlAttribute(required = false)
    protected Date day;
    @XmlAttribute(required = false)
    protected int dayType;
    @XmlAttribute(required = false)
    protected Double epd;
    @XmlAttribute(required = false)
    protected Double ied;
    @XmlAttribute(required = false)
    protected Double iela;
    @XmlAttribute(required = false)
    protected long districtsIdDistricts;
    
    public DistrictsEnergyDayStatistic(){
        super();
    }
    
    public Date getDay() {
        return day;
    }
    public void setDay(Date day) {
        this.day = day;
    }
    public int getDayType() {
        return dayType;
    }
    public void setDayType(int dayType) {
        this.dayType = dayType;
    }
    
    public Double getEpd() {
        return epd;
    }

    public void setEpd(Double epd) {
        this.epd = epd;
    }

    public Double getIed() {
        return ied;
    }

    public void setIed(Double ied) {
        this.ied = ied;
    }

    public Double getIela() {
        return iela;
    }

    public void setIela(Double iela) {
        this.iela = iela;
    }

    public long getDistrictsIdDistricts() {
        return districtsIdDistricts;
    }
    public void setDistrictsIdDistricts(long districtsIdDistricts) {
        this.districtsIdDistricts = districtsIdDistricts;
    }  
}
