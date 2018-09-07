package net.wedjaa.wetnet.web.rest.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wedjaa.wetnet.business.dao.DistrictsDAO;
import net.wedjaa.wetnet.business.dao.MeasuresDAO;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.DistrictsG2;
import net.wedjaa.wetnet.business.domain.G2Data;
import net.wedjaa.wetnet.business.domain.G3Data;
import net.wedjaa.wetnet.business.domain.G4Data;
import net.wedjaa.wetnet.business.domain.G5Data;
import net.wedjaa.wetnet.business.domain.G6Data;
import net.wedjaa.wetnet.business.domain.G7Data;
import net.wedjaa.wetnet.business.domain.G8Data;
import net.wedjaa.wetnet.business.domain.GanttTask;
import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.domain.UsersCFGSParent;
import net.wedjaa.wetnet.business.services.DataDistrictsService;
import net.wedjaa.wetnet.business.services.EventsService;
import net.wedjaa.wetnet.business.services.GraphicService;
import net.wedjaa.wetnet.security.Roles;
import net.wedjaa.wetnet.security.UserDetailsImpl;
import net.wedjaa.wetnet.web.rest.model.C3Object;
import net.wedjaa.wetnet.web.rest.model.D3Data;
import net.wedjaa.wetnet.web.rest.model.D3Object;

import org.apache.commons.lang3.StringUtils;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.joda.time.format.DateTimeFormat;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.render.JsonRenderer;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;


/**
 * Provides some REST services used my D3 services
 * 
 * @author alessandro vincelli, massimo ricci
 */
@Controller
@RequestMapping(value = "/d3")
public class D3Controller {

    @Autowired
    private DataDistrictsService dataDistrictsService;
    @Autowired
    private GraphicService graphicService;
    @Autowired
    private EventsService eventsService;
    @Autowired
    private DistrictsDAO districtsDAO;
    @Autowired
    private MeasuresDAO measuresDAO;
    
    private static Logger log = LoggerFactory.getLogger(D3Controller.class);

    /**
     * All Districts
     * 
     * @param authentication
     * @return 
     */
    //@Secured()
    @RequestMapping(value = "/districts")
    public List<Districts> getDistrictsJSON(Authentication authentication) {
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        List<Districts> result = null;
        if(Roles.isROLE_OPERATOR(details.getAuthorities())){
            result = districtsDAO.getAllDistricts(details.getUsers());
        }
        else{
            result = districtsDAO.getAllDistricts();
        }
        Collections.sort(result);
        
        
//		try {
//			CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:4326",true);
//			CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:3857",true);
//			MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, true);
//			GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
//			Point point = geometryFactory.createPoint(new Coordinate(43.7,10.6));
//			Point targetPoint = (Point) JTS.transform(point, transform);
//	        log.info("FROM 4326 to 3857 ---- "+targetPoint.getX()+ " " +targetPoint.getY());
//	        
//	        
//	        sourceCRS = CRS.decode("EPSG:3857",true);
//			targetCRS = CRS.decode("EPSG:4326",true);
//			transform = CRS.findMathTransform(sourceCRS, targetCRS, true);
//			geometryFactory = new GeometryFactory(new PrecisionModel(), 3857);
//			point = geometryFactory.createPoint(new Coordinate(targetPoint.getX(),targetPoint.getY()));
//			targetPoint = (Point) JTS.transform(point, transform);
//	        log.info("FROM 3857 TO 4326 ---- "+targetPoint.getX()+ " " +targetPoint.getY());
//	        
//	        
//	       
//	        
//	  	} catch (FactoryException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MismatchedDimensionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TransformException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
        
        
        
        
        return result;
    }
    
    /**
     * All Meausures
     * 
     * @param authentication
     * @return 
     */
    //@Secured()
    @RequestMapping(value = "/measures")
    public List<Measures> getMeasuresJSON(Authentication authentication) {
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        List<Measures> result = null;
        if(Roles.isROLE_OPERATOR(details.getAuthorities())){
            result = measuresDAO.getAll(details.getUsers());
        }
        else{
            result = measuresDAO.getAll();
        }
        Collections.sort(result);
        return result;
    }
    
    /**
     *  Meausures del distretto
     * 
     * @param response
     * @param districtId
     * @return 
     */
    //@Secured()
  /*  @RequestMapping(value = "/measures/{districtId}")
    public List<Measures> getMeasuresByDistrictJSON(HttpServletResponse response, @PathVariable("districtId") long districtId) {
        List<Measures> result = measuresDAO.getByDistrictId(districtId);
        Collections.sort(result);
        return result;
    }*/
    
    /*
     * GC - 29/10/2015
     */
    @RequestMapping(value = "/measures/{districtId}")
    public List getMeasuresByDistrictJSON(HttpServletResponse response, @PathVariable("districtId") long districtId, @RequestParam("withSign") boolean withSign) {
        List result = null;
        
        //se withSign == true effettua una query diversa
        if(withSign) result =  measuresDAO.getWithSignByDistrictId(districtId);
        else result =  measuresDAO.getByDistrictId(districtId);
        
        if(result!=null) Collections.sort(result);
        return result;
    }

    /**
     * Data Districts, valori raggruppati per giorno
     * 
     * @param response
     * @return 
     */
    //@Secured()
    @RequestMapping(value = "/g2/json/day")
    public C3Object getDataDistrictsJSON(HttpServletResponse response) {
        C3Object c3Object = new C3Object();
        c3Object.setColumns(dataDistrictsService.getJoinedDataDistricts());
        return c3Object;
    }
    
    /**
     * Grafico 2
     * 
     * @param response
     * @return 
     */
    //@Secured()
    @RequestMapping(value = "/g2",  method = { RequestMethod.POST, RequestMethod.GET } )
    public G2Data getG2(HttpServletResponse response, @RequestBody G2Data g2Data) {
        g2Data = graphicService.getG2Data(g2Data);
       
        return g2Data;
    }    
    
    //***RC 01/12/2015*** 
    /**
     * 
     * @param request
     * @param g2data
     * @param description
     * @return
     */
    @RequestMapping(value = "/g2/sconf", method = { RequestMethod.POST} )
    public String saveG2Configuration(Authentication authentication, HttpServletRequest request, @RequestBody G2Data g2Data) {
        
    	UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
    	
    	boolean result = graphicService.saveG2Configuration(g2Data, (int) details.getIdusers());
    	String str = ""+result;
    	
        return str;
    }
    
    @RequestMapping(value = "/g2/sconf", method = { RequestMethod.GET})
    public List<Object> getAllConfigurations(Authentication authentication, HttpServletResponse response) {
        //se withSign == true effettua una query diversa
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        
        UsersCFGSParent tmp = new UsersCFGSParent();
        tmp.setUsers_idusers(details.getIdusers());
        tmp.setMenu_function(1);//GRAFICI
        tmp.setSubmenu_function(2);//G2
        
        List<Object> result =  graphicService.readAllConfigurations(tmp);
        
        return result;
    }   
    
    @RequestMapping(value = "/g2/pconf", method = { RequestMethod.GET})
    public List<Object> getAllConfigurationParams(Authentication authentication, HttpServletResponse response) {
        //se withSign == true effettua una query diversa
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        
        List<Object> result =  graphicService.readAllConfigurationParams(details.getIdusers());

        return result;
    }   
    
    @RequestMapping(value="/g2/sconf", method = { RequestMethod.DELETE})
    @ResponseBody
    public boolean deleteConfiguration(HttpServletResponse response, @RequestParam("parent") String parentDate) {
    	
    	boolean result = graphicService.removeConfiguration(parentDate);
        return result;
    }
    //***END***
    
    /**
     * Grafico 2, CSV
     * 
     * @param response
     * @param g2Data
     * 
     * @return csv
     */
    //@Secured()
    @RequestMapping(value = "/g2/csv",  method = { RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getG2CSV(HttpServletResponse response, @RequestBody G2Data g2Data) {
        String csv = graphicService.getG2DataCSV(g2Data);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
    

    /**
     * Dati per grafico 3-1
     * 
     * 
     * @param response
     * @param g3Data
     * @return 
     */
    @RequestMapping(value = "/g3_1/json")
    public G3Data getG3_1JSON(HttpServletResponse response, @RequestBody G3Data g3Data) {
        return graphicService.getDataG3_1(g3Data);
    }
    
    /**
     * Dati per grafico 3-2
     * 
     * 
     * @param response
     * @param g3Data
     * @return 
     */
    @RequestMapping(value = "/g3_2/json")
    public G3Data getG3_2JSON(HttpServletResponse response, @RequestBody G3Data g3Data) {
        return graphicService.getDataG3_2(g3Data);
    }

    
    /** GC 06/11/2014
     * Dati per grafico 3-3
     * 
     * 
     * @param response
     * @param g3Data
     * @return 
     */
    @RequestMapping(value = "/g3_3/json")
    public G3Data getG3_3JSON(HttpServletResponse response, @RequestBody G3Data g3Data) {
        return graphicService.getDataG3_3(g3Data);
    }
    
    
    
    /**
     * Grafico 3, CSV
     * 
     * @param response
     * @param List<Object>
     * 
     * @return csv
     */
    //@Secured()
    @RequestMapping(value = "/g3/csv",  method = { RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getG31CSV(HttpServletResponse response, @RequestBody List<Object> dataList) {
        String csv = graphicService.getG3DataCSV(dataList);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
    
    /**
     * 
     * @param request
     * @param g4Data
     * @return
     */
    //@Secured()
    @RequestMapping(value = "/g4", method = { RequestMethod.POST })
    public G4Data getG4BarChart(HttpServletRequest request, @RequestBody G4Data g4Data, Authentication authentication) {
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        
        G4Data result = null;
        if(Roles.isROLE_OPERATOR(details.getAuthorities()))
            result = graphicService.getG4BarChartData(g4Data, details.getUsers());
        else
            result = graphicService.getG4BarChartData(g4Data, null);
        
        return result;
    }
    
    /**
     * Grafico 4, CSV
     * 
     * @param response
     * @param List<Object>
     * 
     * @return csv
     */
    //@Secured()
    @RequestMapping(value = "/g4/csv",  method = { RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getG4CSV(HttpServletResponse response, @RequestBody List<Object> dataList) {
        String csv = graphicService.getG4DataCSV(dataList);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
    
    /**
     * 
     * @param request
     * @param g5Data
     * @return
     */
    //@Secured()
    @RequestMapping(value = "/g5", method = { RequestMethod.POST })
    public G5Data getG5PieChart(HttpServletRequest request, @RequestBody G5Data g5Data) {
        G5Data result = graphicService.getG5PieChartData(g5Data);     
        return result;
    }
    
    /**
     * Grafico 5, CSV
     * 
     * @param response
     * @param List<Object>
     * 
     * @return csv
     */
    //@Secured()
    @RequestMapping(value = "/g5/csv",  method = { RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getG5CSV(HttpServletResponse response, @RequestBody List<Object> dataList) {
        String csv = graphicService.getG5DataCSV(dataList);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
    
    /**
     * 
     * @param request
     * @param g6Data
     * @return
     */
    //@Secured()
    @RequestMapping(value = "/g6", method = { RequestMethod.POST })
    public G6Data getG6LineChart(HttpServletRequest request, @RequestBody G6Data g6Data) {
        G6Data result = graphicService.getG6LineChartData(g6Data);     
        return result;
    }
    
    /**
     * Grafico 6, CSV
     * 
     * @param response
     * @param List<Object>
     * 
     * @return csv
     */
    //@Secured()
    @RequestMapping(value = "/g6/csv",  method = { RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getG6CSV(HttpServletResponse response, @RequestBody List<Object> dataList) {
        String csv = graphicService.getG6DataCSV(dataList);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
    
    /**
     * 
     * @param request
     * @param g6Data
     * @return
     */
    //@Secured()
    @RequestMapping(value = "/g7", method = { RequestMethod.POST })
    public G7Data getG7LineChart(HttpServletRequest request, @RequestBody G7Data g7Data) {
        G7Data result = graphicService.getG7LineChartData(g7Data);     
        return result;
    }
    
    //***RC 01/12/2015*** 
    /**
     * 
     * @param request
     * @param g2data
     * @param description
     * @return
     */
    @RequestMapping(value = "/g7/sconf", method = { RequestMethod.POST} )
    public String saveG7Configuration(Authentication authentication, HttpServletRequest request, @RequestBody G7Data g7Data) {
        
    	UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
    	
    	boolean result = graphicService.saveG7Configuration(g7Data, (int) details.getIdusers());
    	String str = ""+result;
    	
        return str;
    }
    
    @RequestMapping(value = "/g7/sconf", method = { RequestMethod.GET})
    public List<Object> getAllConfigurationsG7(Authentication authentication, HttpServletResponse response) {
        //se withSign == true effettua una query diversa
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        
        UsersCFGSParent tmp = new UsersCFGSParent();
        tmp.setUsers_idusers(details.getIdusers());
        tmp.setMenu_function(1);//GRAFICI
        tmp.setSubmenu_function(7);//G7
        
        List<Object> result =  graphicService.readAllConfigurations(tmp);
        
        return result;
    }   
    
    @RequestMapping(value = "/g7/pconf", method = { RequestMethod.GET})
    public List<Object> getAllConfigurationParamsG7(Authentication authentication, HttpServletResponse response) {
        //se withSign == true effettua una query diversa
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        
        List<Object> result =  graphicService.readAllConfigurationParams(details.getIdusers());

        return result;
    }   
    
    @RequestMapping(value="/g7/sconf", method = { RequestMethod.DELETE})
    @ResponseBody
    public boolean deleteConfigurationG7(HttpServletResponse response, @RequestParam("parent") String parentDate) {
    	
    	boolean result = graphicService.removeConfiguration(parentDate);
        return result;
    }
    //***END***
    
    
    /**
     * Grafico 7, CSV
     * 
     * @param response
     * @param List<Object>
     * 
     * @return csv
     */
    //@Secured()
    @RequestMapping(value = "/g7/csv",  method = { RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getG7CSV(HttpServletResponse response, @RequestBody List<Object> dataList) {
        String csv = graphicService.getG7DataCSV(dataList);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
    
    /**
     * Grafico 1 Eventi, CSV
     * 
     * @param response
     * @param List<Object>
     * 
     * @return csv
     */
    //@Secured()
    @RequestMapping(value = "/eventsG1/csv",  method = { RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getEventsG1CSV(HttpServletResponse response, @RequestBody List<Object> dataList) {
        String csv = graphicService.getEventsG1DataCSV(dataList);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
    
    /**
     * Grafico 2 Eventi, CSV
     * 
     * @param response
     * @param List<Object>
     * 
     * @return csv
     */
    //@Secured()
    @RequestMapping(value = "/eventsG2/csv",  method = { RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getEventsG2CSV(HttpServletResponse response, @RequestBody List<Object> dataList) {
        String csv = graphicService.getEventsG2DataCSV(dataList);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
    
    /**
     * Grafico 3 Eventi, CSV
     * 
     * @param response
     * @param List<Object>
     * 
     * @return csv
     */
    //@Secured()
    @RequestMapping(value = "/eventsG3/csv",  method = { RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getEventsG3CSV(HttpServletResponse response, @RequestBody List<Object> dataList) {
        String csv = graphicService.getEventsG3DataCSV(dataList);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
    
    /**
     * Grafico Gantt Eventi, CSV
     * 
     * @param response
     * @param List<GanttTask>
     * 
     * @return csv
     */
    //@Secured()
    @RequestMapping(value = "/eventsGantt/csv",  method = { RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getEventsGanttCSV(HttpServletResponse response, @RequestBody List<GanttTask> dataList) {
        String csv = eventsService.getEventsGanttDataCSV(dataList);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
    
    /**
     * Data Districts, valori raggruppati per giorno
     * 
     * @param response
     * @return csv
     */
    //@Secured()
    @RequestMapping(value = "/datadistricts/day")
    @ResponseBody
    public String getDataDistricts(HttpServletResponse response) {
        C3Object c3Object = new C3Object();
        c3Object.setX("x");
        ArrayList<Object> list1 = new ArrayList<Object>();
        list1.add("x");
        list1.add("2013-01-01");
        list1.add("2013-01-02");
        list1.add("2013-01-03");
        c3Object.getColumns().add(list1);

        ArrayList<Object> list2 = new ArrayList<Object>();
        list2.add("data1");
        list2.add(30);
        list2.add(50);
        list2.add(20);
        c3Object.getColumns().add(list2);

        ArrayList<Object> list3 = new ArrayList<Object>();
        list3.add("data2");
        list3.add(10);
        list3.add(50);
        list3.add(40);
        c3Object.getColumns().add(list3);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return dataDistrictsService.getJoinedDataDistrictsCSV();
    }

    /**
     *
     * 
     * @param id
     * @return tsv
     */
    //@Secured()
    @RequestMapping(value = "/test2/{id}")
    @ResponseBody
    public String test(@PathVariable long id, HttpServletResponse response) {
        Assert.notNull(id, "the id cannot be null");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        DataTable dataTable = new DataTable();
        CharSequence renderDataTable = JsonRenderer.renderDataTable(dataTable, true, false, true);
        DistrictsG2 g2 = dataDistrictsService.getDataDistrictsByDistrictId(id);
        g2 = dataDistrictsService.getAllDataDistricts();

        StringBuffer sb = new StringBuffer();
        sb.append("date\t");
        for (int i = 0; i < g2.getDistricts().size(); i++) {
            Districts d = g2.getDistricts().get(i);
            sb.append(d.getName());
            if (i < g2.getDistricts().size() - 1) {
                sb.append("\t");
            }
        }
        sb.append("\n");
        ArrayList<D3Data> list = new ArrayList<D3Data>();

        SortedSet<Date> datSet = new TreeSet<Date>(new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o1.compareTo(o2);
            }
        });
        for (Date date : g2.getMap().keySet()) {
            datSet.add(date);
        }

        for (Date date : datSet) {
            sb.append(StringUtils.trim(DateTimeFormat.forPattern("yyyy-MM-dd-HH_mm_ss").print(date.getTime())));
            sb.append("\t");

            for (int i = 0; i < g2.getDistricts().size(); i++) {
                sb.append(g2.getMap().get(date)[i]);
                if (i < g2.getDistricts().size() - 1) {
                    sb.append("\t");
                }
                if (i == g2.getDistricts().size() - 1) {
                    sb.append("\n");
                }
            }

        }

        return sb.toString();

    }

    /**
     * 
     * 
     * @param id (not null)
     * @param model
     * @return the request risto
     */
    //@Secured()
    @RequestMapping(value = "/test/{id}")
    public D3Object test2(@PathVariable long id, Model model) {
        Assert.notNull(id, "the id cannot be null");

        DistrictsG2 g2 = dataDistrictsService.getDataDistrictsByDistrictId(id);

        ArrayList<D3Data> list = new ArrayList<D3Data>();
        for (Date date : g2.getMap().keySet()) {
            D3Data d3Data = new D3Data();
            d3Data.setTooltip(date.toString());
            d3Data.setX(date.toString());
            d3Data.setY(g2.getMap().get(date));
            list.add(d3Data);
        }

        D3Object d3Object = new D3Object();
        d3Object.setSeries(g2.getSeries());
        d3Object.setData(list);
        return d3Object;

    }

    public static void main(String[] args) {
        DistrictsG2 g2 = new DistrictsG2();
        StringBuffer sb = new StringBuffer();
        sb.append("name\tpippo");
        System.out.println(sb.toString());

    }
    
    
    /*GC 16/11/2015*/
    /**
     * Grafico 8
     * 
     * @param response
     * @return 
     */
    //@Secured()
    @RequestMapping(value = "/g8",  method = { RequestMethod.POST, RequestMethod.GET } )
    public G8Data getG8(HttpServletResponse response, @RequestBody G8Data g8Data) {
        g8Data = graphicService.getG8Data(g8Data);
        return g8Data;
    }    
    
    
    /**
     * Grafico 8 CSV
     * 
     * @param response
     * @param g8Data
     * 
     * @return csv
     */
    //@Secured()
    @RequestMapping(value = "/g8/csv",  method = { RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getG8CSV(HttpServletResponse response, @RequestBody G8Data g8Data) {
        String csv = graphicService.getG8DataCSV(g8Data);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
    
    //***RC 01/12/2015*** 
    /**
     * 
     * @param request
     * @param g2data
     * @param description
     * @return
     */
    @RequestMapping(value = "/g8/sconf", method = { RequestMethod.POST} )
    public String saveG8Configuration(Authentication authentication, HttpServletRequest request, @RequestBody G8Data g8Data) {
        
    	UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
    	
    	boolean result = graphicService.saveG8Configuration(g8Data, (int) details.getIdusers());
    	String str = ""+result;
    	
        return str;
    }
    
    @RequestMapping(value = "/g8/sconf", method = { RequestMethod.GET})
    public List<Object> getAllConfigurationsG8(Authentication authentication, HttpServletResponse response) {
        //se withSign == true effettua una query diversa
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        
        UsersCFGSParent tmp = new UsersCFGSParent();
        tmp.setUsers_idusers(details.getIdusers());
        tmp.setMenu_function(1);//GRAFICI
        tmp.setSubmenu_function(8);//G8
        
        List<Object> result =  graphicService.readAllConfigurations(tmp);
        
        return result;
    }   
    
    @RequestMapping(value = "/g8/pconf", method = { RequestMethod.GET})
    public List<Object> getAllConfigurationParamsG8(Authentication authentication, HttpServletResponse response) {
        //se withSign == true effettua una query diversa
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        
        List<Object> result =  graphicService.readAllConfigurationParams(details.getIdusers());

        return result;
    }   
    
    @RequestMapping(value="/g8/sconf", method = { RequestMethod.DELETE})
    @ResponseBody
    public boolean deleteConfigurationG8(HttpServletResponse response, @RequestParam("parent") String parentDate) {
    	
    	boolean result = graphicService.removeConfiguration(parentDate);
        return result;
    }
    //***END***
}
