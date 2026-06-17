package com.wm.automation.api;

/**
 * Central registry of API endpoint paths.
 * All paths are relative; the base URL is resolved from config.properties.
 *
 * NOTE: WM.com production APIs are not publicly available for test automation.
 * The framework uses JSONPlaceholder (https://jsonplaceholder.typicode.com) as
 * a functional stand-in.  Replace these paths with real WM endpoints when
 * authenticated access is available.
 */
public final class WmApiEndpoints {

    private WmApiEndpoints() {}

    // ─── JSONPlaceholder endpoints (publicly available) ────────────────────
    public static final String POSTS = "/posts";
    public static final String POST_BY_ID = "/posts/{id}";
    public static final String USERS = "/users";
    public static final String USER_BY_ID = "/users/{id}";
    public static final String COMMENTS = "/comments";
    public static final String TODOS = "/todos";

    // ─── WM.com service API placeholders ──────────────────────────────────
    // These paths represent the intended WM endpoint structure.
    // They will return 404 against the JSONPlaceholder base URL –
    // swap the apiBaseUrl in config.properties when real credentials are available.
    public static final String SERVICE_AVAILABILITY = "/v1/service/availability";
    public static final String SERVICE_REQUEST = "/v1/service/request";
    public static final String ADDRESS_VALIDATE = "/v1/address/validate";
    public static final String HEALTH_CHECK = "/health";
    public static final String INVALID_ENDPOINT = "/this-does-not-exist-404";
}
