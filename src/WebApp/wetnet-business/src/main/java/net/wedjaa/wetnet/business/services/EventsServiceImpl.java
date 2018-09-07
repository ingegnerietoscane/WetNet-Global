/**
 * 
 */
package net.wedjaa.wetnet.business.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.commons.DateUtil;
import net.wedjaa.wetnet.business.commons.NumberUtil;
import net.wedjaa.wetnet.business.dao.DistrictsDAO;
import net.wedjaa.wetnet.business.dao.EventsDAO;
import net.wedjaa.wetnet.business.domain.CatChartData;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.Events;
import net.wedjaa.wetnet.business.domain.EventsData;
import net.wedjaa.wetnet.business.domain.EventsTypeNum;
import net.wedjaa.wetnet.business.domain.GanttChartData;
import net.wedjaa.wetnet.business.domain.GanttTask;
import net.wedjaa.wetnet.business.domain.PieChartData;
import net.wedjaa.wetnet.business.domain.ScatterChartData;
import net.wedjaa.wetnet.business.domain.Users;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * @author massimo ricci
 *
 */
public class EventsServiceImpl implements EventsService {

    private static final Logger logger = Logger.getLogger(EventsServiceImpl.class.getName());
    private static final String BASE_EVENT_MSG = "events.types.";
    private static final String TYPE_0 = "0";
    private static final String TYPE_1 = "1";
    private static final String TYPE_2 = "2";
    private static final String TYPE_3 = "3";
    private static final String TYPE_4 = "4";
    private static final String TYPE_5 = "5";
    private static Map<String, String> patternMap;
    
    static {
        patternMap = new HashMap<String, String>();
        patternMap.put(TYPE_0, "#FFFFFF");
        patternMap.put(TYPE_1, "#FF9900");
        patternMap.put(TYPE_2, "#FF0000");
        patternMap.put(TYPE_3, "#99CC33");
        patternMap.put(TYPE_4, "#3399FF");
        patternMap.put(TYPE_5, "#000000");
    }
    
    @Autowired
    private MessageSource messages;
    
    @Autowired
    private EventsDAO eventsDAO;
    
    @Autowired
    private DistrictsDAO districtsDAO;

    /**
     * param eventData
     * return 
     */
    @Override
    public List<Events> getEventsByIdDate(EventsData eventData, Users user) {    
        logger.info("received request for 'getEventsByIdDate'");
        
        eventData.setStartDate(DateUtil.fixStartDate(eventData.getStartDate()));
        
        List<Events> result = new ArrayList<Events>();
        if (eventData.getDistrictsSelected() == null)
            result = eventsDAO.getAllByDate(eventData.getStartDate(), eventData.getEndDate(), user);
        else
            result = eventsDAO.getAllByDateAndDistrict(eventData.getDistrictsSelected().getIdDistricts(), eventData.getStartDate(), eventData.getEndDate());
        
        for (Events e : result){
            e.setValue(NumberUtil.roundToAnyDecimal(e.getValue(), 2));
            e.setDelta_value(NumberUtil.roundToAnyDecimal(e.getDelta_value(), 2));
            e.setRanking(NumberUtil.roundToAnyDecimal(e.getRanking()*100., 2));
        }
        
        return result;
    }
    
    @Override
    public PieChartData getEventsPieChartData(EventsData eventData, Locale locale) {    
        logger.info("received request for 'getEventsPieChartData'");
        
        eventData.setStartDate(DateUtil.fixStartDate(eventData.getStartDate()));
        
        PieChartData result = new PieChartData();
        
        List<EventsTypeNum> eventsTypeNum = new ArrayList<EventsTypeNum>();
        if (eventData.getDistrictsSelected() != null)
            eventsTypeNum = eventsDAO.getEventsTypeNumByDistrict(eventData.getDistrictsSelected().getIdDistricts(), eventData.getStartDate(), eventData.getEndDate());
        else if (eventData.getZoneSelected() != null && !eventData.getZoneSelected().equals(""))
            eventsTypeNum = eventsDAO.getEventsTypeNumByZone(eventData.getZoneSelected(), eventData.getStartDate(), eventData.getEndDate());
        else if (eventData.getMunicipalitySelected() != null && !eventData.getMunicipalitySelected().equals(""))
            eventsTypeNum = eventsDAO.getEventsTypeNumByMunicipality(eventData.getMunicipalitySelected(), eventData.getStartDate(), eventData.getEndDate());
        
        for (EventsTypeNum e : eventsTypeNum){
            List<Object> list = new ArrayList<Object>();
            String type = messages.getMessage(BASE_EVENT_MSG + e.getType(), null, locale);
            list.add(type + " (" + String.valueOf(e.getNum()) + ")");
            list.add(e.getNum());
            result.getColumns().add(list);
            result.getPattern().add(patternMap.get(String.valueOf(e.getType())));
        }
        
        return result;
    }
    
    @Override
    public ScatterChartData getEventsScatterChartData(EventsData eventData, Locale locale) {    
        logger.info("received request for 'getEventsScatterChartData'");
        
        eventData.setStartDate(DateUtil.fixStartDate(eventData.getStartDate()));
        
        String type1 = messages.getMessage(BASE_EVENT_MSG + TYPE_1, null, locale);
        String type2 = messages.getMessage(BASE_EVENT_MSG + TYPE_2, null, locale);
        String type3 = messages.getMessage(BASE_EVENT_MSG + TYPE_3, null, locale);
        String type4 = messages.getMessage(BASE_EVENT_MSG + TYPE_4, null, locale);
        String type5 = messages.getMessage(BASE_EVENT_MSG + TYPE_5, null, locale);
        List<String> typesList= new ArrayList<String>();
        typesList.add(type1);
        typesList.add(type2);
        typesList.add(type3);
        typesList.add(type4);
        typesList.add(type5);
        
        ScatterChartData result = new ScatterChartData();
        List<Events> eventsList = new ArrayList<Events>();
        
        if (eventData.getDistrictsSelected() != null)
            eventsList = eventsDAO.getByDateAndDistrictId(eventData.getDistrictsSelected().getIdDistricts(), eventData.getStartDate(), eventData.getEndDate());
        else if (eventData.getZoneSelected() != null && !eventData.getZoneSelected().equals(""))
            eventsList = eventsDAO.getByDateAndZone(eventData.getZoneSelected(), eventData.getStartDate(), eventData.getEndDate());
        else if (eventData.getMunicipalitySelected() != null && !eventData.getMunicipalitySelected().equals(""))
            eventsList = eventsDAO.getByDateAndMunicipality(eventData.getMunicipalitySelected(), eventData.getStartDate(), eventData.getEndDate());
            
        List<Object> xList = new ArrayList<Object>();
        List<Object> type1List = new ArrayList<Object>();
        List<Object> type2List = new ArrayList<Object>();
        List<Object> type3List = new ArrayList<Object>();
        List<Object> type4List = new ArrayList<Object>();
        List<Object> type5List = new ArrayList<Object>();
        xList.add("date");
        type1List.add(type1);
        type2List.add(type2);
        type3List.add(type3);
        type4List.add(type4);
        type5List.add(type5);
        for (Events e : eventsList){       
            xList.add(DateUtil.SDF2SIMPLEUSA.print(e.getDay().getTime()));
            String type = e.getType();
            if (type.equals(TYPE_1)){
                type1List.add(1);
                type2List.add(null);
                type3List.add(null);
                type4List.add(null);
                type5List.add(null);
            } else if (type.equals(TYPE_2)){
                type1List.add(null);
                type2List.add(1);
                type3List.add(null);
                type4List.add(null);
                type5List.add(null);
            } else if (type.equals(TYPE_3)){
                type1List.add(null);
                type2List.add(null);
                type3List.add(1);
                type4List.add(null);
                type5List.add(null);
            } else if (type.equals(TYPE_4)){
                type1List.add(null);
                type2List.add(null);
                type3List.add(null);
                type4List.add(1);
                type5List.add(null);
            } else if (type.equals(TYPE_5)){
                type1List.add(null);
                type2List.add(null);
                type3List.add(null);
                type4List.add(null);
                type5List.add(1);
            }
        }

        //Setta i colori
        for (int i = 1; i <= 5; i++){
            result.getPattern().add(patternMap.get(String.valueOf(i)));
        }
        
        result.getColumns().add(xList);
        result.getColumns().add(type1List);
        result.getColumns().add(type2List);
        result.getColumns().add(type3List);
        result.getColumns().add(type4List);
        result.getColumns().add(type5List);
        result.getGroups().add(typesList);
            
        return result;
    }
    
    @Override
    public GanttChartData getEventsGanttChartData(EventsData eventData, Locale locale) {
        
        eventData.setStartDate(DateUtil.fixStartDate(eventData.getStartDate()));
        
        GanttChartData result = new GanttChartData();
        
        List<Events> eventsList = new ArrayList<Events>();
        
        if (eventData.getDistrictsSelected() != null)
            eventsList = eventsDAO.getByDateAndDistrictId(eventData.getDistrictsSelected().getIdDistricts(), eventData.getStartDate(), eventData.getEndDate());
        else if (eventData.getZoneSelected() != null && !eventData.getZoneSelected().equals(""))
            eventsList = eventsDAO.getByDateAndZone(eventData.getZoneSelected(), eventData.getStartDate(), eventData.getEndDate());
        else if (eventData.getMunicipalitySelected() != null && !eventData.getMunicipalitySelected().equals(""))
            eventsList = eventsDAO.getByDateAndMunicipality(eventData.getMunicipalitySelected(), eventData.getStartDate(), eventData.getEndDate());
        
        List<GanttTask> ganttTaskList = new ArrayList<GanttTask>();
        List<String> taskNamesList = new ArrayList<String>();
        
        for (Events e : eventsList){
            
            Calendar startCalendar = Calendar.getInstance(); 
            startCalendar.setTime(e.getDay());
            startCalendar.add(Calendar.DAY_OF_MONTH, 1);
            
            GanttTask task = new GanttTask(e.getDay(), startCalendar.getTime(), e.getDistrict_name(), e.getType());
            ganttTaskList.add(task);
            
            if (!taskNamesList.contains(e.getDistrict_name())){
                taskNamesList.add(e.getDistrict_name());
            }
        }
        
        result.setTasks(ganttTaskList);
        result.setTaskNames(taskNamesList);
        
        return result;
    }
    
    @Override
    public CatChartData getEventsCatChartData(EventsData eventData, Locale locale, Users user) {    
        logger.info("received request for 'getEventsCatChartData'");
        
        eventData.setStartDate(DateUtil.fixStartDate(eventData.getStartDate()));
        
        String type1 = messages.getMessage(BASE_EVENT_MSG + TYPE_1, null, locale);
        String type2 = messages.getMessage(BASE_EVENT_MSG + TYPE_2, null, locale);
        String type3 = messages.getMessage(BASE_EVENT_MSG + TYPE_3, null, locale);
        String type4 = messages.getMessage(BASE_EVENT_MSG + TYPE_4, null, locale);
        String type5 = messages.getMessage(BASE_EVENT_MSG + TYPE_5, null, locale);
        List<String> typesList= new ArrayList<String>();
        typesList.add(type1);
        typesList.add(type2);
        typesList.add(type3);
        typesList.add(type4);
        typesList.add(type5);
        
        CatChartData result = new CatChartData();
        List<EventsTypeNum> eventsTypeNum = new ArrayList<EventsTypeNum>();
        
        //Query
        if (eventData.getItemFlagged() != null && eventData.getItemFlagged().equals("district"))
            eventsTypeNum = eventsDAO.getEventsGroupByTypeDistrict(eventData.getStartDate(), eventData.getEndDate(), user);
        else if (eventData.getItemFlagged() != null && eventData.getItemFlagged().equals("municipality"))
            eventsTypeNum = eventsDAO.getEventsGroupByTypeMunicipality(eventData.getStartDate(), eventData.getEndDate(), user);
        else if (eventData.getItemFlagged() != null && eventData.getItemFlagged().equals("zone"))
            eventsTypeNum = eventsDAO.getEventsGroupByTypeZone(eventData.getStartDate(), eventData.getEndDate(), user);
        else if (eventData.getDistrictsSelected() != null)
            eventsTypeNum = eventsDAO.getDistrictEventsGroupByTypeDistrict(eventData.getDistrictsSelected().getIdDistricts(), eventData.getStartDate(), eventData.getEndDate());
        else if (eventData.getMunicipalitySelected() != null && !eventData.getMunicipalitySelected().equals(""))
            eventsTypeNum = eventsDAO.getMunicipalityEventsGroupByTypeMunicipality(eventData.getMunicipalitySelected(), eventData.getStartDate(), eventData.getEndDate());
        else if (eventData.getZoneSelected() != null && !eventData.getZoneSelected().equals(""))
            eventsTypeNum = eventsDAO.getZoneEventsGroupByTypeZone(eventData.getZoneSelected(), eventData.getStartDate(), eventData.getEndDate());
        
        //Costruisce asse x
        List<String> xList = new ArrayList<String>();
        
        for (EventsTypeNum e : eventsTypeNum){
            if (!xList.contains(e.getItem())){
                xList.add(e.getItem()); 
            }
        }
        
        //Costruisce barre per i tipi
        int size = xList.size();
        Long[] type1Arr = new Long[size];
        Long[] type2Arr = new Long[size];
        Long[] type3Arr = new Long[size];
        Long[] type4Arr = new Long[size];
        Long[] type5Arr = new Long[size];
        List<Object> type1List = new ArrayList<Object>();
        List<Object> type2List = new ArrayList<Object>();
        List<Object> type3List = new ArrayList<Object>();
        List<Object> type4List = new ArrayList<Object>();
        List<Object> type5List = new ArrayList<Object>();
      
        for (EventsTypeNum e : eventsTypeNum){
            int index = xList.indexOf(e.getItem());
            if (index != -1){
                Long type = e.getType();
                if (type == Integer.parseInt(TYPE_1)){
                    type1Arr[index] = e.getNum();
                } else if (type == Integer.parseInt(TYPE_2)){
                    type2Arr[index] = e.getNum();
                } else if (type == Integer.parseInt(TYPE_3)){
                    type3Arr[index] = e.getNum();
                } else if (type == Integer.parseInt(TYPE_4)){
                    type4Arr[index] = e.getNum();
                } else if (type == Integer.parseInt(TYPE_5)){
                    type5Arr[index] = e.getNum();
                }
            }
        }
        
        type1List.addAll(Arrays.asList(type1Arr));
        type2List.addAll(Arrays.asList(type2Arr));
        type3List.addAll(Arrays.asList(type3Arr));
        type4List.addAll(Arrays.asList(type4Arr));
        type5List.addAll(Arrays.asList(type5Arr));
        
        //Setta i nomi delle columns
        xList.add(0, "x");
        type1List.add(0, type1);
        type2List.add(0, type2);
        type3List.add(0, type3);
        type4List.add(0, type4);
        type5List.add(0, type5);
        
        //Setta i colori
        for (int i = 1; i <= 5; i++){
            result.getPattern().add(patternMap.get(String.valueOf(i)));
        }
        
        //Riempie liste per la POST
        result.getColumns().add(xList);
        result.getColumns().add(type1List);
        result.getColumns().add(type2List);
        result.getColumns().add(type3List);
        result.getColumns().add(type4List);
        result.getColumns().add(type5List);
        result.getGroups().add(typesList);
        
        return result;
    }
    
    @Override
    public List<String> getAllZones(Users user){
        logger.info("received request for 'getAllZones'");
        
        List<String> zones = new ArrayList<String>();
        List<Districts> districtsList = districtsDAO.getAllZones(user);
        
        for (Districts d : districtsList){
            zones.add(d.getZone());
        }
        
        return zones;
    }
    
    @Override
    public List<String> getAllMunicipalities(Users user){
        logger.info("received request for 'getAllMunicipalities'");
        
        List<String> municipalities = new ArrayList<String>();
        List<Districts> districtsList = districtsDAO.getAllMunicipalities(user);
        
        for (Districts d : districtsList){
            municipalities.add(d.getMunicipality());
        }
        
        return municipalities;
    }
    
    //***RC 04/11/2015***
    @Override
    public List<String> getAllWaterAuthorities(Users user){
        logger.info("received request for 'getAllWaterAuthorities'");
        
        List<String> waterAuthorities = new ArrayList<String>();
        List<Districts> districtsList = districtsDAO.getAllWaterAuthorities(user);
        
        for (Districts d : districtsList){
        	waterAuthorities.add(d.getWaterAuthority());
        }
        
        return waterAuthorities;
    }
    //***END***
    
    @Override
    public String getEventsDataCSV(List<Events> dataList, Locale locale) {
        //recupero i dati
        try {
            return createCSV(dataList, locale);
        } catch (IOException e) {
            new BusinessesException("Error on CSV Generation", e);
        }
        return null;
    }
    
    private String createCSV(List<Events> dataList, Locale locale) throws IOException{
        StringBuffer stringBuffer = new StringBuffer();
        CSVPrinter csvPrinter = new CSVPrinter(stringBuffer, CSVFormat.EXCEL);
        
        csvPrinter.print(messages.getMessage("events.form.table.type", null, locale));                    
        csvPrinter.print(messages.getMessage("events.form.table.duration", null, locale));
        csvPrinter.print(messages.getMessage("events.form.table.value", null, locale)); 
        csvPrinter.print(messages.getMessage("events.form.table.ranking", null, locale)); 
        csvPrinter.print(messages.getMessage("events.form.table.day", null, locale)); 
        csvPrinter.print(messages.getMessage("events.form.table.district", null, locale)); 
        csvPrinter.print(messages.getMessage("events.form.table.delta", null, locale)); 
        csvPrinter.print(messages.getMessage("events.form.table.description", null, locale)); 
        csvPrinter.println();
        
        long righe = dataList.size();
        for (int i = 0; i < righe; i++) {
            Events e = dataList.get(i); 
            csvPrinter.print(e.getType());                    
            csvPrinter.print(e.getDuration());
            csvPrinter.print(e.getValue()); 
            csvPrinter.print(e.getRanking()); 
            csvPrinter.print(DateUtil.SDF2SIMPLEUSA.print(e.getDay().getTime())); 
            csvPrinter.print(e.getDistrict_name()); 
            csvPrinter.print(e.getDelta_value()); 
            csvPrinter.print(e.getDescription()); 
            csvPrinter.println();
        }
        csvPrinter.close();
        return stringBuffer.toString();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getEventsGanttDataCSV(List<GanttTask> dataList) {
        //recupero i dati
        try {
            return createCSVFromGanttData(dataList);
        } catch (IOException e) {
            new BusinessesException("Error on CSV Generation", e);
        }
        return null;
    }
    
    private String createCSVFromGanttData(List<GanttTask> dataList) throws IOException{
        StringBuffer stringBuffer = new StringBuffer();
        CSVPrinter csvPrinter = new CSVPrinter(stringBuffer, CSVFormat.EXCEL);
        
        csvPrinter.print("Start date");                    
        csvPrinter.print("End date");
        csvPrinter.print("District"); 
        csvPrinter.print("Event type");
        csvPrinter.println();
        
        long righe = dataList.size();
        for (int i = 0; i < righe; i++) {
            GanttTask gt = dataList.get(i); 
            csvPrinter.print(gt.getStartDate());                    
            csvPrinter.print(gt.getEndDate());
            csvPrinter.print(gt.getTaskName()); 
            csvPrinter.print(gt.getStatus()); 
            csvPrinter.println();
        }
        csvPrinter.close();
        return stringBuffer.toString();
    }
}
