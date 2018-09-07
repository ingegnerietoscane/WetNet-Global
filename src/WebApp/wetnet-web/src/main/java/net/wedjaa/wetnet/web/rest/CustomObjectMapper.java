package net.wedjaa.wetnet.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        super();
        this.registerModule(new JodaModule());
        this.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

}
