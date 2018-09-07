package net.wedjaa.wetnet.business.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;


import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.commons.DateUtil;
import net.wedjaa.wetnet.business.dao.ConnectionsDAO;
import net.wedjaa.wetnet.business.dao.DataDistrictsDAO;
import net.wedjaa.wetnet.business.dao.DistrictsDAO;
import net.wedjaa.wetnet.business.dao.DistrictsFilesDAO;
import net.wedjaa.wetnet.business.dao.MeasuresFilesDAO;
import net.wedjaa.wetnet.business.domain.DataDistricts;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.DistrictsFiles;
import net.wedjaa.wetnet.business.domain.DistrictsG2;
import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.domain.MeasuresFiles;
import net.wedjaa.wetnet.web.spring.model.ResultMessage;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author massimo ricci
 * @author alessandro vincelli
 *
 */
public class DataDistrictsServiceImpl implements DataDistrictsService {

    @Autowired
    private DistrictsDAO districtsDAO;
    @Autowired
    private DataDistrictsDAO dataDistrictsDAO;

    //***RC 06/11/2015***
  	private static final Logger logger = Logger.getLogger("DataDistrictsServiceImpl");
    @Autowired
    private DistrictsFilesDAO districtsFilesDAO;
    @Autowired
    private MeasuresFilesDAO measuresFilesDAO;
    //***END***
    
    //**** GC 04/12/2015***/
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private ConnectionsDAO connectionsDAO;
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public DistrictsG2 getAllDataDistricts() {
        List<Districts> districts = Collections.unmodifiableList(districtsDAO.getAllDistricts());
        List<DataDistricts> dataDistricts = dataDistrictsDAO.getAllDataDistricts();
        HashMap<Date, double[]> map = new HashMap<Date, double[]>();

        for (DataDistricts dataDistrict : dataDistricts) {
            double arr[] = new double[districts.size()];
            map.put(dataDistrict.getTimestamp(), arr);
        }

        for (int i = 0; i < districts.size(); i++) {
            Districts distretto = districts.get(i);
            for (DataDistricts dataDistrict : dataDistricts) {
                if (dataDistrict.getIdDistricts() == distretto.getIdDistricts()) {
                    map.get(dataDistrict.getTimestamp())[i] = dataDistrict.getValue();
                }
            }
        }
        DistrictsG2 g2 = new DistrictsG2(districts, map);
        return g2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistrictsG2 getDataDistrictsByDistrictId(long districtId) {
        List<Districts> districts = new ArrayList<Districts>(1);
        List<Districts> aLLdistricts = districtsDAO.getAllDistricts();
        for (Districts districts2 : aLLdistricts) {
            if (districts2.getIdDistricts() == districtId) {
                districts.add(districts2);
            }
        }

        List<DataDistricts> dataDistricts = dataDistrictsDAO.getAllDataDistricts();
        HashMap<Date, double[]> map = new HashMap<Date, double[]>();

        for (DataDistricts dataDistrict : dataDistricts) {
            double arr[] = new double[districts.size()];
            map.put(dataDistrict.getTimestamp(), arr);
        }

        for (int i = 0; i < districts.size(); i++) {
            Districts distretto = districts.get(i);
            for (DataDistricts dataDistrict : dataDistricts) {
                if (dataDistrict.getIdDistricts() == distretto.getIdDistricts()) {
                    map.get(dataDistrict.getTimestamp())[i] = dataDistrict.getValue();
                }
            }
        }
        DistrictsG2 g2 = new DistrictsG2(districts, map);
        return g2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getJoinedDataDistrictsCSV() {
        List<Object> list = new ArrayList<Object>();
        Set<Date> dates = new TreeSet<Date>(new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o1.compareTo(o2);
            }
        });

        Set<String> districts = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        List<DataDistricts> dataDistricts = dataDistrictsDAO.getJoinedDataDistricts();
        for (DataDistricts dataDistricts2 : dataDistricts) {
            dates.add(dataDistricts2.getTimestamp());
            districts.add(dataDistricts2.getDistrictName());
        }

        StringWriter writer = new StringWriter();
        List<Object> list1 = new ArrayList<Object>();
        try {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
            list1.add("x");
            //prima riga con le date
            csvPrinter.print("x");
            Date[] datesArray = dates.toArray(new Date[dates.size()]);
            for (int j = 0; j < datesArray.length; j++) {
                csvPrinter.print(DateUtil.SDF2SHOW.print(datesArray[j].getTime()));
                list1.add(DateUtil.SDF2SHOW.print(datesArray[j].getTime()));
            }
            list.add(list1);
            csvPrinter.println();
            //righe dei distretti
            String[] districtsArray = districts.toArray(new String[districts.size()]);

            for (int i = 0; i < districtsArray.length; i++) {
                String distretto = districtsArray[i];
                List<Object> listDistretto = new ArrayList<Object>();
                listDistretto.add(distretto);
                csvPrinter.print(distretto);
                for (int j = 0; j < datesArray.length; j++) {
                    Date data = datesArray[j];
                    String value = "0";
                    for (DataDistricts dataDistricts2 : dataDistricts) {
                        if (dataDistricts2.getTimestamp() == data && distretto.equals(dataDistricts2.getDistrictName())) {
                            value = Double.toString(dataDistricts2.getValue());
                        }
                    }
                    listDistretto.add(value);
                    csvPrinter.print(value);
                }
                list.add(distretto);
                csvPrinter.println();
            }
            csvPrinter.close();
            return writer.toString();
        } catch (IOException e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getJoinedDataDistricts() {
        List<Object> list = new ArrayList<Object>();
        Set<Date> dates = new TreeSet<Date>(new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o1.compareTo(o2);
            }
        });

        Set<String> districts = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        List<DataDistricts> dataDistricts = dataDistrictsDAO.getJoinedDataDistricts();
        for (DataDistricts dataDistricts2 : dataDistricts) {
            dates.add(dataDistricts2.getTimestamp());
            districts.add(dataDistricts2.getDistrictName());
        }

        List<Object> list1 = new ArrayList<Object>();
        list1.add("x");
        //prima riga con le date

        Date[] datesArray = dates.toArray(new Date[dates.size()]);
        for (int j = 0; j < datesArray.length; j++) {

            list1.add(DateUtil.SDF2SHOW.print(datesArray[j].getTime()));
        }
        list.add(list1);

        //righe dei distretti
        String[] districtsArray = districts.toArray(new String[districts.size()]);

        for (int i = 0; i < districtsArray.length; i++) {
            String distretto = districtsArray[i];
            List<Object> listDistretto = new ArrayList<Object>();
            listDistretto.add(distretto);

            for (int j = 0; j < datesArray.length; j++) {
                Date data = datesArray[j];
                String value = "0";
                for (DataDistricts dataDistricts2 : dataDistricts) {
                    if (dataDistricts2.getTimestamp() == data && distretto.equals(dataDistricts2.getDistrictName())) {
                        value = Double.toString(dataDistricts2.getValue());
                    }
                }
                listDistretto.add(value);

            }
            list.add(listDistretto);

        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getDatesOnDataDistricts(int year, int month, int week) {
        List<Object> list = new ArrayList<Object>();
        Set<Date> dates = new TreeSet<Date>(new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o1.compareTo(o2);
            }
        });

        List<DataDistricts> dataDistricts = dataDistrictsDAO.getJoinedDataDistricts();
        for (DataDistricts dataDistricts2 : dataDistricts) {
            dates.add(dataDistricts2.getTimestamp());
        }

        Date[] datesArray = dates.toArray(new Date[dates.size()]);
        DateTime start = new DateTime(datesArray[0]);
        DateTime end = new DateTime(datesArray[datesArray.length - 1]);
        Years years = Years.yearsBetween(start, end);
        List<Integer> listAnni = new ArrayList<Integer>(years.getYears());
        for (int i = 0; i < years.getYears(); i++) {
            listAnni.add(years.getValue(i));
        }
        if (year != 0) {

        }
        return list;
        //        Months mt = Months.monthsBetween(start, end);
        //        mt.ge
        //        
        //        List<Object> list1 = new ArrayList<Object>();
        //        list1.add("x");
        //        //prima riga con le date
        //
        //        Date[] datesArray = dates.toArray(new Date[dates.size()]);
        //        for (int j = 0; j < datesArray.length; j++) {
        //
        //            list1.add(DateUtil.SDF2SHOW.print(datesArray[j].getTime()));
        //        }
        //        list.add(list1);
        //
        //        //righe dei distretti
        //        String[] districtsArray = districts.toArray(new String[districts.size()]);
        //
        //        for (int i = 0; i < districtsArray.length; i++) {
        //            String distretto = districtsArray[i];
        //            List<Object> listDistretto = new ArrayList<Object>();
        //            listDistretto.add(distretto);
        //
        //            for (int j = 0; j < datesArray.length; j++) {
        //                Date data = datesArray[j];
        //                String value = "0";
        //                for (DataDistricts dataDistricts2 : dataDistricts) {
        //                    if (dataDistricts2.getTimestamp() == data && distretto.equals(dataDistricts2.getDistrictName())) {
        //                        value = Double.toString(dataDistricts2.getValue());
        //                    }
        //                }
        //                listDistretto.add(value);
        //
        //            }
        //            list.add(listDistretto);
        //
        //        }
        //        return list;
    }

  //***RC 25/11/2015***
    public String createCSVFromListM(List<Measures> dataList, Locale locale) throws IOException{
        StringBuffer stringBuffer = new StringBuffer();
        CSVPrinter csvPrinter = new CSVPrinter(stringBuffer, CSVFormat.EXCEL);
        
        long righe = dataList.size();
        
        csvPrinter.print("IdMeasures");
        csvPrinter.print("Name");
        csvPrinter.print("Description");
        csvPrinter.print("Type");
        csvPrinter.print("Update_timestamp");
        csvPrinter.print("Min_night_start_time");
        csvPrinter.print("Min_night_stop_time");
        csvPrinter.print("Energy_category");
        csvPrinter.print("Energy_specific_content");
        csvPrinter.print("Strumentation_type");
        csvPrinter.print("Strumentation_model");
        csvPrinter.print("Strumentation_serial_number");
        csvPrinter.print("Strumentation_link_type");
        csvPrinter.print("Sap_code");
        csvPrinter.print("Fixed_value");
        csvPrinter.print("Epanet_object_id");
        csvPrinter.print("Critical");
        csvPrinter.print("X_position");
        csvPrinter.print("Y_position");
        csvPrinter.print("Z_position");
        csvPrinter.print("Alarm_thresholds_enable");
        csvPrinter.print("Alarm_min_threshold");
        csvPrinter.print("Alarm_max_threshold");
        csvPrinter.print("Alarm_constant_check_enable");
        csvPrinter.print("Alarm_constant_hysteresis");
        csvPrinter.print("Alarm_constant_check_time");
        csvPrinter.print("Table_name");
        csvPrinter.print("Table_relational_id_column");
        csvPrinter.print("Table_relational_id_value");
        csvPrinter.print("Table_relational_id_type");
        csvPrinter.print("Table_timestamp_column");
        csvPrinter.print("Table_value_column");
        csvPrinter.print("Source");
        csvPrinter.print("Aeeg_code");
        csvPrinter.print("Gis_code");
        csvPrinter.print("Orientation_degrees");
        csvPrinter.print("Connections_id_odbcdsn");
        csvPrinter.print("Roughness");
        csvPrinter.print("Diameter");
        csvPrinter.print("Multiplication_factor");

        csvPrinter.println();
        
        for (int i = 0; i < righe; i++) {
            Measures current = dataList.get(i);
            
            csvPrinter.print(current.getIdMeasures());
            csvPrinter.print(current.getName());
            csvPrinter.print(current.getDescription());
            
            String typ = "";
            if(current.getType()==0) typ = messageSource.getMessage("measure.form.type.val.0", null, locale);
            if(current.getType()==1) typ = messageSource.getMessage("measure.form.type.val.1", null, locale);
            if(current.getType()==2) typ = messageSource.getMessage("measure.form.type.val.2", null, locale);
            csvPrinter.print(typ);
      		// csvPrinter.print(current.getType());
            
            csvPrinter.print(DateUtil.SDTF2SIMPLEUSA.print(current.getUpdate_timestamp().getTime()));
            csvPrinter.print(DateUtil.SDTF2TIME.print(current.getMin_night_start_time().getTime()));
            csvPrinter.print(DateUtil.SDTF2TIME.print(current.getMin_night_stop_time().getTime()));
            
            String cat = "";
            if(current.getEnergy_category()==0) cat = messageSource.getMessage("measure.form.energy_category.val.0", null, locale);
            if(current.getEnergy_category()==1) cat = messageSource.getMessage("measure.form.energy_category.val.1", null, locale);
            if(current.getEnergy_category()==2) cat = messageSource.getMessage("measure.form.energy_category.val.2", null, locale);
            csvPrinter.print(cat);
            //csvPrinter.print(current.getEnergy_category());
            
            
            csvPrinter.print(current.getEnergy_specific_content());
            
            String str_type = "";
	            if(current.getStrumentation_type()==0) str_type = messageSource.getMessage("measure.form.strumentation_type.val.0", null, locale);
	            if(current.getStrumentation_type()==1) str_type = messageSource.getMessage("measure.form.strumentation_type.val.1", null, locale);
	            if(current.getStrumentation_type()==2) str_type = messageSource.getMessage("measure.form.strumentation_type.val.2", null, locale);
	            if(current.getStrumentation_type()==3) str_type = messageSource.getMessage("measure.form.strumentation_type.val.3", null, locale);
	            if(current.getStrumentation_type()==4) str_type = messageSource.getMessage("measure.form.strumentation_type.val.4", null, locale);
	            if(current.getStrumentation_type()==5) str_type = messageSource.getMessage("measure.form.strumentation_type.val.5", null, locale);
		        csvPrinter.print(str_type);
	            
            //csvPrinter.print(current.getStrumentation_type());
            
            
            csvPrinter.print(current.getStrumentation_model());
            csvPrinter.print(current.getStrumentation_serial_number());
            
             String str_link = "";
            if(current.getStrumentation_link_type()==0) str_link = messageSource.getMessage("measure.form.strumentation_link_type.val.0", null, locale);
            if(current.getStrumentation_link_type()==1) str_link = messageSource.getMessage("measure.form.strumentation_link_type.val.1", null, locale);
            if(current.getStrumentation_link_type()==2) str_link = messageSource.getMessage("measure.form.strumentation_link_type.val.2", null, locale);
            if(current.getStrumentation_link_type()==3) str_link = messageSource.getMessage("measure.form.strumentation_link_type.val.3", null, locale);
            if(current.getStrumentation_link_type()==4) str_link = messageSource.getMessage("measure.form.strumentation_link_type.val.4", null, locale);
            csvPrinter.print(str_link);
            //csvPrinter.print(current.getStrumentation_link_type());
            
            csvPrinter.print(current.getSap_code());
            csvPrinter.print(current.getFixed_value());
            csvPrinter.print(current.getEpanet_object_id());
            csvPrinter.print(current.isCritical());
            csvPrinter.print(current.getX_position());
            csvPrinter.print(current.getY_position());
            csvPrinter.print(current.getZ_position());
            csvPrinter.print(current.isAlarm_thresholds_enable());
            csvPrinter.print(current.getAlarm_min_threshold());
            csvPrinter.print(current.getAlarm_max_threshold());
            csvPrinter.print(current.isAlarm_constant_check_enable());
            csvPrinter.print(current.getAlarm_constant_hysteresis());
            csvPrinter.print(current.getAlarm_constant_check_time());
            csvPrinter.print(current.getTable_name());
            csvPrinter.print(current.getTable_relational_id_column());
            csvPrinter.print(current.getTable_relational_id_value());
            csvPrinter.print(current.getTable_relational_id_type());
            csvPrinter.print(current.getTable_timestamp_column());
            csvPrinter.print(current.getTable_value_column());
            
            String sou = "";
            if(current.getSource()==0) sou = messageSource.getMessage("measure.form.source.val.0", null, locale);
            if(current.getSource()==1) sou = messageSource.getMessage("measure.form.source.val.1", null, locale);
            if(current.getSource()==2) sou = messageSource.getMessage("measure.form.source.val.2", null, locale);
            if(current.getSource()==3) sou = messageSource.getMessage("measure.form.source.val.3", null, locale);
            csvPrinter.print(sou);
            //csvPrinter.print(current.getSource());
            
            
            csvPrinter.print(current.getAeeg_code());
            csvPrinter.print(current.getGis_code());
            csvPrinter.print(current.getOrientation_degrees());
            
            String conn_name = connectionsDAO.getById(current.getConnections_id_odbcdsn()).getOdbc_dsn();
            csvPrinter.print(conn_name);
            //csvPrinter.print(current.getConnections_id_odbcdsn());
            
            csvPrinter.print(current.getRoughness());
            csvPrinter.print(current.getDiameter());
            csvPrinter.print(current.getMultiplication_factor());
            
            csvPrinter.println();
        }
        csvPrinter.close();
        return stringBuffer.toString();
    }
    
    public String createCSVFromListD(List<Districts> dataList, Locale locale) throws IOException{
        StringBuffer stringBuffer = new StringBuffer();
        CSVPrinter csvPrinter = new CSVPrinter(stringBuffer, CSVFormat.EXCEL);
        
        long righe = dataList.size();
        
        csvPrinter.print("IdDistricts");
        csvPrinter.print("Name");
        csvPrinter.print("Zone");
        csvPrinter.print("Class");
        csvPrinter.print("Municipality");
        csvPrinter.print("Inhabitants");
        csvPrinter.print("Update_timestamp");
        csvPrinter.print("Min_night_start_time");
        csvPrinter.print("Min_night_stop_time");
        csvPrinter.print("Unitary_phisiological_nigth_demand");
        csvPrinter.print("Properties");
        csvPrinter.print("Rewarded_water");
        csvPrinter.print("Billing");
        csvPrinter.print("Not_household_night_use");
        csvPrinter.print("Length_main");
        csvPrinter.print("Average_zone_night_pressure");
        csvPrinter.print("Household_night_use");
        csvPrinter.print("Alpha_emitter_exponent");
        csvPrinter.print("Sap_code");
        csvPrinter.print("Ev_enable");
        csvPrinter.print("Ev_bands_autoupdate");
        csvPrinter.print("Ev_high_band");
        csvPrinter.print("Ev_low_band");
        csvPrinter.print("Ev_statistic_high_band");
        csvPrinter.print("Ev_statistic_low_band");
        csvPrinter.print("Ev_variable_type");
        csvPrinter.print("Ev_last_good_sample_day");
        csvPrinter.print("Ev_last_good_samples");
        csvPrinter.print("Ev_alpha");
        csvPrinter.print("Ev_samples_trigger");
        csvPrinter.print("Notes");
        csvPrinter.print("MapLevel");
        csvPrinter.print("AeegCode");
        csvPrinter.print("GisCode");
        csvPrinter.print("Type");
        csvPrinter.print("WithdrawalOperationalArea");
        csvPrinter.print("WithdrawalArea");
        csvPrinter.print("WaterAuthority");
        
        csvPrinter.println();
        
        for (int i = 0; i < righe; i++) {
            Districts current = dataList.get(i);
            
            csvPrinter.print(current.getIdDistricts());
            csvPrinter.print(current.getName());
            csvPrinter.print(current.getZone());
            
            String cla = "A";
            if(current.getdClass()==0) cla = "A";
            if(current.getdClass()==1) cla = "B";
            if(current.getdClass()==2) cla = "C";
            csvPrinter.print(cla);
            //csvPrinter.print(current.getdClass());
            
            csvPrinter.print(current.getMunicipality());
            csvPrinter.print(current.getInhabitants());
            csvPrinter.print(DateUtil.SDTF2SIMPLEUSA.print(current.getUpdate_timestamp().getTime()));
            csvPrinter.print(DateUtil.SDTF2TIME.print(current.getMin_night_start_time().getTime()));
            csvPrinter.print(DateUtil.SDTF2TIME.print(current.getMin_night_stop_time().getTime()));
            csvPrinter.print(current.getUnitary_phisiological_nigth_demand());
            csvPrinter.print(current.getProperties());
            csvPrinter.print(current.getRewarded_water());
            csvPrinter.print(current.getBilling());
            csvPrinter.print(current.getNot_household_night_use());
            csvPrinter.print(current.getLength_main());
            csvPrinter.print(current.getAverage_zone_night_pressure());
            csvPrinter.print(current.getHousehold_night_use());
            csvPrinter.print(current.getAlpha_emitter_exponent());
            csvPrinter.print(current.getSap_code());
            csvPrinter.print(current.isEv_enable());
            csvPrinter.print(current.isEv_bands_autoupdate());
            csvPrinter.print(current.getEv_high_band());
            csvPrinter.print(current.getEv_low_band());
            csvPrinter.print(current.getEv_statistic_high_band());
            csvPrinter.print(current.getEv_statistic_low_band());
            
            String ev = "";
	            if(current.getEv_variable_type()==0) ev = messageSource.getMessage("district.form.ev_variable_type.val.0", null, locale);
	            if(current.getEv_variable_type()==1) ev = messageSource.getMessage("district.form.ev_variable_type.val.1", null, locale);
	            if(current.getEv_variable_type()==2) ev = messageSource.getMessage("district.form.ev_variable_type.val.2", null, locale);
	            if(current.getEv_variable_type()==3) ev = messageSource.getMessage("district.form.ev_variable_type.val.3", null, locale);
	            if(current.getEv_variable_type()==4) ev = messageSource.getMessage("district.form.ev_variable_type.val.4", null, locale);
		            csvPrinter.print(ev);   
            //csvPrinter.print(current.getEv_variable_type());
           
	        csvPrinter.print(DateUtil.SDF2SIMPLEUSA.print(current.getEv_last_good_sample_day().getTime()));
            csvPrinter.print(current.getEv_last_good_samples());
            csvPrinter.print(current.getEv_alpha());
            csvPrinter.print(current.getEv_samples_trigger());
            csvPrinter.print(current.getNotes());
            csvPrinter.print(current.getMapLevel());
            csvPrinter.print(current.getAeegCode());
            csvPrinter.print(current.getGisCode());
            
            String typ = "";
            if(current.getType()==0) typ = messageSource.getMessage("district.form.type.option1.label", null, locale);
            if(current.getType()==1) typ = messageSource.getMessage("district.form.type.option2.label", null, locale);
            if(current.getType()==2) typ = messageSource.getMessage("district.form.type.option3.label", null, locale);
            csvPrinter.print(typ);
            
            //csvPrinter.print(current.getType());
            csvPrinter.print(current.getWithdrawalOperationalArea());
            csvPrinter.print(current.getWithdrawalArea());
            csvPrinter.print(current.getWaterAuthority());
            
            csvPrinter.println();
        }
        csvPrinter.close();
        return stringBuffer.toString();
    }
    //***END***
    
    
    //***RC 06/11/2015***
    @Override
    public String updateDistrictFile(MultipartFile file, String id, String desc, String uri) {
        try {
        	if (file != null && file.getBytes() != null && file.getBytes().length > 0) {
                
                DistrictsFiles uploadFile = new DistrictsFiles();
                byte[] b = file.getBytes();
                System.out.println("+++++++++++++++++++++++++\n" +b.length +"+++++++++++++++++++++++++\n" +file.getBytes().length);
                uploadFile.setFile(file.getBytes());
                uploadFile.setFileName(file.getOriginalFilename());
                uploadFile.setFileHash("" +file.getBytes().hashCode());
                uploadFile.setFileUri(uri);
                uploadFile.setDescription(desc);
                uploadFile.setIdDistricts(Integer.parseInt(id));
                
                try {
	                Date date = new Date();
	                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                Date current;
					current = format.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
					uploadFile.setLoadTimestamp(current);
				} catch (ParseException e) {
					e.printStackTrace();
				}
                
                districtsFilesDAO.insert(uploadFile);
                return ResultMessage.SUCCESS;
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return ResultMessage.DANGER;
    }
    
    @Override
    public String updateMeasureFile(MultipartFile file, String id, String desc, String uri) {
       
    	try {
            if (file != null && file.getBytes() != null && file.getBytes().length > 0) {
                
                MeasuresFiles uploadFile = new MeasuresFiles();
                uploadFile.setFile(file.getBytes());
                uploadFile.setFileName(file.getOriginalFilename());
                uploadFile.setFileHash("" +file.getBytes().hashCode());
                uploadFile.setFileUri(uri);
                uploadFile.setDescription(desc);
                uploadFile.setIdMeasures(Integer.parseInt(id));
                
                try {
	                Date date = new Date();
	                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                Date current;
					current = format.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
					uploadFile.setLoadTimestamp(current);
				} catch (ParseException e) {
					e.printStackTrace();
				}
                
                measuresFilesDAO.insert(uploadFile);
                return ResultMessage.SUCCESS;
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return ResultMessage.DANGER;
    }
    
    @Override
    public DistrictsFiles getDistrictFileById(String id) {
       
    	DistrictsFiles df = new DistrictsFiles();
        
        df = districtsFilesDAO.getById(Long.parseLong(id));
        
        return df;
    }
    //***END***

}
