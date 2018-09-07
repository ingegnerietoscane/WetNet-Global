package net.wedjaa.wetnet.business.dao;

import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.domain.DistrictsFiles;

public class DistrictsFilesDAOImpl implements DistrictsFilesDAO {

	 private static final Logger logger = Logger.getLogger(DistrictsFilesDAOImpl.class.getName());

	    @Autowired
	    private SqlSessionTemplate sqlSessionTemplate;
	    
	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    public DistrictsFiles insert(DistrictsFiles obj) {
	        try {
	            int result = sqlSessionTemplate.insert("districtsFiles.insert", obj);

	            if (result == 0) {
	                logger.info("no row modified for " + ReflectionToStringBuilder.toString(obj));
	            }
	            return obj;
	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	    }
	    
	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    public List<DistrictsFiles> getAllByDistrict(long idDistricts) {
	        try {
	            List<DistrictsFiles> dfList = sqlSessionTemplate.selectList("districtsFiles.getAllFilesByDistrict", idDistricts);
	            return dfList;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	    }
	    
	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    public DistrictsFiles getById(long id) {
	        try {
	        	DistrictsFiles df = sqlSessionTemplate.selectOne("districtsFiles.getFileById", id);
	            return df;
	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	    }
}
