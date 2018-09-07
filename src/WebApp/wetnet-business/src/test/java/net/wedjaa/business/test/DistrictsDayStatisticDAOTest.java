/**
 * 
 */
package net.wedjaa.business.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import net.wedjaa.wetnet.business.dao.DistrictsDayStatisticDAO;
import net.wedjaa.wetnet.business.domain.DayStatisticJoinDistrictsJoinEnergy;
import net.wedjaa.wetnet.business.domain.DistrictsDayStatistic;

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
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-business.xml" })
public class DistrictsDayStatisticDAOTest {
    
    private final static Logger logger = LoggerFactory.getLogger(DistrictsDayStatisticDAOTest.class);

    @Autowired
    DistrictsDayStatisticDAO districtsDayStatisticDAO;
    
    @Test
    public void getDayStatisticJoinDistrictsJoinEnergy(){
        DateTime start = new DateTime(2014, 7, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 10, 30, 0, 0, 0, 0);
        List<DayStatisticJoinDistrictsJoinEnergy> result = districtsDayStatisticDAO.getDayStatisticJoinDistrictsJoinEnergy(start.toDate(), end.toDate(), 133L);
        assertTrue("Data not loaded", result.size() > 0);
    }
    
    @Test
    public void getAllDistrictsDayStatistic(){
        List<DistrictsDayStatistic> result = districtsDayStatisticDAO.getAllDistrictsDayStatistic();
        assertTrue("Data not loaded", result.size() > 0);
    }
    
    @Test
    public void getDistrictsDayStatisticById(){
        DateTime start = new DateTime(2014, 7, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 10, 30, 0, 0, 0, 0);
        List<DistrictsDayStatistic> result = districtsDayStatisticDAO.getDistrictsDayStatisticById(start.toDate(), end.toDate(), 48);
        assertTrue("Data not loaded", result.size() > 0);
    }
    
    @Test
    public void getDistrictsDayStatisticByDistrict(){
        DateTime start = new DateTime(2013, 1, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2016, 1, 1, 0, 0, 0, 0);
        List<DistrictsDayStatistic> result = districtsDayStatisticDAO.getDistrictsDayStatisticByDistrict(start.toDate(), end.toDate(), 133);
        assertTrue("Data not loaded", result.size() > 0);
    }
    
    @Test
    public void getDistrictsDayStatisticByMunicipality(){
        DateTime start = new DateTime(2013, 1, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2016, 1, 1, 0, 0, 0, 0);
        List<DistrictsDayStatistic> result = districtsDayStatisticDAO.getDistrictsDayStatisticByMunicipality(start.toDate(), end.toDate(), "pisa");
        assertTrue("Data not loaded", result.size() > 0);
    }
    
    @Test
    public void getDistrictsDayStatisticByZone(){
        DateTime start = new DateTime(2013, 1, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2016, 1, 1, 0, 0, 0, 0);
        List<DistrictsDayStatistic> result = districtsDayStatisticDAO.getDistrictsDayStatisticByZone(start.toDate(), end.toDate(), "pisa");
        assertTrue("Data not loaded", result.size() > 0);
    }
}
