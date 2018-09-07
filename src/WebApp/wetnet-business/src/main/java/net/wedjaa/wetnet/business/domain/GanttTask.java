/**
 * 
 */
package net.wedjaa.wetnet.business.domain;

import java.util.Date;

/**
 * @author massimo ricci
 *
 */
public class GanttTask {

    private Date startDate;
    private Date endDate;
    private String taskName;
    private String status;
    
    public GanttTask() {
        super();
    }

    public GanttTask(Date startDate, Date endDate, String taskName, String status) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.taskName = taskName;
        this.status = status;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
