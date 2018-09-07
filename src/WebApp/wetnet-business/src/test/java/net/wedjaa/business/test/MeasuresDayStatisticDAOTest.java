/**
 * 
 */
package net.wedjaa.business.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import net.wedjaa.wetnet.business.dao.MeasuresDayStatisticDAO;
import net.wedjaa.wetnet.business.domain.DayStatisticJoinMeasures;

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
public class MeasuresDayStatisticDAOTest {

    private final static Logger logger = LoggerFactory.getLogger(MeasuresDayStatisticDAOTest.class);
    
    @Autowired
    MeasuresDayStatisticDAO measuresDayStatisticDAO;
    
    @Test
    public void getDayStatisticJoinMeasures(){
        DateTime start = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 9, 30, 0, 0, 0, 0);
        List<DayStatisticJoinMeasures> result = measuresDayStatisticDAO.getDayStatisticJoinMeasures(start.toDate(), end.toDate(), 49);
        assertTrue("Data not loaded", result.size() > 0);
    }
}
