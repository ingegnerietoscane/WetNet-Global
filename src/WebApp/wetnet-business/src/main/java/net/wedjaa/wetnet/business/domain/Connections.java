package net.wedjaa.wetnet.business.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author alessandro vincelli
 *
 */
@XmlRootElement
public class Connections implements Comparable<Connections> {

    @XmlAttribute(required = false)
    private long id_odbcdsn;
    @XmlAttribute(required = false)
    private String odbc_dsn;
    @XmlAttribute(required = false)
    private String description;
    
    /*
     * GC 22/10/2015
     */
    @XmlAttribute(required = false)
    private String username;
    @XmlAttribute(required = false)
    private String password;
    @XmlAttribute(required = true)
    private int dbType;
    

    public Connections(String odbc_dsn, String description) {
        this.odbc_dsn = odbc_dsn;
        this.description = description;
    }
    
    /*
     * GC 22/10/2015
     */
    public Connections(String odbc_dsn, String description, String username, String password, int db_type) {
        this.odbc_dsn = odbc_dsn;
        this.description = description;
        this.username = username;
        this.password = password;
        this.dbType = db_type;
    }

    public Connections() {
        super();
    }

    public long getId_odbcdsn() {
        return id_odbcdsn;
    }

    public void setId_odbcdsn(long id_odbcdsn) {
        this.id_odbcdsn = id_odbcdsn;
    }

    public String getOdbc_dsn() {
        return odbc_dsn;
    }

    public void setOdbc_dsn(String odbc_dsn) {
        this.odbc_dsn = odbc_dsn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Connections o) {
        if (o != null) {
            return getDescription().compareTo(o.getDescription());
        }
        return 0;
    }
    
    /* GC - 22/10/2015 */
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
	
	public int getDbType() {
		return dbType;
	}

	public void setDbType(int db_type) {
		this.dbType = db_type;
	}
}
