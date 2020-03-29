package com.zlove.base.util;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;


public class JsonUtil {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        objectMapper.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(
            JsonAutoDetect.Visibility.ANY).withGetterVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(JsonAutoDetect.Visibility.NONE).withCreatorVisibility(
            JsonAutoDetect.Visibility.NONE));
    }

    public static String toString(Object object) {
        try {
            String json = objectMapper.writeValueAsString(object);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static <T> T toObject(String source, Class<T> clazz) {
        try {
            return objectMapper.readValue(source, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T toObjectOrThrows(String source, Class<T> clazz) throws JsonParseException {
        try {
            return objectMapper.readValue(source, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage(), null);
        }
    }

}
