package net.wedjaa.wetnet.business.domain;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JSONResponse {

    /**
     * <b>success</b> All went well, and (usually) some data was returned
     */
    public static final String SUCCESS = "success";
    /**
     * <b>fail</b> There was a problem with the data submitted, or some pre-condition of the API call wasn't satisfied
     */
    public static final String FAIL = "fail";
    /**
     * <b>error</b> An error occurred in processing the request, i.e. an exception was thrown, the <b>code</b> is also filled
     */
    public static final String ERROR = "error";

    /**
     * could be success, fail, error
     */
    @XmlAttribute
    private String status;

    /**
     * in case of error is mandatory
     */
    @XmlAttribute
    private String code;

    @XmlAttribute
    private String message;
    /**
     * JSON object data
     */
    @XmlAttribute
    private Map<String, Object> data;
    

    public JSONResponse() {
        super();
    }

    public JSONResponse(String status) {
        super();
        this.status = status;
    }

    public JSONResponse(String status, String code, String message) {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public JSONResponse(String status, String code, String message, Map<String, Object> data) {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public JSONResponse(String status, String code, String message, Object data) {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = new HashMap<String, Object>();
        this.data.put("data",data);
    }

    /**
     * could be success, fail, error
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * JSON object data
     * 
     * @return
     */
    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void addData(String key, Object value) {
        if(this.data == null){
            this.data = new HashMap<String, Object>(); 
        }
        this.data.put(key, value);
    }
  
}
