package com.pq.data;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by CM on 12/20/2014.
 */
public class Json {
    public static <T> List<T> readList(String json, String key, Class<T> aClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TypeFactory typeFactory = mapper.getTypeFactory();
        JavaType type = typeFactory.constructMapType(Map.class,
                typeFactory.constructType(String.class),
                typeFactory.constructCollectionType(List.class, aClass));
        Map<String, List<T>> map =
                mapper.readValue(json, type);
        return map.get(key);
    }
}
