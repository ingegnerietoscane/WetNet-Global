package net.wedjaa.wetnet.business.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author alessandro vincelli
 *
 */
@XmlRootElement
public class Users implements Comparable<Users> {
    
    @XmlAttribute(required = false)
    protected long idusers;
    @XmlAttribute(required = false)
    protected String name;
    @XmlAttribute(required = false)
    protected String surname;
    @XmlAttribute(required = false)
    protected String email;
    @XmlAttribute(required = false)
    protected String telephone_number;
    @XmlAttribute(required = false)
    protected String username;
    @XmlAttribute(required = false)
    private String password;
    @XmlAttribute(required = false)
    protected boolean sms_enabled;
    @XmlAttribute(required = false)
    protected boolean email_enabled;
    @XmlAttribute(required = false)
    protected int role;
    
    /* User preferences */
    protected long districtId = -1;

    public long getIdusers() {
        return idusers;
    }

    public void setIdusers(long idusers) {
        this.idusers = idusers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone_number() {
        return telephone_number;
    }

    public void setTelephone_number(String telephone_number) {
        this.telephone_number = telephone_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSms_enabled() {
        return sms_enabled;
    }

    public void setSms_enabled(boolean sms_enabled) {
        this.sms_enabled = sms_enabled;
    }

    public boolean isEmail_enabled() {
        return email_enabled;
    }

    public void setEmail_enabled(boolean email_enabled) {
        this.email_enabled = email_enabled;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public int compareTo(Users o) {
        if (o != null) {
            return getSurname().compareTo(o.getSurname());
        }
        return 0;
    }

    public long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(long districtId) {
        this.districtId = districtId;
    }
}
