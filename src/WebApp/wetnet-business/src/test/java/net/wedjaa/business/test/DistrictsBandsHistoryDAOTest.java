package net.wedjaa.business.test;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import net.wedjaa.wetnet.business.dao.DistrictsBandsHistoryDAO;
import net.wedjaa.wetnet.business.domain.DistrictsBandsHistory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author massimo ricci
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-business.xml" })
public class DistrictsBandsHistoryDAOTest {

    @Autowired
    private DistrictsBandsHistoryDAO districtsBandsHistoryDAO;

    @Test
    public void insertDistrictsBandsHistoryTest() {

        //Attenzione, l'ID del distretto deve essere valido per far andare a buon fine l'insert
        DistrictsBandsHistory districtsBandsHistory = new DistrictsBandsHistory(48, new Date(), 12.5, 45.5);
        
        DistrictsBandsHistory result = districtsBandsHistoryDAO.insertDistrictsBandsHistory(districtsBandsHistory);
        assertNotNull("districts bands history not loaded", result);
    }

}
