package net.wedjaa.wetnet.business.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author graziella cipolletti
 *
 */
@XmlRootElement
public class MeasuresMeterReading implements Comparable<MeasuresMeterReading> {

    
    @XmlAttribute(required = true)
    private Date timestamp;
    @XmlAttribute(required = true)
    private double value;
    @XmlAttribute(required = true)
    private long idUserreader;
    @XmlAttribute(required = true)
    private long idMeasures;
   
	@Override
	public int compareTo(MeasuresMeterReading o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	

	public long getIdUserreader() {
		return idUserreader;
	}

	public void setIdUserreader(long idUserreader) {
		this.idUserreader = idUserreader;
	}

	public long getIdMeasures() {
		return idMeasures;
	}

	public void setIdMeasures(long idMeasures) {
		this.idMeasures = idMeasures;
	}

	

	
}
