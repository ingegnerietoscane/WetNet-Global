package net.wedjaa.business.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.G2Data;
import net.wedjaa.wetnet.business.domain.G3Data;
import net.wedjaa.wetnet.business.domain.G4Data;
import net.wedjaa.wetnet.business.domain.G6Data;
import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.services.GraphicService;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author alessandro vincelli, massimo ricci
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-business.xml" })
public class GraphicServiceImplTest {

    private final static Logger logger = LoggerFactory.getLogger(GraphicServiceImplTest.class);

    @Autowired
    private GraphicService graphicService;

    @Test
    public void getG4BarChartData(){
        DateTime start = new DateTime(2014, 9, 2, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 12, 3, 0, 0, 0, 0);
        G4Data g4Data = new G4Data();
        g4Data.setStartDate(start.toDate());
        g4Data.setEndDate(end.toDate());
        g4Data.setItemFlagged("district");
        
        G4Data result = graphicService.getG4BarChartData(g4Data, null);
        assertNotNull(result);
    }
    
    @Test
    public void getG6LineChartData(){
        DateTime start = new DateTime(2014, 7, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 10, 13, 0, 0, 0, 0);
        G6Data g6Data = new G6Data();
        g6Data.setStartDate(start.toDate());
        g6Data.setEndDate(end.toDate());
        Districts district1 = new Districts();
        district1.setIdDistricts(133);
        district1.setName("pisa");
        Districts district2 = new Districts();
        district2.setIdDistricts(259);
        district2.setName("filettole");
//        g6Data.getDistrictsSelected().add(district1);
//        g6Data.getDistrictsSelected().add(district2);
        g6Data.getMunicipalitySelected().add("pisa");
        G6Data g6DataSent = graphicService.getG6LineChartData(g6Data);
        assertTrue("columns non caricato", g6DataSent.getColumns().size() > 1);
        
//        List<Object> xlist = ((List<Object>) g6DataSent.getColumns().get(0));
//        List<Object> list1 = ((List<Object>) g6DataSent.getColumns().get(1));
//        List<Object> list2 = ((List<Object>) g6DataSent.getColumns().get(2));
//        List<Object> list3 = ((List<Object>) g6DataSent.getColumns().get(3));
//        List<Object> list4 = ((List<Object>) g6DataSent.getColumns().get(4));
//        for (int i = 0; i < xlist.size(); i++){
//            System.out.print((xlist.get(i) != null) ? xlist.get(i).toString() + " - " : "null" + " - ");
//            System.out.print((list1.get(i) != null) ? list1.get(i).toString() + " - " : "null" + " - ");
//            System.out.println((list2.get(i) != null) ? list2.get(i).toString() + " - " : "null" + " - ");
//            System.out.print((list3.get(i) != null) ? list3.get(i).toString() + " - " : "null" + " - ");
//            System.out.println((list4.get(i) != null) ? list4.get(i).toString() + " - " : "null");
//        }
    }
    
    @Test
    public void getDataG3_1Test() {
        DateTime start = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 2, 0, 0, 0, 0);
        G3Data g3Data = new G3Data();
        g3Data.setStartDate(start.toDate());
        g3Data.setEndDate(end.toDate());
        Districts districts = new Districts();
        districts.setIdDistricts(48);
        g3Data.setDistrictsSelected(districts);
        G3Data result = graphicService.getDataG3_1(g3Data);
        assertNotNull(result);
        assertNotNull(result.getData());
    }
    
    @Test
    public void getDataG3_2Test() {
        DateTime start = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 2, 0, 0, 0, 0);
        G3Data g3Data = new G3Data();
        g3Data.setStartDate(start.toDate());
        g3Data.setEndDate(end.toDate());
        Districts districts = new Districts();
        districts.setIdDistricts(48);
        g3Data.setDistrictsSelected(districts);
        G3Data result = graphicService.getDataG3_2(g3Data);
        assertNotNull(result);
        assertNotNull(result.getData());
    }
    
    @Test
    public void getG2DataTest() {
        DateTime start = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 2, 0, 0, 0, 0);
        G2Data g2Data = new G2Data();
        g2Data.setStartDate(start.toDate());
        g2Data.setEndDate(end.toDate());
        ArrayList<Districts> districts = new ArrayList<Districts>();
        Districts district = new Districts();
        district.setIdDistricts(Long.parseLong("48"));
        districts.add(district);
        g2Data.setDistrictsSelected(districts);
        
        G2Data result = graphicService.getG2Data(g2Data);
        assertNotNull("result non caricato", result);
        assertNotNull("Districts non caricato", result.getStartDate());
        assertNotNull("Districts non caricato", result.getEndDate());
        assertTrue("columns non caricato", result.getColumns().size() > 1);
        //System.out.println(ReflectionToStringBuilder.toString(result));
    }
    
    @Test
    public void getG2Data_OnlyDataMeasuresTest() {
        DateTime start = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 2, 0, 0, 0, 0);
        G2Data g2Data = new G2Data();
        g2Data.setStartDate(start.toDate());
        g2Data.setEndDate(end.toDate());
        ArrayList<Measures> measures = new ArrayList<Measures>();
        Measures measure = new Measures();
        measure.setIdMeasures((Long.parseLong("3651")));
        measures.add(measure);
        g2Data.setMeasuresSelected(measures);
        
        G2Data result = graphicService.getG2Data(g2Data);
        assertNotNull("result non caricato", result);
        assertNotNull("Districts non caricato", result.getStartDate());
        assertNotNull("Districts non caricato", result.getEndDate());
        assertTrue("columns non caricato", result.getColumns().size() > 1);
        List<String> x = (List<String>)result.getColumns().get(0);
        for (String string : x) {
            //System.out.println(string);
            //System.out.println(ReflectionToStringBuilder.toString(x));
                        
        }
    }
    
    @Test
    public void getG2DataCSVTest() {
        DateTime start = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 1, 0, 30, 0, 0);
        G2Data g2Data = new G2Data();
        g2Data.setStartDate(start.toDate());
        g2Data.setEndDate(end.toDate());
        ArrayList<Districts> districts = new ArrayList<Districts>();
        Districts district = new Districts();
        district.setIdDistricts(Long.parseLong("48"));
        districts.add(district);
        g2Data.setDistrictsSelected(districts);
        
        String result = graphicService.getG2DataCSV(g2Data);
        assertNotNull("result non caricato", result);
        //System.out.println(result);
    }

}
