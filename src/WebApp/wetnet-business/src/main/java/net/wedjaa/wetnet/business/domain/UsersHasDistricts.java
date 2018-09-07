package net.wedjaa.wetnet.business.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author alessandro vincelli
 *
 */
@XmlRootElement
public class UsersHasDistricts implements Comparable<UsersHasDistricts> {
    @XmlAttribute(required = false)
    private long users_idusers;
    @XmlAttribute(required = false)
    private long districts_id_districts;
    @XmlAttribute(required = false)
    private String districts_name;
    @XmlAttribute(required = false)
    private boolean events_notification;

    public long getUsers_idusers() {
        return users_idusers;
    }

    public void setUsers_idusers(long users_idusers) {
        this.users_idusers = users_idusers;
    }

    public long getDistricts_id_districts() {
        return districts_id_districts;
    }

    public void setDistricts_id_districts(long districts_id_districts) {
        this.districts_id_districts = districts_id_districts;
    }

    public boolean isEvents_notification() {
        return events_notification;
    }

    public void setEvents_notification(boolean events_notification) {
        this.events_notification = events_notification;
    }

    public String getDistricts_name() {
        return districts_name;
    }

    public void setDistricts_name(String districts_name) {
        this.districts_name = districts_name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(UsersHasDistricts o) {
        if (o != null) {
            return Long.compare(getUsers_idusers(), o.getUsers_idusers());
        }
        return 0;
    }
}
