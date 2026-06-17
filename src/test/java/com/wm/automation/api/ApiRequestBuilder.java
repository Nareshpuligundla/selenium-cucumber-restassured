package com.wm.automation.api;

import com.wm.automation.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Fluent builder for constructing and executing API requests.
 *
 * <pre>
 *   Response response = ApiRequestBuilder.create()
 *       .header("Authorization", "Bearer " + token)
 *       .queryParam("zip", "30301")
 *       .body(payload)
 *       .get("/posts");
 * </pre>
 */
public class ApiRequestBuilder {

    private static final Logger logger = LogManager.getLogger(ApiRequestBuilder.class);

    private final String baseUri;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, Object> queryParams = new HashMap<>();
    private final Map<String, Object> pathParams = new HashMap<>();
    private Object body;
    private ContentType contentType = ContentType.JSON;

    private ApiRequestBuilder() {
        this.baseUri = ConfigReader.getInstance().getApiBaseUrl();
    }

    private ApiRequestBuilder(String baseUri) {
        this.baseUri = baseUri;
    }

    public static ApiRequestBuilder create() {
        return new ApiRequestBuilder();
    }

    public static ApiRequestBuilder create(String baseUri) {
        return new ApiRequestBuilder(baseUri);
    }

    public ApiRequestBuilder header(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public ApiRequestBuilder headers(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public ApiRequestBuilder queryParam(String name, Object value) {
        queryParams.put(name, value);
        return this;
    }

    public ApiRequestBuilder queryParams(Map<String, Object> params) {
        queryParams.putAll(params);
        return this;
    }

    public ApiRequestBuilder pathParam(String name, Object value) {
        pathParams.put(name, value);
        return this;
    }

    public ApiRequestBuilder body(Object requestBody) {
        this.body = requestBody;
        return this;
    }

    public ApiRequestBuilder contentType(ContentType type) {
        this.contentType = type;
        return this;
    }

    private RequestSpecification buildSpec() {
        RequestSpecification spec = RestAssured.given()
                .baseUri(baseUri)
                .contentType(contentType)
                .accept(ContentType.JSON);

        if (!headers.isEmpty()) spec.headers(headers);
        if (!queryParams.isEmpty()) spec.queryParams(queryParams);
        if (!pathParams.isEmpty()) spec.pathParams(pathParams);
        if (body != null) spec.body(body);

        return spec;
    }

    public Response get(String path) {
        logger.info("GET {}{}", baseUri, path);
        return buildSpec().get(path);
    }

    public Response post(String path) {
        logger.info("POST {}{}", baseUri, path);
        return buildSpec().post(path);
    }

    public Response put(String path) {
        logger.info("PUT {}{}", baseUri, path);
        return buildSpec().put(path);
    }

    public Response patch(String path) {
        logger.info("PATCH {}{}", baseUri, path);
        return buildSpec().patch(path);
    }

    public Response delete(String path) {
        logger.info("DELETE {}{}", baseUri, path);
        return buildSpec().delete(path);
    }
}
