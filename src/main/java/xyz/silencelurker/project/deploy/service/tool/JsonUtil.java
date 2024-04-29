package xyz.silencelurker.project.deploy.service.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Silence_Lurker
 */
public class JsonUtil {
    private JsonUtil() {
    }

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toJson(Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T toObject(String json, Class<T> clazz) throws JsonProcessingException {
        return MAPPER.readValue(json, clazz);
    }

    public static Object toObject(String json) throws JsonMappingException, JsonProcessingException {
        return MAPPER.readValue(json, Object.class);
    }

}
