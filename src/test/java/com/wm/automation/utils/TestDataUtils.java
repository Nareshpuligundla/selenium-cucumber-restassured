package com.wm.automation.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.wm.automation.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestDataUtils {

    private static final Logger logger = LogManager.getLogger(TestDataUtils.class);

    private static volatile JsonNode webData;
    private static volatile JsonNode apiData;

    private TestDataUtils() {}

    // ─── Web Test Data ────────────────────────────────────────────────────────

    private static JsonNode webData() {
        if (webData == null) {
            synchronized (TestDataUtils.class) {
                if (webData == null) {
                    webData = JsonUtils.readJsonNode(FrameworkConstants.WEB_TEST_DATA_PATH);
                    logger.info("Loaded web test data from {}", FrameworkConstants.WEB_TEST_DATA_PATH);
                }
            }
        }
        return webData;
    }

    public static String getValidAddress() {
        return webData().path("validAddress").asText();
    }

    public static String getInvalidAddress() {
        return webData().path("invalidAddress").asText();
    }

    public static String getSearchKeyword() {
        return webData().path("searchKeyword").asText();
    }

    public static String getServiceType() {
        return webData().path("serviceType").asText();
    }

    public static String getCustomerName() {
        return webData().path("customerName").asText();
    }

    public static String getPhoneNumber() {
        return webData().path("phoneNumber").asText();
    }

    public static String getEmail() {
        return webData().path("email").asText();
    }

    // ─── API Test Data ────────────────────────────────────────────────────────

    private static JsonNode apiData() {
        if (apiData == null) {
            synchronized (TestDataUtils.class) {
                if (apiData == null) {
                    apiData = JsonUtils.readJsonNode(FrameworkConstants.API_TEST_DATA_PATH);
                    logger.info("Loaded API test data from {}", FrameworkConstants.API_TEST_DATA_PATH);
                }
            }
        }
        return apiData;
    }

    public static String getValidZipCode() {
        return apiData().path("validZipCode").asText();
    }

    public static String getInvalidZipCode() {
        return apiData().path("invalidZipCode").asText();
    }

    public static String getValidServicePayload() {
        return JsonUtils.toJsonString(apiData().path("validServicePayload"));
    }

    public static String getInvalidServicePayload() {
        return JsonUtils.toJsonString(apiData().path("invalidServicePayload"));
    }

    public static JsonNode getValidServicePayloadNode() {
        return apiData().path("validServicePayload");
    }

    public static JsonNode getApiDataNode(String key) {
        return apiData().path(key);
    }

    public static JsonNode getWebDataNode(String key) {
        return webData().path(key);
    }
}
