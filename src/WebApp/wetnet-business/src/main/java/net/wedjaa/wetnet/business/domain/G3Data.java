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
 * @author alessandro vincelli, massimo ricci
 *
 */
@XmlRootElement
public class G3Data {

    @XmlAttribute(required = false)
    private Districts districtsSelected;
    @XmlAttribute(required = false)
    private Date startDate;
    @XmlAttribute(required = false)
    private Date endDate;
    @XmlAttribute(required = false)
    private List<Object> data;
    @XmlAttribute(required = false)
    private String minNightStartTime;
    @XmlAttribute(required = false)
    private String minNightStopTime;
    
    /* GC 03/11/2015 */
    @XmlAttribute(required = false)
    private List<Object> medie;
    
    
    /* GC 06/11/2015 */
    @XmlAttribute(required = false)
    private double coeffDeterminazione;
    @XmlAttribute(required = false)
    private double indCorrelazione;
    

    public G3Data() {
        super();
        startDate = new Date();
        endDate = new Date();
        data = new ArrayList<Object>();
        
        /* GC 03/11/2015 */
        medie = new ArrayList<Object>();
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

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public String getMinNightStartTime() {
        return minNightStartTime;
    }

    public void setMinNightStartTime(String minNightStartTime) {
        this.minNightStartTime = minNightStartTime;
    }

    public String getMinNightStopTime() {
        return minNightStopTime;
    }

    public void setMinNightStopTime(String minNightStopTime) {
        this.minNightStopTime = minNightStopTime;
    }

    
    /* GC 03/11/2015 */
	public List<Object> getMedie() {
		return medie;
	}

	public void setMedie(List<Object> medie) {
		this.medie = medie;
	}

	 /* GC 06/11/2015 */
	public double getCoeffDeterminazione() {
		return coeffDeterminazione;
	}

	public void setCoeffDeterminazione(double coeffDeterminazione) {
		this.coeffDeterminazione = coeffDeterminazione;
	}

	public double getIndCorrelazione() {
		return indCorrelazione;
	}

	public void setIndCorrelazione(double indCorrelazione) {
		this.indCorrelazione = indCorrelazione;
	}
    
}
