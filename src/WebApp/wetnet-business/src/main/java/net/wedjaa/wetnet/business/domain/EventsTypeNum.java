/**
 * 
 */
package net.wedjaa.wetnet.business.domain;

/**
 * @author massimo ricci
 *
 */
public class EventsTypeNum {

    private Long num;
    private Long type;
    private String item;
    private Long id;
    
    public EventsTypeNum() {
        this.num = (long) 0;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
