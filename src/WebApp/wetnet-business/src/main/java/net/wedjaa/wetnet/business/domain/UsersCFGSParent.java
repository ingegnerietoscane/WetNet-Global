package net.wedjaa.wetnet.business.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author roberto cascelli
 *
 */
@XmlRootElement
public class UsersCFGSParent implements Comparable<UsersCFGSParent> {
    
    @XmlAttribute(required = true)
    protected Date save_date;
    
    @XmlAttribute(required = true)
    protected int menu_function;
    
    @XmlAttribute(required = true)
    protected int submenu_function;
    
    @XmlAttribute(required = false)
    protected String description;
    
    @XmlAttribute(required = true)
    protected int time_base;
    
    @XmlAttribute(required = true)
    protected int granularity;
    
    @XmlAttribute(required = true)
    protected long users_idusers;

	public Date getSave_date() {
		return save_date;
	}

	public void setSave_date(Date save_date) {
		this.save_date = save_date;
	}

	public long getMenu_function() {
		return menu_function;
	}

	public void setMenu_function(int menu_function) {
		this.menu_function = menu_function;
	}

	public int getSubmenu_function() {
		return submenu_function;
	}

	public void setSubmenu_function(int submenu_function) {
		this.submenu_function = submenu_function;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTime_base() {
		return time_base;
	}

	public void setTime_base(int time_base) {
		this.time_base = time_base;
	}

	public int getGranularity() {
		return granularity;
	}

	public void setGranularity(int granularity) {
		this.granularity = granularity;
	}

	public long getUsers_idusers() {
		return users_idusers;
	}

	public void setUsers_idusers(long users_idusers) {
		this.users_idusers = users_idusers;
	}

	@Override
	public int compareTo(UsersCFGSParent o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
