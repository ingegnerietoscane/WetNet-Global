/**
 * 
 */
package net.wedjaa.wetnet.business.services;

import java.util.List;
import java.util.Locale;

import net.wedjaa.wetnet.business.domain.CatChartData;
import net.wedjaa.wetnet.business.domain.Events;
import net.wedjaa.wetnet.business.domain.EventsData;
import net.wedjaa.wetnet.business.domain.GanttChartData;
import net.wedjaa.wetnet.business.domain.GanttTask;
import net.wedjaa.wetnet.business.domain.PieChartData;
import net.wedjaa.wetnet.business.domain.ScatterChartData;
import net.wedjaa.wetnet.business.domain.Users;

/**
 * @author massimo ricci
 *
 */
public interface EventsService {

    public List<Events> getEventsByIdDate(EventsData eventData, Users user);
    
    public PieChartData getEventsPieChartData(EventsData eventData, Locale locale);
    
    public ScatterChartData getEventsScatterChartData(EventsData eventData, Locale locale);
    
    public GanttChartData getEventsGanttChartData(EventsData eventData, Locale locale);
    
    public CatChartData getEventsCatChartData(EventsData eventData, Locale locale, Users user);

    public List<String> getAllZones(Users user);
    
    public List<String> getAllMunicipalities(Users user);
   
    //***RC 04/11/2015***
    public List<String> getAllWaterAuthorities(Users user);
    //***END***
    
    public String getEventsDataCSV(List<Events> dataList, Locale locale);
    
    public String getEventsGanttDataCSV(List<GanttTask> dataList);
}
