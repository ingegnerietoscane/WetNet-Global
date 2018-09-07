/**
 * 
 */
package net.wedjaa.wetnet.web.spring.model;

/**
 * @author massimo ricci
 *
 */
public class ResultMessage {

    public final static String SUCCESS = "success";
    public final static String DANGER = "danger";
    public final static String INFO = "info";
    public final static String WARNING = "warning";
    private String type;
    private String message;
    
    public ResultMessage() {
        super();
    }
    
    public ResultMessage(String type, String message) {
        super();
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
