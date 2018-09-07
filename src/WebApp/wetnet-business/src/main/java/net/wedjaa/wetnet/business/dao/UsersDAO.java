package net.wedjaa.wetnet.business.dao;

import java.util.List;

import net.wedjaa.wetnet.business.domain.Users;
import net.wedjaa.wetnet.business.domain.UsersHasDistricts;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
public interface UsersDAO {

    /**
     * 
     * @return
     */
    public List<Users> getAll();

    /**
     * 
     * @param userId
     * @return
     */
    public Users getById(long userId);
    
    /**
     * 
     * @param userName
     * @return 
     */
    public Users getByUserName(String userName);

    /**
     * 
     * @param user
     * @return
     */
    public Users saveOrUpdate(Users user);

    /**
     * 
     * @param idusers
     */
    public void delete(long idusers);

    /**
     * crea l'associaziond UsersHasDistricts
     * 
     * @param usersId
     * @param districtsId
     * @return
     */
    public void addDistrictsToUsers(long usersId, long districtsId);

    /**
     * 
     * @param usersId
     * @param districtsId
     * 
     */
    public void removeUsersHasDistricts(long usersId, long districtsId);

    /**
     * aggiorna la proprieta "events_notification"
     * 
     * @param usersHasDistricts
     * @return
     */
    public UsersHasDistricts updateUsersHasDistricts(UsersHasDistricts usersHasDistricts);

    /**
     * UsersHasDistricts di un utente
     * 
     * @param usersId
     * @return lista di UsersHasDistricts
     */
    public List<UsersHasDistricts> getUsersHasDistrictsByUsersId(long usersId);

}
