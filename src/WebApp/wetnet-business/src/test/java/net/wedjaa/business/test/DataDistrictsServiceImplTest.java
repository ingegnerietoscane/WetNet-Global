package net.wedjaa.business.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.wedjaa.wetnet.business.domain.DistrictsG2;
import net.wedjaa.wetnet.business.services.DataDistrictsService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author massimo ricci
 * @author alessandro vincelli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-business.xml" })
public class DataDistrictsServiceImplTest {

    private final static Logger logger = LoggerFactory.getLogger(DataDistrictsServiceImplTest.class);

    @Autowired
    private DataDistrictsService dataDistrictsService;

    @Test
    public void getAllDataDistrictsTest() {
        DistrictsG2 result = dataDistrictsService.getAllDataDistricts();
        assertNotNull("result non caricato", result);
        assertTrue("Districts non caricato", result.getDistricts().size() > 0);
        assertTrue("Map non caricato", result.getMap().keySet().size() > 0);
        //System.out.println(ReflectionToStringBuilder.toString(result));
    }

    @Test
    public void getJoinedDataDistrictsCSVTest() {
        String result = dataDistrictsService.getJoinedDataDistrictsCSV();
        assertNotNull("csv non caricato", result);

        //System.out.println(result);
    }
    

}
