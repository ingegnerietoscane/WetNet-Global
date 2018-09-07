package net.wedjaa.wetnet.business.dao;

import java.util.List;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.domain.Connections;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author alessandro vincelli
 *
 */
public class ConnectionsDAOImpl implements ConnectionsDAO {

    private static final Logger logger = Logger.getLogger(ConnectionsDAOImpl.class.getName());

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Connections> getAll() {
        try {
            List<Connections> result = sqlSessionTemplate.selectList("connections.getAll");
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connections getById(long userId) {
        try {
            Connections result = sqlSessionTemplate.selectOne("connections.getBydId", userId);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connections saveOrUpdate(Connections connection) {
        try {
            int result = 0;
            if (connection.getId_odbcdsn() > 0) {
                result = sqlSessionTemplate.update("connections.update", connection);
            } else {
                result = sqlSessionTemplate.insert("connections.insert", connection);
            }
            if (result == 0) {
                logger.info("no row modified for " + ReflectionToStringBuilder.toString(connection));
            }
            return connection;
        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long id) {
        try {
            int result = sqlSessionTemplate.delete("connections.delete", id);
            if (result == 0) {
                logger.info("no row deleted for userid: " + id);
            }

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }


}
