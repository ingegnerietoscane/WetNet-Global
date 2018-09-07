package net.wedjaa.business.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import net.wedjaa.wetnet.business.dao.EventsDAO;
import net.wedjaa.wetnet.business.domain.Events;
import net.wedjaa.wetnet.business.domain.EventsTypeNum;
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
 * @author alessandro vincelli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-business.xml" })
public class EventsDAOTest {

    private final static Logger logger = LoggerFactory.getLogger(EventsDAOTest.class);

    @Autowired
    private EventsDAO eventsDAO;

    @Test
    public void getAll() {
        List<Events> result = eventsDAO.getAll();
        assertTrue("Events not loaded", result.size() > 0);
        assertTrue("Events desc id not loaded", result.get(0).getDescription() != "");
    }
    
    @Test
    public void getAllByDate() {
        DateTime start = new DateTime(2013, 9, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2016, 9, 20, 0, 0, 0, 0);
        Users user = new Users();
        user.setIdusers(3);
        List<Events> result = eventsDAO.getAllByDate(start.toDate(), end.toDate(), user);
        assertTrue("Events not loaded", result.size() > 0);
    }

    @Test
    public void getByDateAndDistrictId() {
        DateTime start = new DateTime(2014, 9, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 9, 20, 0, 0, 0, 0);
        List<Events> result = eventsDAO.getByDateAndDistrictId((long)307, start.toDate(), end.toDate());
        assertTrue("Events not loaded", result.size() > 0);
        assertTrue("Events desc id not loaded", result.get(0).getDescription() != "");
    }
    
    @Test
    public void getEventsTypeNumByMunicipality() {
        DateTime start = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 9, 30, 0, 0, 0, 0);
        List<EventsTypeNum> result = eventsDAO.getEventsTypeNumByMunicipality("pisa", start.toDate(), end.toDate());
        assertTrue("Total Events by municipality not loaded", result.size() > 0);
        assertTrue("Total Events number by municipality not loaded", result.get(0).getNum() > 0);
    }
    
    @Test
    public void getEventsGroupByTypeDistrict() {
        DateTime start = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 9, 30, 0, 0, 0, 0);
        Users user = new Users();
        user.setIdusers(3);
        List<EventsTypeNum> result = eventsDAO.getEventsGroupByTypeDistrict(start.toDate(), end.toDate(), user);
        assertTrue("Total Events grouped by type and district not loaded", result.size() > 0);
    }
    
    @Test
    public void getEventsGroupByTypeZone() {
        DateTime start = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 9, 30, 0, 0, 0, 0);
        Users user = new Users();
        user.setIdusers(3);
        List<EventsTypeNum> result = eventsDAO.getEventsGroupByTypeZone(start.toDate(), end.toDate(), user);
        assertTrue("Total Events grouped by type and zone not loaded", result.size() > 0);
    }
    
    @Test
    public void getEventsGroupByTypeMunicipality() {
        DateTime start = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 9, 30, 0, 0, 0, 0);
        Users user = new Users();
        user.setIdusers(3);
        List<EventsTypeNum> result = eventsDAO.getEventsGroupByTypeMunicipality(start.toDate(), end.toDate(), user);
        assertTrue("Total Events grouped by type and municipality not loaded", result.size() > 0);
    }
}
