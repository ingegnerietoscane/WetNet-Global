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
public class Measures implements Comparable<Measures> {

    @XmlAttribute(required = false)
    private long idMeasures;
    @XmlAttribute(required = false)
    private String name;
    @XmlAttribute(required = false)
    private String description;
    @XmlAttribute(required = false)
    private int type;
    
    /* GC - 22/10/2015 */
    /*
    @XmlAttribute(required = false)
    private boolean reliable;
    */
    
    @XmlAttribute(required = false)
    //la data deve essere formattata, altrimenti il date picker puo' non validare la data in alcune situazioni
    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssz")
    private Date update_timestamp;
    @XmlAttribute(required = false)
    private Date min_night_start_time;
    @XmlAttribute(required = false)
    private Date min_night_stop_time;
    
    /* GC - 22/10/2015 */ 
	/*
    @XmlAttribute(required = false)
    private Date max_day_start_time_1;
    @XmlAttribute(required = false)
    private Date max_day_stop_time_1;
    @XmlAttribute(required = false)
    private Date max_day_start_time_2;
    @XmlAttribute(required = false)
    private Date max_day_stop_time_2;
    @XmlAttribute(required = false)
    private Date max_day_start_time_3;
    @XmlAttribute(required = false)
    private Date max_day_stop_time_3;
    */
    
    @XmlAttribute(required = false)
    private int energy_category;
    @XmlAttribute(required = false)
    private double energy_specific_content;
    @XmlAttribute(required = false)
    private int strumentation_type;
    @XmlAttribute(required = false)
    private String strumentation_model;
    @XmlAttribute(required = false)
    private String strumentation_serial_number;
    @XmlAttribute(required = false)
    private int strumentation_link_type;
    @XmlAttribute(required = false)
    private String sap_code;
    @XmlAttribute(required = false)
    private double fixed_value;
    @XmlAttribute(required = false)
    private String epanet_object_id;
    @XmlAttribute(required = false)
    private boolean critical;
    @XmlAttribute(required = false)
    private double x_position;
    @XmlAttribute(required = false)
    private double y_position;
    @XmlAttribute(required = false)
    private double z_position;
    @XmlAttribute(required = false)
    private boolean alarm_thresholds_enable;
    @XmlAttribute(required = false)
    private double alarm_min_threshold;
    @XmlAttribute(required = false)
    private double alarm_max_threshold;
    @XmlAttribute(required = false)
    private boolean alarm_constant_check_enable;
    @XmlAttribute(required = false)
    private double alarm_constant_hysteresis;
    @XmlAttribute(required = false)
    private int alarm_constant_check_time;
    @XmlAttribute(required = false)
    private String table_name;
    @XmlAttribute(required = false)
    private String table_relational_id_column;
    @XmlAttribute(required = false)
    private String table_relational_id_value;
    @XmlAttribute(required = false)
    private int table_relational_id_type;
    @XmlAttribute(required = false)
    private String table_timestamp_column;
    @XmlAttribute(required = false)
    private String table_value_column;
    @XmlAttribute(required = false)
    private long connections_id_odbcdsn;
    @XmlAttribute(required = false)
    private String map;
    
    // ***RC 02/11/2015***
    @XmlAttribute(required = false)
    private double orientation_degrees;
    
    @XmlAttribute(required = false)
    private int alarm_threshold_check_time;
    //***END***
    
    
    /* GC 11/11/2015*/
    @XmlAttribute(required = false)
    private String aeeg_code;
    
    @XmlAttribute(required = false)
    private String gis_code;
    
    @XmlAttribute(required = false)
    private double roughness;
    
    @XmlAttribute(required = false)
    private double diameter;
    
    @XmlAttribute(required = false)
    private double multiplication_factor;
    
    @XmlAttribute(required = false)
    private int source;
    
    
    public long getIdMeasures() {
        return idMeasures;
    }

    public void setIdMeasures(long idMeasures) {
        this.idMeasures = idMeasures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /* GC - 22/10/2015 */
    /*
    public boolean isReliable() {
        return reliable;
    }

    public void setReliable(boolean reliable) {
        this.reliable = reliable;
    }
    */

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

    public int getEnergy_category() {
        return energy_category;
    }

    public void setEnergy_category(int energy_category) {
        this.energy_category = energy_category;
    }

    public double getEnergy_specific_content() {
        return energy_specific_content;
    }

    public void setEnergy_specific_content(double energy_specific_content) {
        this.energy_specific_content = energy_specific_content;
    }

    public int getStrumentation_type() {
        return strumentation_type;
    }

    public void setStrumentation_type(int strumentation_type) {
        this.strumentation_type = strumentation_type;
    }

    public String getStrumentation_model() {
        return strumentation_model;
    }

    public void setStrumentation_model(String strumentation_model) {
        this.strumentation_model = strumentation_model;
    }

    public String getStrumentation_serial_number() {
        return strumentation_serial_number;
    }

    public void setStrumentation_serial_number(String strumentation_serial_number) {
        this.strumentation_serial_number = strumentation_serial_number;
    }

    public int getStrumentation_link_type() {
        return strumentation_link_type;
    }

    public void setStrumentation_link_type(int strumentation_link_type) {
        this.strumentation_link_type = strumentation_link_type;
    }

    public String getSap_code() {
        return sap_code;
    }

    public void setSap_code(String sap_code) {
        this.sap_code = sap_code;
    }

    public double getFixed_value() {
        return fixed_value;
    }

    public void setFixed_value(double fixed_value) {
        this.fixed_value = fixed_value;
    }

    public String getEpanet_object_id() {
        return epanet_object_id;
    }

    public void setEpanet_object_id(String epanet_object_id) {
        this.epanet_object_id = epanet_object_id;
    }

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    public double getX_position() {
        return x_position;
    }

    public void setX_position(double x_position) {
        this.x_position = x_position;
    }

    public double getY_position() {
        return y_position;
    }

    public void setY_position(double y_position) {
        this.y_position = y_position;
    }

    public double getZ_position() {
        return z_position;
    }

    public void setZ_position(double z_position) {
        this.z_position = z_position;
    }

    public boolean isAlarm_thresholds_enable() {
        return alarm_thresholds_enable;
    }

    public void setAlarm_thresholds_enable(boolean alarm_thresholds_enable) {
        this.alarm_thresholds_enable = alarm_thresholds_enable;
    }

    public double getAlarm_min_threshold() {
        return alarm_min_threshold;
    }

    public void setAlarm_min_threshold(double alarm_min_threshold) {
        this.alarm_min_threshold = alarm_min_threshold;
    }

    public double getAlarm_max_threshold() {
        return alarm_max_threshold;
    }

    public void setAlarm_max_threshold(double alarm_max_threshold) {
        this.alarm_max_threshold = alarm_max_threshold;
    }

    public boolean isAlarm_constant_check_enable() {
        return alarm_constant_check_enable;
    }

    public void setAlarm_constant_check_enable(boolean alarm_constant_check_enable) {
        this.alarm_constant_check_enable = alarm_constant_check_enable;
    }

    public double getAlarm_constant_hysteresis() {
        return alarm_constant_hysteresis;
    }

    public void setAlarm_constant_hysteresis(double alarm_constant_hysteresis) {
        this.alarm_constant_hysteresis = alarm_constant_hysteresis;
    }

    public int getAlarm_constant_check_time() {
        return alarm_constant_check_time;
    }

    public void setAlarm_constant_check_time(int alarm_constant_check_time) {
        this.alarm_constant_check_time = alarm_constant_check_time;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getTable_relational_id_column() {
        return table_relational_id_column;
    }

    public void setTable_relational_id_column(String table_relational_id_column) {
        this.table_relational_id_column = table_relational_id_column;
    }

    public String getTable_relational_id_value() {
        return table_relational_id_value;
    }

    public void setTable_relational_id_value(String table_relational_id_value) {
        this.table_relational_id_value = table_relational_id_value;
    }

    public int getTable_relational_id_type() {
        return table_relational_id_type;
    }

    public void setTable_relational_id_type(int table_relational_id_type) {
        this.table_relational_id_type = table_relational_id_type;
    }

    public String getTable_timestamp_column() {
        return table_timestamp_column;
    }

    public void setTable_timestamp_column(String table_timestamp_column) {
        this.table_timestamp_column = table_timestamp_column;
    }

    public String getTable_value_column() {
        return table_value_column;
    }

    public void setTable_value_column(String table_value_column) {
        this.table_value_column = table_value_column;
    }

    public long getConnections_id_odbcdsn() {
        return connections_id_odbcdsn;
    }

    public void setConnections_id_odbcdsn(long connections_id_odbcdsn) {
        this.connections_id_odbcdsn = connections_id_odbcdsn;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    @Override
    public int compareTo(Measures o) {
        if (o != null) {
            return getName().compareToIgnoreCase(o.getName());
        }
        return 0;
    }
    
   
	 /* GC 11/11/2015*/
	
	public String getAeeg_code() {
		return aeeg_code;
	}

	public void setAeeg_code(String aeeg_code) {
		this.aeeg_code = aeeg_code;
	}

	public String getGis_code() {
		return gis_code;
	}

	public void setGis_code(String gis_code) {
		this.gis_code = gis_code;
	}

	public double getRoughness() {
		return roughness;
	}

	public void setRoughness(double roughness) {
		this.roughness = roughness;
	}

	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

	public double getMultiplication_factor() {
		return multiplication_factor;
	}

	public void setMultiplication_factor(double multiplication_factor) {
		this.multiplication_factor = multiplication_factor;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	 // ***RC 02/11/2015***
	public double getOrientation_degrees() {
		return orientation_degrees;
	}

	public void setOrientation_degrees(double orientation_degrees) {
		this.orientation_degrees = orientation_degrees;
	}
	
	
	public int getAlarm_threshold_check_time() {
		return alarm_threshold_check_time;
	}

	public void setAlarm_threshold_check_time(int alarm_threshold_check_time) {
		this.alarm_threshold_check_time = alarm_threshold_check_time;
	}
	//***END***
}
