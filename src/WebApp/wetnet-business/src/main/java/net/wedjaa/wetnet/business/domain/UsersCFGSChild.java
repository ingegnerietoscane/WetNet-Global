package net.wedjaa.wetnet.business.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author roberto cascelli
 *
 */
@XmlRootElement
public class UsersCFGSChild implements Comparable<UsersCFGSChild> {
    
    @XmlAttribute(required = true)
    protected int progressive;
    
    @XmlAttribute(required = true)
    protected int type;
    
    @XmlAttribute(required = true)
    protected long objectid;
    
    @XmlAttribute(required = true)
    protected Date users_cfgs_parent_save_date;
    
    @XmlAttribute(required = true)
    protected int users_cfgs_parent_menu_function;
    
    @XmlAttribute(required = true)
    protected int users_cfgs_parent_submenu_function;
    
    @XmlAttribute(required = true)
    protected int users_idusers;

	public int getProgressive() {
		return progressive;
	}

	public void setProgressive(int progressive) {
		this.progressive = progressive;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getObjectid() {
		return objectid;
	}

	public void setObjectid(long objectid) {
		this.objectid = objectid;
	}

	public Date getUsers_cfgs_parent_save_date() {
		return users_cfgs_parent_save_date;
	}

	public void setUsers_cfgs_parent_save_date(Date users_cfgs_parent_save_date) {
		this.users_cfgs_parent_save_date = users_cfgs_parent_save_date;
	}

	public int getUsers_cfgs_parent_menu_function() {
		return users_cfgs_parent_menu_function;
	}

	public void setUsers_cfgs_parent_menu_function(int users_cfgs_parent_menu_function) {
		this.users_cfgs_parent_menu_function = users_cfgs_parent_menu_function;
	}

	public int getUsers_cfgs_parent_submenu_function() {
		return users_cfgs_parent_submenu_function;
	}

	public void setUsers_cfgs_parent_submenu_function(int users_cfgs_parent_submenu_function) {
		this.users_cfgs_parent_submenu_function = users_cfgs_parent_submenu_function;
	}
	
	public int getUsers_idusers() {
		return users_idusers;
	}

	public void setUsers_idusers(int users_idusers) {
		this.users_idusers = users_idusers;
	}

	@Override
	public int compareTo(UsersCFGSChild o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
