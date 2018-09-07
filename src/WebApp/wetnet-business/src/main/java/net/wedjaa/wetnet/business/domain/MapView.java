package net.wedjaa.wetnet.business.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author alessandro vincelli
 *
 */
@XmlRootElement
public class MapView implements Comparable<MapView> {

    @XmlAttribute(required = false)
    private long id_map_view;
    @XmlAttribute(required = false)
    private String kml;

    public long getId_map_view() {
        return id_map_view;
    }

    public void setId_map_view(long id_map_view) {
        this.id_map_view = id_map_view;
    }

    public String getKml() {
        return kml;
    }

    public void setKml(String kml) {
        this.kml = kml;
    }

    @Override
    public int compareTo(MapView o) {
        if (o != null) {
            return Long.compare(getId_map_view(), o.getId_map_view());
        }
        return 0;
    }
}
