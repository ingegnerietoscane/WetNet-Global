package net.wedjaa.wetnet.business.dao.params;

import java.util.Date;

import net.wedjaa.wetnet.business.commons.DateUtil;

/**
 * classe di utilita' per passare parametri alle query sql su data_measures
 * 
 * @author alessandro vincelli
 *
 */
public class DataMeasuresFilter {

    public DataMeasuresFilter(String startdate, String enddate, long measuresIdMeasures) {
        super();
        this.startdate = startdate;
        this.enddate = enddate;
        this.measuresIdMeasures = measuresIdMeasures;
    }

    public DataMeasuresFilter(Date startDate, Date endDate, long idMeasures) {
        super();
        this.startdate = DateUtil.SDTF2SIMPLEUSA.print(startDate.getTime());
        this.enddate = DateUtil.SDTF2SIMPLEUSA.print(endDate.getTime());
        this.measuresIdMeasures = idMeasures;
    }
    
    public DataMeasuresFilter() {
        super();
    }

    private String startdate;
    private String enddate;
    private long measuresIdMeasures;

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public long getMeasuresIdMeasures() {
        return measuresIdMeasures;
    }

    public void setMeasuresIdMeasures(long measuresIdMeasures) {
        this.measuresIdMeasures = measuresIdMeasures;
    }

}
