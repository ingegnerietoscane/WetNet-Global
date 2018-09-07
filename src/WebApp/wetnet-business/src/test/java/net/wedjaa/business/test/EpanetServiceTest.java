package net.wedjaa.business.test;

import static org.junit.Assert.assertTrue;
import net.wedjaa.wetnet.business.dao.DistrictsDAO;
import net.wedjaa.wetnet.business.dao.MeasuresDAO;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.EpanetData;
import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.services.EpanetService;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author alessandro vincelli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-business.xml" })
public class EpanetServiceTest {

    @Autowired
    private EpanetService epanetService;
    @Autowired
    private DistrictsDAO districtsDAO;
    @Autowired
    private MeasuresDAO measuresDAO;

    @Test
    public void get_PATFileTest() {
        DateTime start = new DateTime(2014, 1, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 1, 2, 0, 0, 0, 0);
        EpanetData epanetData = new EpanetData();
        epanetData.setStartDate(start.toDate());
        epanetData.setEndDate(end.toDate());
        Districts districts = new Districts();
        districts.setIdDistricts(393);
        epanetData.setDistrictsSelected(districts);
        String patFile = epanetService.get_PATFile(epanetData);
        //System.out.print(patFile);
        assertTrue("patFile empty", !patFile.isEmpty());
    }
    
    @Test
    public void get_DATFileTest() {
        
        Measures measureFlow =  measuresDAO.getById(5637);//tipo 0 , flow
        Measures measurePressure =  measuresDAO.getById(4888);//tipo 0 , flow
        
        DateTime start = new DateTime(2014, 1, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 1, 2, 0, 0, 0, 0);
        EpanetData epanetData = new EpanetData();
        epanetData.setStartDate(start.toDate());
        epanetData.setEndDate(end.toDate());
        Districts districts = new Districts();
        districts.setIdDistricts(393);
        epanetData.setDistrictsSelected(districts);
        epanetData.getMeasuresSelected().add(measureFlow);
        epanetData.getMeasuresSelected().add(measurePressure);
        String datFile = epanetService.get_DATFile(epanetData);
        //System.out.print(datFile);
        assertTrue("datFile empty", !datFile.isEmpty());
    }

}
