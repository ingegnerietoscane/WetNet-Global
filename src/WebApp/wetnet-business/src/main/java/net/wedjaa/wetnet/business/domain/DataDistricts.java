package net.wedjaa.wetnet.business.domain;

import java.util.Date;


/**
 * @author massimo ricci
 *
 */
public class DataDistricts {
    
    public static final String ID_DISTRICTS_FIELD = "districts_id_districts";
    
    
    private long idDistricts;
    private double value;
    
    /* GC - 22/10/2015 */
    //private int reliable;
    
    private Date timestamp;
    private String districtName;

    public long getIdDistricts() {
        return idDistricts;
    }

    public void setIdDistricts(long idDistricts) {
        this.idDistricts = idDistricts;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    /* GC - 22/10/2015 */
    /*
    public int getReliable() {
        return reliable;
    }

    public void setReliable(int reliable) {
        this.reliable = reliable;
    }
    */

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

}
