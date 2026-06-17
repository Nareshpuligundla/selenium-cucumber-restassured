package com.wm.automation.api;

import com.wm.automation.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Low-level REST client wrapping Rest Assured.
 * Use {@link ApiRequestBuilder} for fluent request construction.
 */
public class ApiClient {

    private static final Logger logger = LogManager.getLogger(ApiClient.class);

    private final RequestSpecification baseSpec;

    public ApiClient() {
        String baseUrl = ConfigReader.getInstance().getApiBaseUrl();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        baseSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

        logger.info("ApiClient initialised with base URL: {}", baseUrl);
    }

    public ApiClient(String baseUrl) {
        baseSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
        logger.info("ApiClient initialised with base URL: {}", baseUrl);
    }

    // ─── GET ─────────────────────────────────────────────────────────────────

    public Response get(String path) {
        logger.info("GET {}", path);
        return RestAssured.given(baseSpec).get(path);
    }

    public Response get(String path, Map<String, ?> queryParams) {
        logger.info("GET {} params={}", path, queryParams);
        return RestAssured.given(baseSpec).queryParams(queryParams).get(path);
    }

    public Response getWithPathParams(String path, Map<String, ?> pathParams) {
        logger.info("GET {} pathParams={}", path, pathParams);
        return RestAssured.given(baseSpec).pathParams(pathParams).get(path);
    }

    // ─── POST ────────────────────────────────────────────────────────────────

    public Response post(String path, Object body) {
        logger.info("POST {}", path);
        return RestAssured.given(baseSpec).body(body).post(path);
    }

    public Response post(String path, Object body, Map<String, String> headers) {
        logger.info("POST {} headers={}", path, headers);
        return RestAssured.given(baseSpec).headers(headers).body(body).post(path);
    }

    // ─── PUT ─────────────────────────────────────────────────────────────────

    public Response put(String path, Object body) {
        logger.info("PUT {}", path);
        return RestAssured.given(baseSpec).body(body).put(path);
    }

    // ─── PATCH ───────────────────────────────────────────────────────────────

    public Response patch(String path, Object body) {
        logger.info("PATCH {}", path);
        return RestAssured.given(baseSpec).body(body).patch(path);
    }

    // ─── DELETE ──────────────────────────────────────────────────────────────

    public Response delete(String path) {
        logger.info("DELETE {}", path);
        return RestAssured.given(baseSpec).delete(path);
    }

    // ─── Custom spec ─────────────────────────────────────────────────────────

    public RequestSpecification given() {
        return RestAssured.given(baseSpec);
    }
}
