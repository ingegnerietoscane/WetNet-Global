package net.wedjaa.wetnet.business.dao;

import java.util.List;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.domain.Users;
import net.wedjaa.wetnet.business.domain.UsersHasDistricts;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
public class UsersDAOImpl implements UsersDAO {

    private static final Logger logger = Logger.getLogger(UsersDAOImpl.class.getName());

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Users> getAll() {
        try {
            List<Users> result = sqlSessionTemplate.selectList("users.getAll");
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Users getById(long userId) {
        try {
            Users result = sqlSessionTemplate.selectOne("users.getBydId", userId);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Users saveOrUpdate(Users user) {
        try {
            int result = 0;
            if (user.getIdusers() > 0) {
                result = sqlSessionTemplate.update("users.update", user);
            } else {
                result = sqlSessionTemplate.insert("users.insert", user);
            }
            if (result == 0) {
                logger.info("no row modified for " + ReflectionToStringBuilder.toString(user));
            }
            return user;
        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long idusers) {
        try {
            int result = sqlSessionTemplate.delete("users.delete", idusers);
            if (result == 0) {
                logger.info("no row deleted for userid: " + idusers);
            }

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDistrictsToUsers(long usersId, long districtsId) {
        UsersHasDistricts usersHasDistricts = new UsersHasDistricts();
        usersHasDistricts.setDistricts_id_districts(districtsId);
        usersHasDistricts.setUsers_idusers(usersId);
        sqlSessionTemplate.insert("users.insertUsersHasDistricts", usersHasDistricts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUsersHasDistricts(long usersId, long districtsId) {
        UsersHasDistricts usersHasDistricts = new UsersHasDistricts();
        usersHasDistricts.setDistricts_id_districts(districtsId);
        usersHasDistricts.setUsers_idusers(usersId);
        sqlSessionTemplate.delete("users.deleteUsersHasDistricts", usersHasDistricts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsersHasDistricts updateUsersHasDistricts(UsersHasDistricts usersHasDistricts) {
        sqlSessionTemplate.update("users.updateUsersHasDistricts", usersHasDistricts);
        return usersHasDistricts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UsersHasDistricts> getUsersHasDistrictsByUsersId(long usersId) {
        return sqlSessionTemplate.selectList("users.getUsersHasDistrictBydIdusers", usersId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Users getByUserName(String userName) {
        Users result = sqlSessionTemplate.selectOne("users.getByUserName", userName);
        return result;
    }

}
