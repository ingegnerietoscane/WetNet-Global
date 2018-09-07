package net.wedjaa.business.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.wedjaa.wetnet.business.dao.ConnectionsDAO;
import net.wedjaa.wetnet.business.domain.Connections;

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
public class ConnectionsDAOTest {

    @Autowired
    private ConnectionsDAO connectionsDAO;

    @Test
    public void getAllTest() {
        List<Connections> result = connectionsDAO.getAll();
        assertTrue("connections not loaded", result.size() > 0);
        assertTrue("odbc_dsn not loaded", result.get(0).getOdbc_dsn() != "");
    }

    @Test
    public void saveOrUpdateTest() {
        Connections u = new Connections();
        u.setDescription("desc");
        u.setOdbc_dsn("dsn");
        Connections result = connectionsDAO.saveOrUpdate(u);
        assertNotNull("Connections not loaded", result);
        assertEquals("desc not saved", "desc", result.getDescription());
        assertEquals("dsn not saved", "dsn", result.getOdbc_dsn());
        u.setDescription("desc2");
        result = connectionsDAO.saveOrUpdate(u);
        u = connectionsDAO.getById(u.getId_odbcdsn());
        assertEquals("desc2", u.getDescription());
        connectionsDAO.delete(u.getId_odbcdsn());
        u = connectionsDAO.getById(u.getId_odbcdsn());
        assertNull("Connections not deleted", u);
    }

}
