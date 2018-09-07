/**
 * 
 */
package net.wedjaa.wetnet.business.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author massimo ricci
 * @author alessandro vincelli
 *
 */
@XmlRootElement
public class DataMeasures implements Comparable<DataMeasures> {

    @XmlAttribute(required = false)
    private long measuresIdMeasures;
    @XmlAttribute(required = false)
    private Date timestamp;
    @XmlAttribute(required = false)
    private Double value;
    @XmlAttribute(required = false)
    private String nameMeasures;

    public long getMeasuresIdMeasures() {
        return measuresIdMeasures;
    }

    public void setMeasuresIdMeasures(long measuresIdMeasures) {
        this.measuresIdMeasures = measuresIdMeasures;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getNameMeasures() {
        return nameMeasures;
    }

    public void setNameMeasures(String nameMeasures) {
        this.nameMeasures = nameMeasures;
    }

    @Override
    public int compareTo(DataMeasures o) {
        if (o != null) {
            return getTimestamp().compareTo(o.getTimestamp());
        }
        return 0;
    }
}
