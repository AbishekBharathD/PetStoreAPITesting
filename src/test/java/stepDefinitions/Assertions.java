package stepDefinitions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;

import io.cucumber.java.en.Then;

public class Assertions {
    public static Response response;

    public static void setResponse(Response r){
        response = r;
    }


    @Then("Response status code should be {int}")
    public void statusCodeValidation(int statusCode) {
        response.then()
            .assertThat()
                .statusCode(statusCode)
            .log().all();
    }

    @Then("Validate {string} schema")
    public void validateSchema(String key){
        response.then()
            .assertThat()
                .body(matchesJsonSchemaInClasspath("schema/" + key + "Schema.json"));
    }

    @Then("Response status line contains {string}")
    public void statusLineValidation(String str) {
        response.then()
            .assertThat()
                .statusLine(containsString(str));
    }

    @Then("Response time less than {string} ms")
    public void responseTimeLessThan(String str) {
        response.then()
            .assertThat()
                .time(lessThan(Long.valueOf(str)));
    }
}
