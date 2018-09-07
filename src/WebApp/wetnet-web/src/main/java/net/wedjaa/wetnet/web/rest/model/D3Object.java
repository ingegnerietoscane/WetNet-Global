package net.wedjaa.wetnet.web.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class D3Object {

    @XmlAttribute
    private List<String> series;

    @XmlAttribute
    private List<D3Data> data;

    public List<String> getSeries() {
        return series;
    }

    public void setSeries(List<String> series) {
        this.series = series;
    }

    public List<D3Data> getData() {
        return data;
    }

    public void setData(List<D3Data> data) {
        this.data = data;
    }

}
