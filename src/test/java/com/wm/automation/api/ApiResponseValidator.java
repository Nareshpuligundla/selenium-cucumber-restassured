package com.wm.automation.api;

import com.wm.automation.constants.FrameworkConstants;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Fluent assertion helper for Rest Assured {@link Response} objects.
 */
public class ApiResponseValidator {

    private static final Logger logger = LogManager.getLogger(ApiResponseValidator.class);

    private final Response response;

    private ApiResponseValidator(Response response) {
        this.response = response;
    }

    public static ApiResponseValidator from(Response response) {
        return new ApiResponseValidator(response);
    }

    // ─── Status code ─────────────────────────────────────────────────────────

    public ApiResponseValidator statusCodeIs(int expected) {
        int actual = response.getStatusCode();
        logger.info("Asserting status code {} == {}", actual, expected);
        assertThat(actual)
                .as("Expected HTTP status code %d but got %d", expected, actual)
                .isEqualTo(expected);
        return this;
    }

    public ApiResponseValidator statusCodeIsSuccess() {
        int actual = response.getStatusCode();
        assertThat(actual)
                .as("Expected 2xx status code but got %d", actual)
                .isBetween(200, 299);
        return this;
    }

    // ─── Body assertions ─────────────────────────────────────────────────────

    public ApiResponseValidator bodyIsNotEmpty() {
        String body = response.getBody().asString();
        assertThat(body).as("Response body should not be empty").isNotEmpty();
        return this;
    }

    public ApiResponseValidator bodyContains(String text) {
        assertThat(response.getBody().asString())
                .as("Response body should contain: " + text)
                .contains(text);
        return this;
    }

    public ApiResponseValidator fieldEquals(String jsonPath, Object expected) {
        Object actual = response.jsonPath().get(jsonPath);
        logger.info("Asserting jsonPath '{}' = {} (actual={})", jsonPath, expected, actual);
        assertThat(actual)
                .as("jsonPath '%s' expected '%s' but got '%s'", jsonPath, expected, actual)
                .isEqualTo(expected);
        return this;
    }

    public ApiResponseValidator fieldNotNull(String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        assertThat(value)
                .as("Field at jsonPath '%s' should not be null", jsonPath)
                .isNotNull();
        return this;
    }

    public ApiResponseValidator fieldNotEmpty(String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        assertThat(value).as("Field at jsonPath '%s' should not be null", jsonPath).isNotNull();
        if (value instanceof String s) {
            assertThat(s).as("Field at jsonPath '%s' should not be empty", jsonPath).isNotEmpty();
        }
        return this;
    }

    public ApiResponseValidator listSizeGreaterThan(String jsonPath, int minSize) {
        List<?> list = response.jsonPath().getList(jsonPath);
        assertThat(list)
                .as("List at '%s' should have more than %d elements", jsonPath, minSize)
                .hasSizeGreaterThan(minSize);
        return this;
    }

    // ─── Header assertions ────────────────────────────────────────────────────

    public ApiResponseValidator headerExists(String headerName) {
        assertThat(response.getHeader(headerName))
                .as("Header '%s' should be present", headerName)
                .isNotNull();
        return this;
    }

    public ApiResponseValidator headerEquals(String headerName, String expectedValue) {
        assertThat(response.getHeader(headerName))
                .as("Header '%s'", headerName)
                .isEqualTo(expectedValue);
        return this;
    }

    // ─── Schema validation ───────────────────────────────────────────────────

    public ApiResponseValidator matchesSchema(String schemaFileName) {
        File schemaFile = new File(FrameworkConstants.SCHEMAS_DIR + schemaFileName);
        logger.info("Validating response against schema: {}", schemaFile.getAbsolutePath());
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
        return this;
    }

    // ─── Response time ───────────────────────────────────────────────────────

    public ApiResponseValidator responseTimeLessThan(long milliseconds) {
        long actual = response.getTime();
        assertThat(actual)
                .as("Response time %dms should be less than %dms", actual, milliseconds)
                .isLessThan(milliseconds);
        return this;
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    public Response getResponse() {
        return response;
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public String getBody() {
        return response.getBody().asString();
    }

    public <T> T getJsonPath(String path) {
        return response.jsonPath().get(path);
    }

    public Map<String, Object> asMap() {
        return response.jsonPath().getMap("$");
    }
}
