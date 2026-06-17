package com.wm.automation.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JsonUtils {

    private static final Logger logger = LogManager.getLogger(JsonUtils.class);
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private JsonUtils() {}

    public static <T> T readFromFile(String filePath, Class<T> type) {
        try {
            return MAPPER.readValue(new File(filePath), type);
        } catch (IOException e) {
            logger.error("Failed to read JSON file {}: {}", filePath, e.getMessage());
            throw new RuntimeException("Could not read JSON file: " + filePath, e);
        }
    }

    public static <T> T readFromFile(String filePath, TypeReference<T> typeRef) {
        try {
            return MAPPER.readValue(new File(filePath), typeRef);
        } catch (IOException e) {
            logger.error("Failed to read JSON file {}: {}", filePath, e.getMessage());
            throw new RuntimeException("Could not read JSON file: " + filePath, e);
        }
    }

    public static JsonNode readJsonNode(String filePath) {
        try {
            return MAPPER.readTree(new File(filePath));
        } catch (IOException e) {
            logger.error("Failed to read JSON node from {}: {}", filePath, e.getMessage());
            throw new RuntimeException("Could not read JSON node: " + filePath, e);
        }
    }

    public static Map<String, Object> readAsMap(String filePath) {
        return readFromFile(filePath, new TypeReference<>() {});
    }

    public static String toJsonString(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (IOException e) {
            logger.error("Failed to serialise object to JSON: {}", e.getMessage());
            throw new RuntimeException("Could not serialise to JSON", e);
        }
    }

    public static <T> T fromJsonString(String json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            logger.error("Failed to deserialise JSON string: {}", e.getMessage());
            throw new RuntimeException("Could not deserialise JSON string", e);
        }
    }

    public static String getValueFromFile(String filePath, String fieldName) {
        JsonNode root = readJsonNode(filePath);
        JsonNode node = root.get(fieldName);
        if (node == null) {
            throw new RuntimeException("Field '" + fieldName + "' not found in " + filePath);
        }
        return node.asText();
    }

    public static ObjectMapper getObjectMapper() {
        return MAPPER;
    }
}
