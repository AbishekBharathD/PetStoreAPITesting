package stepDefinitions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;
import hooks.Hooks;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;

public class Assertions {
    public static Response response;
    SoftAssert softAssert = new SoftAssert();
    Scenario scenario;

    public static void setResponse(Response r){
        response = r;
    }

    @Then("Response status code should be {int}")
    public void statusCodeValidation(int expected) {
        int actual = response.getStatusCode();

        if (actual != expected) {
            String msg = "Status Code Failed: expected=" + expected + ", actual=" + actual;
            Hooks.scenario.log(msg);
            Hooks.softAssert.fail(msg);
        } else {
            Hooks.scenario.log("Status Code Passed: " + actual);
        }
    }

    @Then("Response status line contains {string}")
    public void statusLineValidation(String expectedText) {
        String actual = response.getStatusLine();

        if (!actual.contains(expectedText)) {
            String msg = "Status Line Failed: expected contains '" + expectedText + "', actual='" + actual + "'";
            Hooks.scenario.log(msg);
            Hooks.softAssert.fail(msg);
        } else {
            Hooks.scenario.log("Status Line Passed: " + actual);
        }
    }

    @Then("Response time less than {int} ms")
    public void responseTimeLessThan(int expectedTime) {
        long actualTime = response.getTime();

        if (actualTime >= expectedTime) {
            String msg = "Response Time Failed: expected < " + expectedTime + ", actual=" + actualTime;
            Hooks.scenario.log(msg);
            Hooks.softAssert.fail(msg);
        } else {
            Hooks.scenario.log("Response Time Passed: " + actualTime + " ms");
        }
    }

    @Then("Validate {string} schema")
    public void validateSchema(String key) {
        try {
            response.then()
                    .assertThat()
                    .body(matchesJsonSchemaInClasspath("schema/" + key + "Schema.json"));

            Hooks.scenario.log("Schema Validation Passed: " + key);

        } catch (AssertionError e) {
            String msg = "Schema Validation Failed: " + e.getMessage();
            Hooks.scenario.log(msg);
            Hooks.softAssert.fail(msg);
        }
    }
}