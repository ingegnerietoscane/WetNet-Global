package net.wedjaa.business.test;

import static org.junit.Assert.assertTrue;

import java.util.Locale;

import net.wedjaa.wetnet.business.domain.CatChartData;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.EventsData;
import net.wedjaa.wetnet.business.domain.PieChartData;
import net.wedjaa.wetnet.business.services.EventsService;

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
public class EventsServiceTest {

    private final static Logger logger = LoggerFactory.getLogger(EventsServiceTest.class);

    @Autowired
    private EventsService eventService;

    @Test
    //@Ignore("custom message")
    public void getEventsCatChartData() {
        DateTime start = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 9, 30, 0, 0, 0, 0);
        EventsData eventData = new EventsData();
        eventData.setStartDate(start.toDate());
        eventData.setEndDate(end.toDate());
        eventData.setItemFlagged("district");
        CatChartData result = eventService.getEventsCatChartData(eventData, Locale.ITALIAN, null); 
        assertTrue("Data not loaded", result.getColumns().size() > 0);
        assertTrue("Data desc id not loaded", result.getGroups().size() > 0);
    }
    
    @Test
    //@Ignore("custom message")
    public void getEventsPieChartData() {
        DateTime start = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 11, 30, 0, 0, 0, 0);
        EventsData eventData = new EventsData();
        eventData.setStartDate(start.toDate());
        eventData.setEndDate(end.toDate());
        eventData.setDistrictsSelected(new Districts());
        eventData.getDistrictsSelected().setIdDistricts(393);
        PieChartData result = eventService.getEventsPieChartData(eventData, Locale.ITALIAN);
        assertTrue("Data not loaded", result.getColumns().size() > 0);
    }
}
