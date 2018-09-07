package net.wedjaa.business.test;

import java.util.List;

import net.wedjaa.wetnet.business.dao.DistrictsDayStatisticDAO;
import net.wedjaa.wetnet.business.domain.DistrictsDayStatistic;

import org.joda.time.DateTime;
import org.junit.Assert;
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
public class DistrictsDayStatisticServiceTest {

    private final static Logger logger = LoggerFactory.getLogger(DistrictsDayStatisticServiceTest.class);

    @Autowired
    private DistrictsDayStatisticDAO districtsDayStatisticService;

    @Test
    public void start() {
        DateTime start = new DateTime(2014, 3, 26, 0, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 1, 0, 0, 0, 0);
        List<DistrictsDayStatistic> list = districtsDayStatisticService.getDistrictsDayStatisticById(start.toDate(), end.toDate(), 48);
        Assert.assertNotNull("dati non  caricati", list);

    }

}
