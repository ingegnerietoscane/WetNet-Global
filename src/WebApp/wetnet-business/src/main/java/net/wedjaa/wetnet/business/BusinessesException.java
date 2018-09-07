package net.wedjaa.wetnet.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessesException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(BusinessesException.class);

    public BusinessesException() {
        super();
        log.error("Eccezione Generica");
    }

    public BusinessesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        log.error(message, cause);
    }

    public BusinessesException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }

    public BusinessesException(String message) {
        super(message);
        log.error(message);
    }

    public BusinessesException(Throwable cause) {
        super(cause);
        log.error("Eccezione Generica", cause);
    }

}
