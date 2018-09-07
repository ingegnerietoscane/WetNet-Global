package net.wedjaa.business.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import net.wedjaa.wetnet.business.dao.DistrictsDAO;
import net.wedjaa.wetnet.business.dao.UsersDAO;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.MeasuresHasDistricts;
import net.wedjaa.wetnet.business.domain.Users;
import net.wedjaa.wetnet.business.domain.UsersHasDistricts;

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
public class DistrictsDAOTest {

    @Autowired
    private DistrictsDAO districtsDAO;
    @Autowired
    private UsersDAO usersDAO;

    @Test
    public void getAllTest() {
        List<Districts> result = districtsDAO.getAllDistricts();
        assertTrue("districts not loaded", result.size() > 0);
        assertTrue("districts name not loaded", result.get(0).getName() != "");
    }
    
    @Test
    public void getAllFilteredOnUserTest() {
        Users users = usersDAO.getByUserName("admin");
        List<Districts> result = districtsDAO.getAllDistricts(users);
        assertTrue("districts not loaded", result.size() > 0);
        assertTrue("districts name not loaded", result.get(0).getName() != "");
    }

    @Test
    public void saveOrUpdateTest() {
        Districts d = createADistricts();

        Districts result = districtsDAO.saveOrUpdate(d);
        assertNotNull("user not loaded", result);
        assertTrue("user name not loaded", result.getName() != "");
        assertEquals("sap code", result.getSap_code());
        d = districtsDAO.getById(d.getIdDistricts());
        assertEquals("sap code", d.getSap_code());
        districtsDAO.delete(d.getIdDistricts());
        d = districtsDAO.getById(d.getIdDistricts());
        assertNull("districts not deleted", d);
    }
    
    @Test
    public void addMeasures() {
        Districts d = createADistricts();

        Districts result = districtsDAO.saveOrUpdate(d);
        result = districtsDAO.getById(d.getIdDistricts());
        
        List<MeasuresHasDistricts> list = districtsDAO.getMeasuresHasDistrictsByDistrictsId(result.getIdDistricts());
        assertEquals(0, list.size());
        /* GC - 22/10/2015 */ 
        /*districtsDAO.addMeasures(result.getIdDistricts(), 523, 0, 35);*/
        districtsDAO.addMeasures(result.getIdDistricts(), 523, 0);
        list = districtsDAO.getMeasuresHasDistrictsByDistrictsId(result.getIdDistricts());
        assertEquals(1, list.size());
        districtsDAO.removeMeasuresHasDistricts(list.get(0).getMeasures_id_measures(), list.get(0).getDistricts_id_districts());
        list = districtsDAO.getMeasuresHasDistrictsByDistrictsId(result.getIdDistricts());
        assertEquals(0, list.size());
        
        districtsDAO.delete(d.getIdDistricts());
    }
    

    private Districts createADistricts() {
        Districts d = new Districts();
        d.setName("d test");
        d.setSap_code("sap code");
        d.setUpdate_timestamp(new Date());
        d.setMin_night_start_time(new Date());
        d.setMin_night_stop_time(new Date());
        
        /* GC - 22/10/2015 */ 
    	/*
        d.setMax_day_start_time_2(new Date());
        d.setMax_day_start_time_3(new Date());
        d.setMax_day_stop_time_1(new Date());
        d.setMax_day_stop_time_2(new Date());
        d.setMax_day_stop_time_3(new Date());
        */
        d.setEv_last_good_sample_day(new Date());
        d.setdClass(23L);
        return d;
    }

}
