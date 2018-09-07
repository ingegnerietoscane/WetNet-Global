package net.wedjaa.business.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import net.wedjaa.wetnet.business.dao.DataMeasuresDAO;
import net.wedjaa.wetnet.business.domain.DataMeasures;

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
public class DataMeasuresDAOTest {

    private final static Logger logger = LoggerFactory.getLogger(DataMeasuresDAOTest.class);

    @Autowired
    private DataMeasuresDAO dataMeasuresDAO;

    @Test
    public void getDataMeasuresAVGonDaysTest() {
        DateTime start = new DateTime(2014, 3, 26, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        List<DataMeasures> result = dataMeasuresDAO.getAVGOnDays(start.toDate(), end.toDate(), (long)3651);
        assertTrue("DataMeasures not loaded", result.size() > 0);
        assertTrue("DataMeasures id not loaded", result.get(0).getMeasuresIdMeasures() != 0);
    }
    
    @Test
    public void getDataMeasuresAVGOnHoursTest() {
        DateTime start = new DateTime(2014, 3, 26, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        List<DataMeasures> result = dataMeasuresDAO.getAVGOnHours(start.toDate(), end.toDate(), (long)3651);
        assertTrue("DataMeasures not loaded", result.size() > 0);
        assertTrue("DataMeasures id not loaded", result.get(0).getMeasuresIdMeasures() != 0);
    }
    
    @Test
    public void getAllDataMeasuresTest() {
        DateTime start = new DateTime(2014, 3, 26, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        List<DataMeasures> result = dataMeasuresDAO.getAllFiltered(start.toDate(), end.toDate(), (long)3651);
        assertTrue("DataMeasures not loaded", result.size() > 0);
    }

}
