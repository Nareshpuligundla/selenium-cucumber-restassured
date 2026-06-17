# WM Automation Framework

A fully working BDD Cucumber + Selenium + Rest Assured test automation framework for WM.com.

---

## Framework Overview

| Layer | Technology |
|---|---|
| Test language | Java 17 |
| Build tool | Maven 3.9+ |
| BDD layer | Cucumber 7 (primary execution layer) |
| Runner | TestNG via `cucumber-testng` |
| Web automation | Selenium WebDriver 4 + WebDriverManager |
| API automation | Rest Assured 5 |
| Page Object Model | Yes – all UI logic in `pages/` |
| Dependency injection | PicoContainer (shared state across step-def classes) |
| Assertions | AssertJ |
| Reporting | Cucumber HTML/JSON + ExtentReports |
| Logging | Log4j2 |
| Test data | JSON files (`testdata/`) |
| Schema validation | JSON Schema Validator (Rest Assured) |

---

## Folder Structure

```
TestAutomationFramework/
├── pom.xml
├── README.md
├── .gitignore
├── testng.xml
└── src/test/
    ├── java/com/wm/automation/
    │   ├── base/
    │   │   ├── BaseTest.java          – optional base for plain TestNG helpers
    │   │   ├── BasePage.java          – parent of all page objects
    │   │   ├── DriverFactory.java     – ThreadLocal WebDriver lifecycle
    │   │   └── ScenarioContext.java   – per-scenario shared state (PicoContainer)
    │   ├── pages/
    │   │   ├── HomePage.java
    │   │   ├── ServiceRequestPage.java
    │   │   └── SearchPage.java
    │   ├── stepdefinitions/
    │   │   ├── CommonSteps.java
    │   │   ├── WebSteps.java
    │   │   └── ApiSteps.java
    │   ├── runners/
    │   │   └── TestRunner.java
    │   ├── hooks/
    │   │   └── Hooks.java
    │   ├── api/
    │   │   ├── ApiClient.java
    │   │   ├── ApiRequestBuilder.java
    │   │   ├── ApiResponseValidator.java
    │   │   └── WmApiEndpoints.java
    │   ├── utils/
    │   │   ├── ConfigReader.java
    │   │   ├── WaitUtils.java
    │   │   ├── ElementUtils.java
    │   │   ├── JavaScriptUtils.java
    │   │   ├── ScreenshotUtils.java
    │   │   ├── TestDataUtils.java
    │   │   ├── JsonUtils.java
    │   │   ├── AssertionUtils.java
    │   │   ├── RandomDataUtils.java
    │   │   └── DateTimeUtils.java
    │   ├── constants/
    │   │   └── FrameworkConstants.java
    │   └── reporting/
    │       ├── ReportManager.java
    │       └── ReportLogger.java
    └── resources/
        ├── features/
        │   ├── web/
        │   │   ├── wm_home_page.feature
        │   │   └── wm_service_request.feature
        │   ├── api/
        │   │   └── wm_service_availability_api.feature
        │   └── e2e/
        │       └── wm_web_api_e2e.feature
        ├── config/
        │   ├── config.properties      – base config
        │   ├── qa.properties          – QA overrides
        │   └── prod.properties        – prod overrides
        ├── testdata/
        │   ├── web-testdata.json
        │   └── api-testdata.json
        ├── schemas/
        │   └── service-availability-schema.json
        └── log4j2.xml
```

---

## Prerequisites

| Tool | Version |
|---|---|
| Java JDK | 17 or later |
| Maven | 3.9+ |
| Chrome/Firefox/Edge | latest |
| Internet access | Required (WM.com + JSONPlaceholder) |

---

## Setup

```bash
# 1. Clone the repository
git clone <repo-url>
cd TestAutomationFramework

# 2. Confirm Java version
java -version   # must be 17+

# 3. Install dependencies (first run downloads ~200 MB of JARs)
mvn clean install -DskipTests
```

---

## How to Run Tests

### Run all tests
```bash
mvn clean test
```

### Run only API tests (no browser required)
```bash
mvn clean test -Dcucumber.filter.tags="@api"
```

### Run only Web UI tests
```bash
mvn clean test -Dcucumber.filter.tags="@web"
```

### Run only E2E tests
```bash
mvn clean test -Dcucumber.filter.tags="@e2e"
```

### Run smoke tests across all layers
```bash
mvn clean test -Dcucumber.filter.tags="@smoke"
```

### Run regression tests
```bash
mvn clean test -Dcucumber.filter.tags="@regression"
```

### Run combined tags (AND / OR)
```bash
# Smoke API tests only
mvn clean test -Dcucumber.filter.tags="@api and @smoke"

# Web regression OR API smoke
mvn clean test -Dcucumber.filter.tags="@regression or @smoke"
```

### Run headless
```bash
mvn clean test -Dcucumber.filter.tags="@web" -Dheadless=true
```

### Run with a specific browser
```bash
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge
```

### Run against a different environment
```bash
mvn clean test -Denv=prod
```

### Combine overrides
```bash
mvn clean test \
  -Dcucumber.filter.tags="@smoke" \
  -Dbrowser=chrome \
  -Dheadless=true \
  -Denv=qa
```

---

## How Reports Work

After every test run reports are written to `target/`:

| Report | Path |
|---|---|
| Cucumber HTML | `target/cucumber-reports/cucumber.html` |
| Cucumber JSON | `target/cucumber-reports/cucumber.json` |
| Cucumber JUnit XML | `target/cucumber-reports/cucumber.xml` |
| ExtentReports HTML | `target/extent-reports/ExtentReport.html` |
| Log4j2 rolling log | `target/logs/wm-automation.log` |

Open the HTML reports directly in a browser.

---

## How Screenshots Work

- `screenshotOnFailure=true` (set in `config.properties`)
- On every failing **web** scenario the `@After` hook in `Hooks.java` automatically:
  1. Captures a PNG screenshot
  2. Attaches it inline to the Cucumber HTML report
  3. Saves it to `target/screenshots/`
  4. Attaches a base64 version to the ExtentReports HTML

Screenshots are only taken when a WebDriver is active (API-only scenarios are skipped).

---

## Tags Reference

| Tag | Purpose |
|---|---|
| `@web` | Selenium browser scenarios |
| `@api` | REST API-only scenarios (no browser) |
| `@e2e` | End-to-end combining API + UI |
| `@smoke` | Critical happy-path scenarios |
| `@regression` | Full regression coverage |

Hooks automatically detect the tag and only launch a browser for `@web` and `@e2e`.

---

## How to Add a New Feature File

1. Create a `.feature` file under the matching folder:
   - Web: `src/test/resources/features/web/`
   - API: `src/test/resources/features/api/`
   - E2E: `src/test/resources/features/e2e/`

2. Add the appropriate tag(s) at the top of the file:
   ```gherkin
   @web @regression
   Feature: My New Feature
   ```

3. Write scenarios in Gherkin:
   ```gherkin
   Scenario: Something should work
     Given the user navigates to the WM home page
     When something happens
     Then the result should be visible
   ```

4. Run with `dryRun=true` in `TestRunner.java` to see which steps are missing (temporarily set `dryRun = true`, run, then reset to `false`).

---

## How to Add New Step Definitions

1. Add a method to the appropriate class:
   - `WebSteps.java` for browser interactions
   - `ApiSteps.java` for REST calls
   - `CommonSteps.java` for shared navigation steps

2. Use the correct annotation and expression:
   ```java
   @When("the user clicks the {string} button")
   public void theUserClicksButton(String buttonName) {
       // Call page object method – never put locators here
       somePage().clickButton(buttonName);
   }
   ```

3. Step definition classes receive `ScenarioContext` via constructor (PicoContainer DI):
   ```java
   public class MyNewSteps {
       private final ScenarioContext context;
       public MyNewSteps(ScenarioContext context) {
           this.context = context;
       }
   }
   ```

---

## How to Add a New Page Object

1. Create a class in `src/test/java/com/wm/automation/pages/`:
   ```java
   package com.wm.automation.pages;

   import com.wm.automation.base.BasePage;
   import org.openqa.selenium.WebElement;
   import org.openqa.selenium.support.FindBy;

   public class MyNewPage extends BasePage {

       @FindBy(id = "my-element")
       private WebElement myElement;

       @Override
       public boolean isPageLoaded() {
           return isDisplayed(myElement);
       }

       public void clickMyElement() {
           click(myElement);
       }
   }
   ```

2. Instantiate it **inside a step definition method** (never in a constructor):
   ```java
   @When("the user does something")
   public void theUserDoesSomething() {
       new MyNewPage().clickMyElement();
   }
   ```

---

## How to Add New API Tests

1. Add endpoint constants to `WmApiEndpoints.java`.
2. Build the request in `ApiSteps.java` using `ApiRequestBuilder`:
   ```java
   @When("a GET request is made to the service endpoint")
   public void getServiceEndpoint() {
       Response response = ApiRequestBuilder.create()
           .header("Authorization", "Bearer " + token)
           .queryParam("zip", "77002")
           .get(WmApiEndpoints.SERVICE_AVAILABILITY);
       context.set(FrameworkConstants.CONTEXT_API_RESPONSE, response);
   }
   ```
3. Add assertions using `ApiResponseValidator`:
   ```java
   @Then("the service response should be valid")
   public void serviceResponseShouldBeValid() {
       ApiResponseValidator.from(getStoredResponse())
           .statusCodeIs(200)
           .fieldNotNull("serviceAvailable")
           .responseTimeLessThan(3000);
   }
   ```

---

## Configuration Reference

All keys in `config.properties` can be overridden via `-D` on the command line.

| Key | Default | Description |
|---|---|---|
| `browser` | `chrome` | Browser: chrome / firefox / edge |
| `headless` | `false` | Run headless |
| `environment` | `qa` | Active config environment |
| `baseUrl` | `https://www.wm.com` | Web application under test |
| `apiBaseUrl` | `https://jsonplaceholder.typicode.com` | API base URL |
| `explicitWait` | `20` | WebDriverWait timeout (seconds) |
| `pageLoadTimeout` | `30` | Page load timeout (seconds) |
| `screenshotOnFailure` | `true` | Screenshot on scenario failure |
| `retryFailedTests` | `false` | Retry failed tests |

---

## Troubleshooting

### `mvn: command not found`
Install Maven: `brew install maven` (macOS) or download from https://maven.apache.org/.

### `java.lang.UnsupportedClassVersionError`
You are running the tests with a JDK older than 17. Set `JAVA_HOME` to JDK 17+.

### ChromeDriver version mismatch
WebDriverManager auto-downloads the matching ChromeDriver. If it fails behind a proxy, set:
```properties
# in config.properties
wdm.proxyHost=proxy.example.com
wdm.proxyPort=8080
```

### Web tests fail with `NoSuchElementException`
WM.com is frequently updated. Locators in `pages/` may need refreshing. Update the `@FindBy` annotations to match the current DOM — **only the page object class needs changing**, no step-definition changes required.

### WM.com blocks automated browsers
WM.com may detect and block Selenium traffic. This is expected for a production site. The framework compiles and the API layer (JSONPlaceholder) runs cleanly. Use a test/staging environment URL in `config.properties` when one is available.

### Tests hang on page load
Increase `pageLoadTimeout` in `config.properties` or use a VPN/different network. WM.com occasionally CDN-throttles requests.

---

## CI/CD Notes

### GitHub Actions example

```yaml
name: WM Automation

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Run API smoke tests
        run: mvn clean test -Dcucumber.filter.tags="@api and @smoke" -Dheadless=true
      - name: Upload Cucumber reports
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: cucumber-reports
          path: target/cucumber-reports/
```

### Running headless in CI
Always add `-Dheadless=true` in CI pipelines. Chrome/Firefox must be installed on the agent:
```bash
# Ubuntu
sudo apt-get install -y google-chrome-stable
```

### Parallel execution
To run scenarios in parallel, change `testng.xml`:
```xml
<suite name="WM Suite" parallel="methods" thread-count="4">
```
And in `TestRunner.java`, set `@DataProvider(parallel = true)`.
Each thread gets its own `WebDriver` instance via `DriverFactory`'s `ThreadLocal`.
