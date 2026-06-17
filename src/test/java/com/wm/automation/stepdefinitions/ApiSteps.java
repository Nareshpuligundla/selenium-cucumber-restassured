package com.wm.automation.stepdefinitions;

import com.wm.automation.api.ApiClient;
import com.wm.automation.api.ApiRequestBuilder;
import com.wm.automation.api.ApiResponseValidator;
import com.wm.automation.api.WmApiEndpoints;
import com.wm.automation.base.ScenarioContext;
import com.wm.automation.constants.FrameworkConstants;
import com.wm.automation.reporting.ReportLogger;
import com.wm.automation.utils.AssertionUtils;
import com.wm.automation.utils.TestDataUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiSteps {

    private static final Logger logger = LogManager.getLogger(ApiSteps.class);

    private final ScenarioContext context;
    private ApiClient apiClient;

    public ApiSteps(ScenarioContext context) {
        this.context = context;
    }

    private ApiClient apiClient() {
        if (apiClient == null) {
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    private Response getStoredResponse() {
        return context.get(FrameworkConstants.CONTEXT_API_RESPONSE);
    }

    // ─── Step definitions ─────────────────────────────────────────────────────

    @When("a GET request is made to the posts endpoint")
    public void aGetRequestIsMadeToThePostsEndpoint() {
        Response response = apiClient().get(WmApiEndpoints.POSTS);
        context.set(FrameworkConstants.CONTEXT_API_RESPONSE, response);
        context.set(FrameworkConstants.CONTEXT_API_STATUS_CODE, response.getStatusCode());
        ReportLogger.info("GET " + WmApiEndpoints.POSTS + " → " + response.getStatusCode());
        logger.info("GET {} → {}", WmApiEndpoints.POSTS, response.getStatusCode());
    }

    @When("a GET request is made to an invalid endpoint")
    public void aGetRequestIsMadeToAnInvalidEndpoint() {
        Response response = apiClient().get(WmApiEndpoints.INVALID_ENDPOINT);
        context.set(FrameworkConstants.CONTEXT_API_RESPONSE, response);
        context.set(FrameworkConstants.CONTEXT_API_STATUS_CODE, response.getStatusCode());
        ReportLogger.info("GET " + WmApiEndpoints.INVALID_ENDPOINT + " → " + response.getStatusCode());
    }

    @When("a GET request is made to the posts endpoint with id {int}")
    public void aGetRequestIsMadeToThePostsEndpointWithId(int id) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("id", id);
        Response response = ApiRequestBuilder.create()
                .pathParam("id", id)
                .get(WmApiEndpoints.POST_BY_ID);
        context.set(FrameworkConstants.CONTEXT_API_RESPONSE, response);
        context.set(FrameworkConstants.CONTEXT_API_STATUS_CODE, response.getStatusCode());
        ReportLogger.info("GET /posts/" + id + " → " + response.getStatusCode());
        logger.info("GET /posts/{} → {}", id, response.getStatusCode());
    }

    @When("a POST request is made to the posts endpoint with valid payload")
    public void aPostRequestIsMadeToThePostsEndpointWithValidPayload() {
        String payload = TestDataUtils.getValidServicePayload();
        Response response = ApiRequestBuilder.create()
                .body(payload)
                .post(WmApiEndpoints.POSTS);
        context.set(FrameworkConstants.CONTEXT_API_RESPONSE, response);
        context.set(FrameworkConstants.CONTEXT_API_STATUS_CODE, response.getStatusCode());
        ReportLogger.info("POST " + WmApiEndpoints.POSTS + " → " + response.getStatusCode());
        logger.info("POST {} → {}", WmApiEndpoints.POSTS, response.getStatusCode());
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedCode) {
        Response response = getStoredResponse();
        ApiResponseValidator.from(response).statusCodeIs(expectedCode);
        ReportLogger.pass("Response status code is " + expectedCode);
    }

    @And("the response should contain a list of posts")
    public void theResponseShouldContainAListOfPosts() {
        Response response = getStoredResponse();
        List<?> posts = response.jsonPath().getList("$");
        AssertionUtils.assertListNotEmpty(posts, "Response should contain a list of posts");
        AssertionUtils.assertGreaterThan(posts.size(), 0, "Post list should have at least one item");
        ReportLogger.pass("Response contains " + posts.size() + " posts");
    }

    @And("the response should match the expected schema")
    public void theResponseShouldMatchTheExpectedSchema() {
        Response response = getStoredResponse();
        ApiResponseValidator.from(response).matchesSchema("service-availability-schema.json");
        ReportLogger.pass("Response matches expected JSON schema");
    }

    @And("the response should contain the created post data")
    public void theResponseShouldContainTheCreatedPostData() {
        Response response = getStoredResponse();
        ApiResponseValidator validator = ApiResponseValidator.from(response);
        validator.fieldNotNull("id");
        validator.bodyIsNotEmpty();
        ReportLogger.pass("POST response contains created resource data");
    }

    @And("the API response title field should not be empty")
    public void theApiResponseTitleFieldShouldNotBeEmpty() {
        Response response = getStoredResponse();
        ApiResponseValidator.from(response).fieldNotEmpty("title");
        ReportLogger.pass("API response 'title' field is not empty");
    }
}
