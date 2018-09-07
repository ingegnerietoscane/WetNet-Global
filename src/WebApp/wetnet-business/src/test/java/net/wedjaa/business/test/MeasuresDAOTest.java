package net.wedjaa.business.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import net.wedjaa.wetnet.business.dao.MeasuresDAO;
import net.wedjaa.wetnet.business.dao.UsersDAO;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.domain.Users;

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
public class MeasuresDAOTest {

    private final static Logger logger = LoggerFactory.getLogger(MeasuresDAOTest.class);

    @Autowired
    private MeasuresDAO measuresDAO;
    @Autowired
    private UsersDAO usersDAO;

    @Test
    public void getAllDataDistrictsTest() {
        List<Measures> result = measuresDAO.getAll();
        assertTrue("Measures not loaded", result.size() > 0);
        assertTrue("DataMeasures id not loaded", result.get(0).getIdMeasures() != 0);
    }

    @Test
    public void getByDistrictIdTest() {
        List<Measures> result = measuresDAO.getByDistrictId((long) 48);
        assertTrue("Measures not loaded", result.size() > 0);
        assertTrue("DataMeasures id not loaded", result.get(0).getIdMeasures() != 0);
    }

    @Test
    public void getAllFilteredOnUserTest() {
        Users users = usersDAO.getByUserName("admin");
        List<Measures> result = measuresDAO.getAll(users);
        assertTrue("Measures not loaded", result.size() > 0);
        assertTrue("DataMeasures id not loaded", result.get(0).getIdMeasures() != 0);
    }
}
