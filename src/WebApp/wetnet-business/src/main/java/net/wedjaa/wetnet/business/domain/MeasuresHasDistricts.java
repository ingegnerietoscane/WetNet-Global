package net.wedjaa.wetnet.business.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
@XmlRootElement
public class MeasuresHasDistricts implements Comparable<MeasuresHasDistricts> {
    //`measures_id_measures`, `measures_connections_id_odbcdsn`, `districts_id_districts`, `sign`
    @XmlAttribute(required = false)
    private long measures_id_measures;
    @XmlAttribute(required = false)
    private String measures_name;
   
    /* GC - 22/10/2015
    @XmlAttribute(required = false)
    private long measures_connections_id_odbcdsn;
    */
    @XmlAttribute(required = false)
    private long districts_id_districts;
    @XmlAttribute(required = false)
    private String districts_name;
    @XmlAttribute(required = false)
    private long sign;

    public MeasuresHasDistricts() {
        super();
    }

    public MeasuresHasDistricts(long id_districts, long id_measures) {
        this();
        districts_id_districts = id_districts;
        measures_id_measures = id_measures;
    }

    /* GC - 22/10/2015
    public MeasuresHasDistricts(long id_districts, long id_measures, long sign, long connections_id_odbcdsn) {
        districts_id_districts = id_districts;
        measures_id_measures = id_measures;
        this.sign = sign;
        measures_connections_id_odbcdsn = connections_id_odbcdsn;
    }*/
    public MeasuresHasDistricts(long id_districts, long id_measures, long sign) {
        districts_id_districts = id_districts;
        measures_id_measures = id_measures;
        this.sign = sign;
    }

    public long getMeasures_id_measures() {
        return measures_id_measures;
    }

    public void setMeasures_id_measures(long measures_id_measures) {
        this.measures_id_measures = measures_id_measures;
    }

    /* GC - 22/10/2015
    public long getMeasures_connections_id_odbcdsn() {
        return measures_connections_id_odbcdsn;
    }

    public void setMeasures_connections_id_odbcdsn(long measures_connections_id_odbcdsn) {
        this.measures_connections_id_odbcdsn = measures_connections_id_odbcdsn;
    }
    */

    public long getDistricts_id_districts() {
        return districts_id_districts;
    }

    public void setDistricts_id_districts(long districts_id_districts) {
        this.districts_id_districts = districts_id_districts;
    }

    public String getMeasures_name() {
        return measures_name;
    }

    public void setMeasures_name(String measures_name) {
        this.measures_name = measures_name;
    }

    public String getDistricts_name() {
        return districts_name;
    }

    public void setDistricts_name(String districts_name) {
        this.districts_name = districts_name;
    }

    public long getSign() {
        return sign;
    }

    public void setSign(long sign) {
        this.sign = sign;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(MeasuresHasDistricts o) {
        if (o != null) {
            return getMeasures_name().compareTo(o.getMeasures_name());
        }
        return 0;
    }
}
