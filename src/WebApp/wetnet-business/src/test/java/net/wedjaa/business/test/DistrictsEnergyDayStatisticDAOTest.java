/**
 * 
 */
package net.wedjaa.business.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import net.wedjaa.wetnet.business.dao.DistrictsEnergyDayStatisticDAO;
import net.wedjaa.wetnet.business.domain.EpdIedIela;
import net.wedjaa.wetnet.business.domain.Users;

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
public class DistrictsEnergyDayStatisticDAOTest {

    private final static Logger logger = LoggerFactory.getLogger(DistrictsEnergyDayStatisticDAOTest.class);

    @Autowired
    DistrictsEnergyDayStatisticDAO districtsEnergyDayStatisticDAO;
    
    @Test
    public void getAllDistrictsByDate(){
        DateTime start = new DateTime(2014, 7, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 10, 30, 0, 0, 0, 0);
        Users user = new Users();
        user.setIdusers(3);
        List<EpdIedIela> result = districtsEnergyDayStatisticDAO.getAllDistrictsByDate(user, start.toDate(), end.toDate());
        assertTrue("Data not loaded", result.size() > 0);
    }
    
    @Test
    public void getAllMunicipalitiesByDate(){
        DateTime start = new DateTime(2014, 7, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 10, 30, 0, 0, 0, 0);
        Users user = new Users();
        user.setIdusers(3);
        List<EpdIedIela> result = districtsEnergyDayStatisticDAO.getAllMunicipalitiesByDate(user, start.toDate(), end.toDate());
        assertTrue("Data not loaded", result.size() > 0);
    }
    
    @Test
    public void getAllZonesByDate(){
        DateTime start = new DateTime(2014, 7, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 10, 30, 0, 0, 0, 0);
        Users user = new Users();
        user.setIdusers(3);
        List<EpdIedIela> result = districtsEnergyDayStatisticDAO.getAllZonesByDate(user, start.toDate(), end.toDate());
        assertTrue("Data not loaded", result.size() > 0);
    }
    
}
