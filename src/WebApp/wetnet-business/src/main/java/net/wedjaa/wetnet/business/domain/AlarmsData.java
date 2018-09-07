package net.wedjaa.wetnet.business.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author graziella cipolletti
 *
 */
@XmlRootElement
public class AlarmsData {

    @XmlAttribute(required = false)
    private Measures measuresSelected;
   
    @XmlAttribute(required = false)
    private List<Alarms> data;
    
    @XmlAttribute(required = false)
    private boolean closedAlarms;

    public AlarmsData() {
        super();
       data = new ArrayList<Alarms>();
    }

	public Measures getMeasuresSelected() {
		return measuresSelected;
	}

	public void setMeasuresSelected(Measures measuresSelected) {
		this.measuresSelected = measuresSelected;
	}

	public List<Alarms> getData() {
		return data;
	}

	public void setData(List<Alarms> data) {
		this.data = data;
	}

	public boolean isClosedAlarms() {
		return closedAlarms;
	}

	public void setClosedAlarms(boolean closedAlarms) {
		this.closedAlarms = closedAlarms;
	}


   
}
