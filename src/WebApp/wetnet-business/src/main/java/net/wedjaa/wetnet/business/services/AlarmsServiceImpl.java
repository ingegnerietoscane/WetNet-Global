/**
 * 
 */
package net.wedjaa.wetnet.business.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.commons.DateUtil;
import net.wedjaa.wetnet.business.commons.NumberUtil;
import net.wedjaa.wetnet.business.dao.AlarmsDAO;
import net.wedjaa.wetnet.business.dao.DistrictsDAO;
import net.wedjaa.wetnet.business.dao.EventsDAO;
import net.wedjaa.wetnet.business.dao.MeasuresDAO;
import net.wedjaa.wetnet.business.domain.Alarms;
import net.wedjaa.wetnet.business.domain.AlarmsData;
import net.wedjaa.wetnet.business.domain.CatChartData;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.Events;
import net.wedjaa.wetnet.business.domain.EventsData;
import net.wedjaa.wetnet.business.domain.EventsTypeNum;
import net.wedjaa.wetnet.business.domain.GanttChartData;
import net.wedjaa.wetnet.business.domain.GanttTask;
import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.domain.PieChartData;
import net.wedjaa.wetnet.business.domain.ScatterChartData;
import net.wedjaa.wetnet.business.domain.Users;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * @author graziella cipolletti
 *
 */
public class AlarmsServiceImpl implements AlarmsService {

    private static final Logger logger = Logger.getLogger(AlarmsServiceImpl.class.getName());
  
    @Autowired
    private MessageSource messages;
    
    @Autowired
    private AlarmsDAO alarmsDAO;
    
    @Autowired
    private MeasuresDAO measuresDAO;
    
    
    /**
     * param alarmData
     * return 
     */
    @Override
    public List<Alarms> getAlarms(AlarmsData alarmData, Users user){    
        logger.info("received request for 'getAlarms'");
          
        List<Alarms> result = new ArrayList<Alarms>();
        List<Alarms> tempResult = new ArrayList<Alarms>();
        
        if (alarmData.getMeasuresSelected() == null)
        	tempResult = alarmsDAO.getAllActive(user);
        else
        	tempResult = alarmsDAO.getAllActiveByMeasures(alarmData.getMeasuresSelected(),user);
        
        
        for(Alarms a: tempResult)
        {
        	if(a.getEvent_type() == 1)
        	{
        	a.setDuration(DateUtil.fromTimeToDayAndTime(a.getDuration()));	
        	result.add(a);
        	}
        }
        
     
        return result;
    }
    
    public List<Alarms> getAlarmsClose(AlarmsData alarmData, Users user)
    {
    	 logger.info("received request for 'getAlarmsClose'");
         
    	  List<Alarms> result = new ArrayList<Alarms>();
          
          if (alarmData.getMeasuresSelected() == null)
              result = alarmsDAO.getAllClose(user);
          else
              result = alarmsDAO.getAllCloseByMeasures(alarmData.getMeasuresSelected(),user);

          for(Alarms a: result)
          {
         		a.setDuration(DateUtil.fromTimeToDayAndTime(a.getDuration()));	
          }
          
        
         return result;
    }


	@Override
	public String getAlarmsDataCSV(List<Alarms> dataList, Locale locale) {
		//recupero i dati
        try {
            return createCSV(dataList, locale);
        } catch (IOException e) {
            new BusinessesException("Error on CSV Generation", e);
        }
        return null;
    }
    
    private String createCSV(List<Alarms> dataList, Locale locale) throws IOException{
        StringBuffer stringBuffer = new StringBuffer();
        CSVPrinter csvPrinter = new CSVPrinter(stringBuffer, CSVFormat.EXCEL);
        
        csvPrinter.print(messages.getMessage("alarms-active.form.table.measure", null, locale));                    
        csvPrinter.print(messages.getMessage("alarms-active.form.table.type", null, locale));
        csvPrinter.print(messages.getMessage("alarms-active.form.table.value", null, locale)); 
        csvPrinter.print(messages.getMessage("alarms-active.form.table.reference-value", null, locale)); 
        csvPrinter.print(messages.getMessage("alarms-active.form.table.duration", null, locale));  
        csvPrinter.println();
        
        long righe = dataList.size();
        for (int i = 0; i < righe; i++) {
        	
            Alarms a = dataList.get(i); 
           
            String type="";
        	switch (a.getAlarm_type()){
        	case 0:
        		type=messages.getMessage("alarms.type.0", null, locale);
        		break;
        	case 1:
        		type=messages.getMessage("alarms.type.1", null, locale);
        		break;
        	case 2:
        		type=messages.getMessage("alarms.type.2", null, locale);
        		break;
        	case 3:
        		type=messages.getMessage("alarms.type.3", null, locale);
        		break;
        	case 4:
        		type=messages.getMessage("alarms.type.4", null, locale);
        		break;
        	default:
        		break;
        	}
        	
            
            csvPrinter.print(a.getMeasures_name());                    
            csvPrinter.print(type);
            csvPrinter.print(a.getAlarm_value());
            csvPrinter.print(a.getReference_value()); 
            csvPrinter.print(a.getDuration()); 
            csvPrinter.println();
        }
        csvPrinter.close();
        return stringBuffer.toString();
    }
    
   
}
