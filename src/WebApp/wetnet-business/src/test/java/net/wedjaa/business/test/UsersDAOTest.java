package net.wedjaa.business.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.wedjaa.wetnet.business.dao.UsersDAO;
import net.wedjaa.wetnet.business.domain.Users;
import net.wedjaa.wetnet.business.domain.UsersHasDistricts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-business.xml" })
public class UsersDAOTest {

    @Autowired
    private UsersDAO usersDAO;

    @Test
    public void getAllTest() {
        List<Users> result = usersDAO.getAll();
        assertTrue("users not loaded", result.size() > 0);
        assertTrue("user name not loaded", result.get(0).getName() != "");
    }


    @Test
    public void saveOrUpdateTest() {
        Users u = new Users();
        u.setName("nome");
        u.setSurname("cognome");
        u.setEmail("emnail");
        u.setEmail_enabled(true);
        u.setSms_enabled(true);
        u.setUsername("username");
        u.setTelephone_number("telephone number");
        u.setPassword("password");
        u.setRole(0);
        Users result = usersDAO.saveOrUpdate(u);
        assertNotNull("user not loaded", result);
        assertTrue("user name not loaded", result.getName() != "");
        assertTrue("user id not loaded", result.getIdusers() != 0);
        u.setEmail("newemail");
        result = usersDAO.saveOrUpdate(u);
        u = usersDAO.getById(u.getIdusers());
        assertEquals("newemail", u.getEmail());
        u = usersDAO.getByUserName(u.getUsername());
        assertEquals("username", u.getUsername());
        usersDAO.delete(u.getIdusers());
        u = usersDAO.getById(u.getIdusers());
        assertNull("user not deleted", u);
    }
    
    @Test
    public void saveOrUpdateOnUsersHasDistrictsTest() {
        Users u = new Users();
        u.setName("nome");
        u.setSurname("cognome");
        u.setEmail("emnail");
        u.setUsername("username");
        u.setPassword("password");
        u.setRole(0);
        usersDAO.saveOrUpdate(u);
        
        usersDAO.addDistrictsToUsers(u.getIdusers(), 48);
        List<UsersHasDistricts> usersHasDistrictsByUsersId = usersDAO.getUsersHasDistrictsByUsersId(u.getIdusers());
        assertEquals("usersHasDistrictsByUsersId not added", 1, usersHasDistrictsByUsersId.size());
        UsersHasDistricts usersHasDistricts = usersHasDistrictsByUsersId.get(0);
        assertEquals("usersHasDistrictsByUsersId isEvents_notification not false", false, usersHasDistricts.isEvents_notification());
        usersHasDistricts.setEvents_notification(true);
        usersDAO.updateUsersHasDistricts(usersHasDistricts);
        usersHasDistricts = usersDAO.getUsersHasDistrictsByUsersId(u.getIdusers()).get(0);
        assertEquals("usersHasDistrictsByUsersId isEvents_notification not true", true, usersHasDistricts.isEvents_notification());
        usersDAO.removeUsersHasDistricts(usersHasDistricts.getUsers_idusers(), usersHasDistricts.getDistricts_id_districts());
        usersHasDistrictsByUsersId = usersDAO.getUsersHasDistrictsByUsersId(u.getIdusers());
        assertEquals("usersHasDistrictsByUsersId not deleted", 0, usersHasDistrictsByUsersId.size());
        usersDAO.delete(u.getIdusers());
        u = usersDAO.getById(u.getIdusers());
        assertNull("user not deleted", u);
    }
}
