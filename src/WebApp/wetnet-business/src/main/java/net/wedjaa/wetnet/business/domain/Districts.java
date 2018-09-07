package net.wedjaa.wetnet.business.domain;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author massimo ricci
 * @author alessandro vincelli
 *
 */
@XmlRootElement
public class Districts implements Comparable<Districts> {

    @XmlAttribute(required = false)
    private long idDistricts;
    @XmlAttribute(required = false)
    private String name;
    @XmlAttribute(required = false)
    private String zone;
    @XmlAttribute(required = false)
    private Long dClass;
    @XmlAttribute(required = false)
    private String municipality;
    @XmlAttribute(required = false)
    private Long inhabitants;
    
    @XmlAttribute(required = false)
    @XmlTransient
    /***GC 04/12/2015****/
    //private List<Measures> measures;
    private List<MeasuresWithSign> measures;
    
    
    //***RC 03/11/2015***
    @XmlAttribute(required = false)
    private String waterAuthority;
    @XmlAttribute(required = false)
    private int mapLevel;
	@XmlAttribute(required = false)
    private String aeegCode;
	@XmlAttribute(required = false)
    private String gisCode;
	@XmlAttribute(required = false)
    private String withdrawalOperationalArea;
	@XmlAttribute(required = false)
    private String withdrawalArea;
	@XmlAttribute(required = false)
    private int type;
	//***END***

    private Date update_timestamp;
    private Date min_night_start_time;
    private Date min_night_stop_time;
    
    /* GC - 22/10/2015 */ 
	/*
    private Date max_day_start_time_1;
    private Date max_day_stop_time_1;
    private Date max_day_start_time_2;
    private Date max_day_stop_time_2;
    private Date max_day_start_time_3;
    private Date max_day_stop_time_3;
    */
    private double unitary_phisiological_nigth_demand;
    private double properties;
    private double rewarded_water;
    private double billing;
    private double not_household_night_use;
    private double length_main;
    private double average_zone_night_pressure;
    private double household_night_use;
    private double alpha_emitter_exponent;
    private String sap_code;
    private boolean ev_bands_autoupdate;
    private boolean ev_enable;
    private double ev_high_band;
    private double ev_low_band;
    private double ev_statistic_high_band;
    private double ev_statistic_low_band;
    private int ev_variable_type;
    //la data deve essere formattata, altrimenti il date picker puo' non validare la data in alcune situazioni
    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date ev_last_good_sample_day;
    private int ev_last_good_samples;
    private int ev_alpha;
    private int ev_samples_trigger;
    private String notes;
    private String map;

    @XmlAttribute(required = false)
    private double old_ev_high_band;
    @XmlAttribute(required = false)
    private double old_ev_low_band;
    
    public long getIdDistricts() {
        return idDistricts;
    }

    public void setIdDistricts(long idDistricts) {
        this.idDistricts = idDistricts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getdClass() {
        return dClass;
    }

    public void setdClass(Long dClass) {
        this.dClass = dClass;
    }

    public Long getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(Long inhabitants) {
        this.inhabitants = inhabitants;
    }

    
    /***GC 04/12/2015****/
    /*
    public List<Measures> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measures> measures) {
        this.measures = measures;
    }*/
    
    
    public List<MeasuresWithSign> getMeasures() {
        return measures;
    }

    public void setMeasures(List<MeasuresWithSign> measures) {
        this.measures = measures;
    }
    /***END****/

    public Date getUpdate_timestamp() {
        return update_timestamp;
    }

    public void setUpdate_timestamp(Date update_timestamp) {
        this.update_timestamp = update_timestamp;
    }

    public Date getMin_night_start_time() {
        return min_night_start_time;
    }

    public void setMin_night_start_time(Date min_night_start_time) {
        this.min_night_start_time = min_night_start_time;
    }

    public Date getMin_night_stop_time() {
        return min_night_stop_time;
    }

    public void setMin_night_stop_time(Date min_night_stop_time) {
        this.min_night_stop_time = min_night_stop_time;
    }

    /* GC - 22/10/2015 */ 
	/*
    public Date getMax_day_start_time_1() {
        return max_day_start_time_1;
    }

    public void setMax_day_start_time_1(Date max_day_start_time_1) {
        this.max_day_start_time_1 = max_day_start_time_1;
    }

    public Date getMax_day_stop_time_1() {
        return max_day_stop_time_1;
    }

    public void setMax_day_stop_time_1(Date max_day_stop_time_1) {
        this.max_day_stop_time_1 = max_day_stop_time_1;
    }

    public Date getMax_day_start_time_2() {
        return max_day_start_time_2;
    }

    public void setMax_day_start_time_2(Date max_day_start_time_2) {
        this.max_day_start_time_2 = max_day_start_time_2;
    }

    public Date getMax_day_stop_time_2() {
        return max_day_stop_time_2;
    }

    public void setMax_day_stop_time_2(Date max_day_stop_time_2) {
        this.max_day_stop_time_2 = max_day_stop_time_2;
    }

    public Date getMax_day_start_time_3() {
        return max_day_start_time_3;
    }

    public void setMax_day_start_time_3(Date max_day_start_time_3) {
        this.max_day_start_time_3 = max_day_start_time_3;
    }

    public Date getMax_day_stop_time_3() {
        return max_day_stop_time_3;
    }

    public void setMax_day_stop_time_3(Date max_day_stop_time_3) {
        this.max_day_stop_time_3 = max_day_stop_time_3;
    }
    */

    public double getUnitary_phisiological_nigth_demand() {
        return unitary_phisiological_nigth_demand;
    }

    public void setUnitary_phisiological_nigth_demand(double unitary_phisiological_nigth_demand) {
        this.unitary_phisiological_nigth_demand = unitary_phisiological_nigth_demand;
    }

    public double getProperties() {
        return properties;
    }

    public void setProperties(double properties) {
        this.properties = properties;
    }

    public double getRewarded_water() {
        return rewarded_water;
    }

    public void setRewarded_water(double rewarded_water) {
        this.rewarded_water = rewarded_water;
    }

    public double getBilling() {
        return billing;
    }

    public void setBilling(double billing) {
        this.billing = billing;
    }

    public double getNot_household_night_use() {
        return not_household_night_use;
    }

    public void setNot_household_night_use(double not_household_night_use) {
        this.not_household_night_use = not_household_night_use;
    }

    public double getLength_main() {
        return length_main;
    }

    public void setLength_main(double length_main) {
        this.length_main = length_main;
    }

    public double getAverage_zone_night_pressure() {
        return average_zone_night_pressure;
    }

    public void setAverage_zone_night_pressure(double average_zone_night_pressure) {
        this.average_zone_night_pressure = average_zone_night_pressure;
    }

    public double getHousehold_night_use() {
        return household_night_use;
    }

    public void setHousehold_night_use(double household_night_use) {
        this.household_night_use = household_night_use;
    }

    public double getAlpha_emitter_exponent() {
        return alpha_emitter_exponent;
    }

    public void setAlpha_emitter_exponent(double alpha_emitter_exponent) {
        this.alpha_emitter_exponent = alpha_emitter_exponent;
    }

    public String getSap_code() {
        return sap_code;
    }

    public void setSap_code(String sap_code) {
        this.sap_code = sap_code;
    }

    public boolean isEv_bands_autoupdate() {
        return ev_bands_autoupdate;
    }

    public void setEv_bands_autoupdate(boolean ev_bands_autoupdate) {
        this.ev_bands_autoupdate = ev_bands_autoupdate;
    }

    public boolean isEv_enable() {
        return ev_enable;
    }

    public void setEv_enable(boolean ev_enable) {
        this.ev_enable = ev_enable;
    }

    public double getEv_high_band() {
        return ev_high_band;
    }

    public void setEv_high_band(double ev_high_band) {
        this.ev_high_band = ev_high_band;
    }

    public double getEv_low_band() {
        return ev_low_band;
    }

    public void setEv_low_band(double ev_low_band) {
        this.ev_low_band = ev_low_band;
    }

    public double getEv_statistic_high_band() {
        return ev_statistic_high_band;
    }

    public void setEv_statistic_high_band(double ev_statistic_high_band) {
        this.ev_statistic_high_band = ev_statistic_high_band;
    }

    public double getEv_statistic_low_band() {
        return ev_statistic_low_band;
    }

    public void setEv_statistic_low_band(double ev_statistic_low_band) {
        this.ev_statistic_low_band = ev_statistic_low_band;
    }

    public int getEv_variable_type() {
        return ev_variable_type;
    }

    public void setEv_variable_type(int ev_variable_type) {
        this.ev_variable_type = ev_variable_type;
    }

    public Date getEv_last_good_sample_day() {
        return ev_last_good_sample_day;
    }

    public void setEv_last_good_sample_day(Date ev_last_good_sample_day) {
        this.ev_last_good_sample_day = ev_last_good_sample_day;
    }

    public int getEv_last_good_samples() {
        return ev_last_good_samples;
    }

    public void setEv_last_good_samples(int ev_last_good_samples) {
        this.ev_last_good_samples = ev_last_good_samples;
    }

    public int getEv_alpha() {
        return ev_alpha;
    }

    public void setEv_alpha(int ev_alpha) {
        this.ev_alpha = ev_alpha;
    }

    public int getEv_samples_trigger() {
        return ev_samples_trigger;
    }

    public void setEv_samples_trigger(int ev_samples_trigger) {
        this.ev_samples_trigger = ev_samples_trigger;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public double getOld_ev_high_band() {
        return old_ev_high_band;
    }

    public void setOld_ev_high_band(double old_ev_high_band) {
        this.old_ev_high_band = old_ev_high_band;
    }

    public double getOld_ev_low_band() {
        return old_ev_low_band;
    }

    public void setOld_ev_low_band(double old_ev_low_band) {
        this.old_ev_low_band = old_ev_low_band;
    }

    @Override
    public int compareTo(Districts o) {
        if (o != null) {
            return getName().compareToIgnoreCase(o.getName());
        }
        return 0;
    }
    
    //***RC 03/11/2015***
    public String getWaterAuthority() {
		return waterAuthority;
	}

	public void setWaterAuthority(String waterAuthority) {
		this.waterAuthority = waterAuthority;
	}

	public int getMapLevel() {
		return mapLevel;
	}

	public void setMapLevel(int mapLevel) {
		this.mapLevel = mapLevel;
	}

	public String getAeegCode() {
		return aeegCode;
	}

	public void setAeegCode(String aeegCode) {
		this.aeegCode = aeegCode;
	}

	public String getGisCode() {
		return gisCode;
	}

	public void setGisCode(String gisCode) {
		this.gisCode = gisCode;
	}

	public String getWithdrawalOperationalArea() {
		return withdrawalOperationalArea;
	}

	public void setWithdrawalOperationalArea(String withdrawalOperationalArea) {
		this.withdrawalOperationalArea = withdrawalOperationalArea;
	}

	public String getWithdrawalArea() {
		return withdrawalArea;
	}

	public void setWithdrawalArea(String withdrawalArea) {
		this.withdrawalArea = withdrawalArea;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}        
    //***END***
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Districts){
            return this.idDistricts == ((Districts) obj).getIdDistricts();
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return (int) this.idDistricts;
    }
}
