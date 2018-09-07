/**
 * 
 */
package net.wedjaa.wetnet.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.dao.DataDistrictsDAO;
import net.wedjaa.wetnet.business.dao.DistrictsDAO;
import net.wedjaa.wetnet.business.dao.DistrictsDayStatisticDAO;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.DistrictsDayStatistic;
import net.wedjaa.wetnet.util.WetnetWebUtils;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author massimo ricci
 *
 */
@Controller
@RequestMapping("/graphics")
public class GraphicsController {

    private  static final Logger logger = Logger.getLogger("GraphicsController");
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private DistrictsDAO districtsService;
	
	@Autowired
	private DistrictsDayStatisticDAO districtsDayStatisticService;
	
	@Autowired
    private DataDistrictsDAO dataDistrictsDAO;

	@RequestMapping(value = "/statistic", method = RequestMethod.GET)
	public String getGraphic(ModelMap model, @RequestParam(value="idDistricts", required=false) Long idDistricts,
	                                                                        @RequestParam(value="districtsName", required=false) String districtsName) {
		logger.info("Received request '/statistic'");
		
		List<Districts> districtsList = districtsService.getAllDistricts();
		
		// Crea Select Option con i distretti
		SortedMap<Long, String> districtsMap = WetnetWebUtils.getDistrictsMap(districtsList);
		
		model.addAttribute("districtsMap", districtsMap);
		
		
		
		
		
		String jsonResponse = "";
        String jsonAvgDay = "";
        String jsonMinNight = "";
        String jsonRealLeakage = "";
        String jsonFlags = "";
		
		if (idDistricts != null){
		    
		    for (Districts d : districtsList)
	              if (d.getIdDistricts() == idDistricts.longValue())
	                  districtsName = d.getName();
		    
            DateTime start = new DateTime(1900, 1, 1, 0, 0, 0, 0);
            DateTime end = new DateTime(2999, 12, 31, 0, 0, 0, 0);
    		
    		List<DistrictsDayStatistic> dataList = districtsDayStatisticService.getDistrictsDayStatisticById(start.toDate(), end.toDate(), idDistricts);
    		
    		
    		
    		//Crea le liste con i dati da visualizzare nel grafico
    		List<Double[]> avgDayList = new ArrayList<Double[]>();
    		List<Double[]> minNightList = new ArrayList<Double[]>();
    		List<Double[]> realLeakageList = new ArrayList<Double[]>();
    		List<String[]> flagsList = new ArrayList<String[]>();
    		
    		
    		if(dataList.size() > 0){
	    		Double min = dataList.get(0).getAvgDay();
	    		Double max = dataList.get(0).getAvgDay();
	    		long imin = 0;
	    		long imax = 0;
	    		for (int i = 0; i < dataList.size(); i++){
	    			double index = (double) i;
	    			avgDayList.add(new Double[] {index, dataList.get(i).getAvgDay()});
	    			minNightList.add(new Double[] {index, dataList.get(i).getMinNight()});
	    			realLeakageList.add(new Double[] {index, dataList.get(i).getRealLeakage()});
	    			
	    			if (dataList.get(i).getAvgDay() != null){
	        			if (min > dataList.get(i).getAvgDay()){
	        				min = dataList.get(i).getAvgDay();
	        				imin = i;
	        			}
	        			if (max < dataList.get(i).getAvgDay()){
	        				max = dataList.get(i).getAvgDay();
	        				imax = i;
	        			}
	    			}
	    		}
	    		flagsList.add(new String[] {String.valueOf(imax), "max"});
	    		flagsList.add(new String[] {String.valueOf(imin), "min"});
    		}
		
    		//Trasforma in json le liste dei dati da visualizzare
    		try {
    			jsonResponse = this.mapper.writeValueAsString(dataList);
    			jsonAvgDay = this.mapper.writeValueAsString(avgDayList);
    			jsonMinNight = this.mapper.writeValueAsString(minNightList);
    			jsonRealLeakage = this.mapper.writeValueAsString(realLeakageList);
    			jsonFlags = this.mapper.writeValueAsString(flagsList);
    		} catch (IOException e) {
    			logger.log(Level.SEVERE, e.getMessage());
    		}
		}
		
		model.addAttribute("idDistricts", idDistricts);
        model.addAttribute("districtsName", districtsName);
		model.addAttribute("jsonResponse", jsonResponse);
		model.addAttribute("jsonAvgDay", jsonAvgDay);
		model.addAttribute("jsonMinNight", jsonMinNight);
		model.addAttribute("jsonRealLeakage", jsonRealLeakage);
		model.addAttribute("jsonFlags", jsonFlags);
		
		return "wetnet/graphics/statistic";
	}
	   
    @RequestMapping(value = "/statistic-g2", method = RequestMethod.GET)
    public String getGraphicG2(ModelMap model, Principal principal,
                            @RequestParam(value = "idDistricts", required = false) Long idDistricts, @RequestParam(value = "day", required = false) String day,
                            @RequestParam(value = "duration", required = false) Long duration) {
        model.put("idDistricts", idDistricts);
        model.put("day", day);
        model.put("duration", duration);
        return "wetnet/graphics/statistic-g2";
    }
    
    //***RC 03/11/2015***
    @RequestMapping(value = "/statistic-g2M", method = RequestMethod.GET)
    public String getGraphicG2Measures(ModelMap model, Principal principal,
                            @RequestParam(value = "idMeasures", required = false) Long idMeasures, @RequestParam(value = "day", required = false) String day,
                            @RequestParam(value = "duration", required = false) Long duration) {
    	
        model.put("idMeasures", idMeasures);
        model.put("day", day);
        model.put("duration", duration);
        return "wetnet/graphics/statistic-g2";
    }
    //***END***
    
    @RequestMapping(value = "/statistic-g3_1", method = RequestMethod.GET)
    public String getGraphicG3_1(ModelMap model, Principal principal, @RequestParam(value = "idDistricts", required = false) Long idDistricts) {
        return "wetnet/graphics/statistic-g3_1";
    }
    
    @RequestMapping(value = "/statistic-g3_2", method = RequestMethod.GET)
    public String getGraphicG3_2(ModelMap model, Principal principal, @RequestParam(value = "idDistricts", required = false) Long idDistricts) {
        return "wetnet/graphics/statistic-g3_2";
    }
    	
    @RequestMapping(value = "/statistic-g4", method = RequestMethod.GET)
    public String getGraphicG4(ModelMap model, Principal principal) {
        return "wetnet/graphics/statistic-g4";
    }
    
    @RequestMapping(value = "/statistic-g5", method = RequestMethod.GET)
    public String getGraphicG5(ModelMap model, Principal principal) {
        return "wetnet/graphics/statistic-g5";
    }
    
    @RequestMapping(value = "/statistic-g6", method = RequestMethod.GET)
    public String getGraphicG6(ModelMap model, Principal principal) {
        return "wetnet/graphics/statistic-g6";
    }
    
    @RequestMapping(value = "/statistic-g7", method = RequestMethod.GET)
    public String getGraphicG7(ModelMap model, Principal principal,
                            @RequestParam(value = "idDistricts", required = false) Long idDistricts, @RequestParam(value = "day", required = false) String day,
                            @RequestParam(value = "duration", required = false) Long duration) {
        model.put("idDistricts", idDistricts);
        model.put("day", day);
        model.put("duration", duration);
        return "wetnet/graphics/statistic-g7";
    }
    
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal) {
		logger.info("Received request '/dashboard'");
		String name = principal.getName();
		model.addAttribute("username", name);
		return "wetnet/graphics/dashboard";
	}
	
	
	/*GC 16/11/2015*/
	 @RequestMapping(value = "/statistic-g8", method = RequestMethod.GET)
	    public String getGraphicG8(ModelMap model, Principal principal,
	                            @RequestParam(value = "idDistricts", required = false) Long idDistricts, @RequestParam(value = "day", required = false) String day,
	                            @RequestParam(value = "duration", required = false) Long duration) {
	        model.put("idDistricts", idDistricts);
	        model.put("day", day);
	        model.put("duration", duration);
	        return "wetnet/graphics/statistic-g8";
	    }
}
