package net.wedjaa.business.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import net.wedjaa.wetnet.business.dao.DataDistrictsDAO;
import net.wedjaa.wetnet.business.dao.DistrictsDAO;
import net.wedjaa.wetnet.business.domain.DataDistricts;
import net.wedjaa.wetnet.business.domain.Districts;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author massimo ricci
 * @author alessandro vincelli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-business.xml" })
public class DataDistrictsDAOTest {

    private final static Logger logger = LoggerFactory.getLogger(DataDistrictsDAOTest.class);
    
    @Autowired
    private DataDistrictsDAO dataDistrictsDAO;

    @Autowired
    private DistrictsDAO districtsDAO;

    @Test
    public void getAllDataDistrictsTest() {
        List<DataDistricts> allDataDistricts = dataDistrictsDAO.getAllDataDistricts();
        assertTrue("Data District non caricati", allDataDistricts.size() > 0);
    }

    @Test
    public void getDataDistrictsByDistrictIdTest() {
        List<Districts> allDistricts = districtsDAO.getAllDistricts();
        assertTrue("Districts non caricati", allDistricts.size() > 0);

        List<DataDistricts> result = dataDistrictsDAO.getDataDistrictsByDistrictId(allDistricts.get(0).getIdDistricts());
        assertTrue("Data District non caricati", result.size() > 0);

    }

    @Test
    public void getJoinedDataDistrictsTest() {
        List<DataDistricts> allDataDistricts = dataDistrictsDAO.getJoinedDataDistricts();
        assertTrue("Data District non caricati", allDataDistricts.size() > 0);
    }

    @Test
    public void getJoinedDataDistrictsByDistrictIdTest() {
        List<Districts> allDistricts = districtsDAO.getAllDistricts();
        List<DataDistricts> result = dataDistrictsDAO.getJoinedDataDistrictsByDistrictId(allDistricts.get(0).getIdDistricts());
        assertTrue("Data District non caricati", result.size() > 0);
    }
    
    @Test
    public void getJoinedDataDistrictsAVGOnDaysTest() {
        List<Districts> allDistricts = districtsDAO.getAllDistricts();
        DateTime start = new DateTime(2014, 3, 26, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        List<DataDistricts> result = dataDistrictsDAO.getJoinedDataDistrictsAVGOnDays(start.toDate(), end.toDate(), allDistricts.get(0).getIdDistricts());
//        for (DataDistricts dataDistricts : result) {
//            System.out.println(ReflectionToStringBuilder.toString(dataDistricts));
//        }
        assertTrue("Data District non caricati", result.size() > 0);
    }
    
    @Test
    public void getJoinedDataDistrictsAVGOnHoursTest() {
        List<Districts> allDistricts = districtsDAO.getAllDistricts();
        DateTime start = new DateTime(2014, 3, 26, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        List<DataDistricts> result = dataDistrictsDAO.getJoinedDataDistrictsAVGOnHours(start.toDate(), end.toDate(), allDistricts.get(0).getIdDistricts());
//        for (DataDistricts dataDistricts : result) {
//            System.out.println(ReflectionToStringBuilder.toString(dataDistricts));
//        }
        assertTrue("Data District non caricati", result.size() > 0);
    }
    
    @Test
    public void getJoinedAllDataDistricts() {
        List<Districts> allDistricts = districtsDAO.getAllDistricts();
        DateTime start = new DateTime(2014, 3, 26, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        List<DataDistricts> result = dataDistrictsDAO.getJoinedAllDataDistricts(start.toDate(), end.toDate(), allDistricts.get(0).getIdDistricts());
//        for (DataDistricts dataDistricts : result) {
//            System.out.println(ReflectionToStringBuilder.toString(dataDistricts));
//        }
        assertTrue("Data District non caricati", result.size() > 0);
    }
    
    
    
    
    
    // Energy Profile
    @Test
    public void getJoinedEnergyProfileAVGOnDaysTest() {
        DateTime start = new DateTime(2014, 1, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 1, 2, 0, 0, 0, 0);
        List<DataDistricts> result = dataDistrictsDAO.getJoinedEnergyProfileAVGOnDays(start.toDate(), end.toDate(), 393);
        assertTrue("Data District non caricati", result.size() > 0);
    }
    
    @Test
    public void getJoinedEnergyProfileAVGOnHoursTest() {
        DateTime start = new DateTime(2014, 1, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 1, 2, 0, 0, 0, 0);
        List<DataDistricts> result = dataDistrictsDAO.getJoinedEnergyProfileAVGOnHours(start.toDate(), end.toDate(), 393);
        assertTrue("Data District non caricati", result.size() > 0);
    }
    
    @Test
    public void getJoinedAllEnergyProfile() {
        DateTime start = new DateTime(2014, 1, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 1, 2, 0, 0, 0, 0);
        List<DataDistricts> result = dataDistrictsDAO.getJoinedAllEnergyProfile(start.toDate(), end.toDate(), 393);
        assertTrue("Data District non caricati", result.size() > 0);
    }
    
    // Profilo Perdite
    
    @Test
    public void getJoinedLossesProfileAVGOnDaysTest() {
        DateTime start = new DateTime(2014, 1, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 10, 0, 0, 0, 0);
        List<DataDistricts> result = dataDistrictsDAO.getJoinedLossesProfileAVGOnDays(start.toDate(), end.toDate(), 73);
        assertTrue("Data District non caricati", result.size() > 0);
    }
    
    @Test
    public void getJoinedLossesProfileAVGOnHoursTest() {
        DateTime start = new DateTime(2014, 1, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 10, 0, 0, 0, 0);
        List<DataDistricts> result = dataDistrictsDAO.getJoinedLossesProfileAVGOnHours(start.toDate(), end.toDate(), 73);
        assertTrue("Data District non caricati", result.size() > 0);
    }
    
    @Test
    public void getJoinedAllLossesProfile() {
        DateTime start = new DateTime(2014, 1, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 10, 0, 0, 0, 0);
        List<DataDistricts> result = dataDistrictsDAO.getJoinedAllLossesProfile(start.toDate(), end.toDate(), 73);
        assertTrue("Data District non caricati", result.size() > 0);
    }
}
