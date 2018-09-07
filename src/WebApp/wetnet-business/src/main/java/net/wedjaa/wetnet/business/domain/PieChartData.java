/**
 * 
 */
package net.wedjaa.wetnet.business.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author massimo ricci
 *
 */
@XmlRootElement
public class PieChartData {

    @XmlAttribute(required = false)
    private List<Object> columns;
    @XmlAttribute(required = false)
    private List<String> pattern;
    
    public PieChartData() {
        this.columns = new ArrayList<Object>();
        this.pattern = new ArrayList<String>();
    }
    
    public List<Object> getColumns() {
        return columns;
    }

    public void setColumns(List<Object> columns) {
        this.columns = columns;
    }

    public List<String> getPattern() {
        return pattern;
    }

    public void setPattern(List<String> pattern) {
        this.pattern = pattern;
    }
    
}
