package com.nerdysoft.taskmanager.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.nerdysoft.taskmanager.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import java.util.regex.Matcher;

public final class JsonTestUtil {

    private JsonTestUtil() {
    }

    public static <T> String writeValue(T obj) {
        try {
            return objectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    public static String writeUserWithPassword(User user) {
        try {
            String json = objectMapper().writeValueAsString(user);
            json = json.replaceAll("(})$", String.format(",\"password\":\"%s\"}",
                    Matcher.quoteReplacement(user.getPassword())));
            return json;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + user + "'", e);
        }
    }

    private static ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.registerModule(new Hibernate5Module());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return mapper;
    }

}
