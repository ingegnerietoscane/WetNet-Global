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
public class GanttChartData {

    @XmlAttribute(required = false)
    private List<GanttTask> tasks;
    
    @XmlAttribute(required = false)
    private List<String> taskNames;

    public GanttChartData() {
        super();
        this.tasks = new ArrayList<GanttTask>();
        this.taskNames = new ArrayList<String>();;
    }

    public List<GanttTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<GanttTask> tasks) {
        this.tasks = tasks;
    }

    public List<String> getTaskNames() {
        return taskNames;
    }

    public void setTaskNames(List<String> taskNames) {
        this.taskNames = taskNames;
    }

}
