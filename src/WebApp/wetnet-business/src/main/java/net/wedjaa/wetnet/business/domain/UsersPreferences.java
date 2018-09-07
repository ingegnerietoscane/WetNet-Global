/**
 * 
 */
package net.wedjaa.wetnet.business.domain;

/**
 * @author massimo
 *
 */
public class UsersPreferences extends Users{

    public UsersPreferences(){
        super();
    }
    
    /**
     * Copia tutti i campi dell'oggetto Users passato come parametro tranne la password
     */
    public UsersPreferences(Users users){
        this.idusers = users.idusers;
        this.name = users.name;
        this.surname = users.surname;
        this.email = users.email;
        this.telephone_number = users.telephone_number;
        this.username = users.username;
        this.sms_enabled = users.sms_enabled;
        this.email_enabled = users.email_enabled;
        this.role = users.role;
        this.districtId = users.districtId;
    }
    
}
