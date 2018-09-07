package net.wedjaa.business.test;

import static org.junit.Assert.assertTrue;
import net.wedjaa.wetnet.business.services.MapViewService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author alessandro vincelli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-business.xml" })
public class MapViewDAOTest {

    @Autowired
    private MapViewService mapViewService;

    @Test
    @Ignore
    public void get() {
        String result = mapViewService.getDistrictsKLM();
        assertTrue("result not loaded", StringUtils.isNotBlank(result));
    }



}
