package net.wedjaa.wetnet.business.dao;

import java.util.List;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.domain.DistrictsFiles;
import net.wedjaa.wetnet.business.domain.MeasuresFiles;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author roberto cascelli
 *
 */
public class MeasuresFilesDAOImpl implements MeasuresFilesDAO {

    private static final Logger logger = Logger.getLogger(MeasuresFilesDAOImpl.class.getName());

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MeasuresFiles insert(MeasuresFiles obj) {
        try {
            int result = sqlSessionTemplate.insert("measuresFiles.insert", obj);

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
    public List<MeasuresFiles> getAllByMeasure(long idMeasures) {
        try {
            List<MeasuresFiles> mfList = sqlSessionTemplate.selectList("measuresFiles.getAllFilesByMeasure", idMeasures);
            return mfList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MeasuresFiles getById(long id) {
        try {
        	MeasuresFiles df = sqlSessionTemplate.selectOne("measuresFiles.getFileById", id);
            return df;
        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
}
