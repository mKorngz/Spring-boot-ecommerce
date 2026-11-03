package org.example.e_commerce_api.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils {
    private static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        return objectMapper;
    }
    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return getObjectMapper().readValue(json, clazz);
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference)
            throws JsonProcessingException {
        return getObjectMapper().readValue(json, typeReference);
    }

    public static <T> String toJson(T data) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(data);
    }
}
