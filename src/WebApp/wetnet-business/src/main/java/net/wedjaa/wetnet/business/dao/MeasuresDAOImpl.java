package net.wedjaa.wetnet.business.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.domain.MeasuresMeterReading;
import net.wedjaa.wetnet.business.domain.MeasuresWithSign;
import net.wedjaa.wetnet.business.domain.Users;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author alessandro vincelli, massimo ricci, graziella cipolletti
 *
 */
public class MeasuresDAOImpl implements MeasuresDAO {

    private static final Logger logger = Logger.getLogger(MeasuresDAOImpl.class.getName());

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Measures> getAll() {
        try {
            List<Measures> list = sqlSessionTemplate.selectList("measures.getAllMeasures");
            return list;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Measures> getAll(Users user) {
        try {
            List<Measures> list = sqlSessionTemplate.selectList("measures.getAllMeasures", user);
            return list;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Measures> getByDistrictId(long districtId) {
        try {
            List<Measures> list = sqlSessionTemplate.selectList("measures.getMeasuresByDistrictId", districtId);
            return list;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
    
    
    /*
     * GC - 29/10/2015
     */
    /**
     * {@inheritDoc}
     */
    @Override
    public List<MeasuresWithSign> getWithSignByDistrictId(long districtId) {
        try {
            List<MeasuresWithSign> list = sqlSessionTemplate.selectList("measures.getMeasuresWithSignByDistrictId", districtId);
            return list;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Measures saveOrUpdate(Measures measures) {
        if (measures.getIdMeasures() == 0) {
            sqlSessionTemplate.insert("measures.insertMeasures", measures);
        } else {
            sqlSessionTemplate.update("measures.updateMeasures", measures);
        }
        return measures;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long id) {
        sqlSessionTemplate.delete("measures.deleteMeasures", id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Measures getById(long id) {
        return sqlSessionTemplate.selectOne("measures.getById", id);
    }

	@Override
	public List<MeasuresMeterReading> getAllMeterReadingByMeasure(long id) {
		List<MeasuresMeterReading> list = new ArrayList<MeasuresMeterReading>();
		 try {
	            list = sqlSessionTemplate.selectList("measuresMeterReading.getAllByMeasure", id);
	            
	        } catch (Exception e) {
	           e.printStackTrace();
	        }
		 return list;
	}

	@Override
	public MeasuresMeterReading getLastMeterReadingByMeasure(long id) {
		MeasuresMeterReading reading = null;
		
		try {
			reading = sqlSessionTemplate.selectOne("measuresMeterReading.getLastByIdMeasure", id);
            
        } catch (Exception e) {
           e.printStackTrace();
        }
		
		return reading;
	}

	@Override
	public MeasuresMeterReading saveMeterReading(MeasuresMeterReading measuresMeterReading) {
		        sqlSessionTemplate.insert("measuresMeterReading.insert", measuresMeterReading);
	        return measuresMeterReading;
	}

}
