package stepDefinitions;

import endpoints.IPetStoreEndpoint;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import base.BaseTest;

public class UserStep extends BaseTest{
    Response response;

    @When("I Send GET user request with valid username {string}")
    public void fetchUserByUsername(String userName) {
        response = RestAssured
                .given()
                    .spec(requestSpec)
                    .pathParam("username", userName)
                .when()
                    .get(IPetStoreEndpoint.GET_USER);
        Assertions.setResponse(response);
    }


    
}