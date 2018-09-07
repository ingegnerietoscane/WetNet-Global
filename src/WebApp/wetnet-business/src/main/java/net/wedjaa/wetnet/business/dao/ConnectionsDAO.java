package net.wedjaa.wetnet.business.dao;

import java.util.List;

import net.wedjaa.wetnet.business.domain.Connections;

/**
 * @author alessandro vincelli
 *
 */
public interface ConnectionsDAO {

    /**
     * 
     * @return
     */
    public List<Connections> getAll();

    /**
     * 
     * @param id
     * @return
     */
    public Connections getById(long id);

    /**
     * 
     * @param connection
     * @return
     */
    public Connections saveOrUpdate(Connections connection);

    /**
     * 
     * @param id
     */
    public void delete(long id);



}
