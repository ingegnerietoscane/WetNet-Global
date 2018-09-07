package net.wedjaa.wetnet.business.dao.params;

import java.util.Date;

import net.wedjaa.wetnet.business.commons.DateUtil;
import net.wedjaa.wetnet.business.domain.Users;

/**
 * classe di utilita' per passare parametri alle query sql su data_districs
 * 
 * @author alessandro vincelli
 *
 */
public class DataDistrictsFilter {

    private String startdate;
    private String enddate;
    private long idDistrict;
    private String zone;
    private String municipality;
    private Users user;
    private Long idusers;
    
    public DataDistrictsFilter(Date startDate, Date endDate) {
        super();
        this.startdate = DateUtil.SDTF2SIMPLEUSA.print(startDate.getTime());
        this.enddate = DateUtil.SDTF2SIMPLEUSA.print(endDate.getTime());
    }
    
    public DataDistrictsFilter(Date startDate, Date endDate, long idDistrict) {
        super();
        this.startdate = DateUtil.SDTF2SIMPLEUSA.print(startDate.getTime());
        this.enddate = DateUtil.SDTF2SIMPLEUSA.print(endDate.getTime());
        this.idDistrict = idDistrict;
    }
    
    public DataDistrictsFilter(Date startDate, Date endDate, String zone, String municipality) {
        super();
        this.startdate = DateUtil.SDTF2SIMPLEUSA.print(startDate.getTime());
        this.enddate = DateUtil.SDTF2SIMPLEUSA.print(endDate.getTime());
        this.zone = zone;
        this.municipality = municipality;
    }
    
    public DataDistrictsFilter(Users user, Date startDate, Date endDate) {
        super();
        this.startdate = DateUtil.SDTF2SIMPLEUSA.print(startDate.getTime());
        this.enddate = DateUtil.SDTF2SIMPLEUSA.print(endDate.getTime());
        this.user = user;
        this.idusers = (user != null) ? user.getIdusers() : null;
    }
    
    public DataDistrictsFilter(Users user, Date startDate, Date endDate, long idDistrict) {
        super();
        this.startdate = DateUtil.SDTF2SIMPLEUSA.print(startDate.getTime());
        this.enddate = DateUtil.SDTF2SIMPLEUSA.print(endDate.getTime());
        this.idDistrict = idDistrict;
        this.user = user;
        this.idusers = (user != null) ? user.getIdusers() : null;
    }
    
    public DataDistrictsFilter(Users user, Date startDate, Date endDate, String zone, String municipality) {
        super();
        this.startdate = DateUtil.SDTF2SIMPLEUSA.print(startDate.getTime());
        this.enddate = DateUtil.SDTF2SIMPLEUSA.print(endDate.getTime());
        this.zone = zone;
        this.municipality = municipality;
        this.user = user;
        this.idusers = (user != null) ? user.getIdusers() : null;
    }

    public DataDistrictsFilter() {
        super();
    }

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

    public long getIdDistrict() {
        return idDistrict;
    }

    public void setIdDistrict(long idDistrict) {
        this.idDistrict = idDistrict;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Long getIdusers() {
        return idusers;
    }

    public void setIdusers(Long idusers) {
        this.idusers = idusers;
    }
}
