/**
 * 
 */
package net.wedjaa.wetnet.business.services;

import java.util.List;
import java.util.Locale;

import net.wedjaa.wetnet.business.domain.Alarms;
import net.wedjaa.wetnet.business.domain.AlarmsData;
import net.wedjaa.wetnet.business.domain.CatChartData;
import net.wedjaa.wetnet.business.domain.Events;
import net.wedjaa.wetnet.business.domain.EventsData;
import net.wedjaa.wetnet.business.domain.GanttChartData;
import net.wedjaa.wetnet.business.domain.GanttTask;
import net.wedjaa.wetnet.business.domain.PieChartData;
import net.wedjaa.wetnet.business.domain.ScatterChartData;
import net.wedjaa.wetnet.business.domain.Users;

/**
 * @author graziella cipolletti
 *
 */
public interface AlarmsService {

	/*restituisce gli allarmi attivi*/
    public List<Alarms> getAlarms(AlarmsData alarmData, Users user);
    
    /*restituisce gli allarmi chiusi*/
    public List<Alarms> getAlarmsClose(AlarmsData alarmData, Users user);
    
    
    public String getAlarmsDataCSV(List<Alarms> dataList, Locale locale);
   
}
