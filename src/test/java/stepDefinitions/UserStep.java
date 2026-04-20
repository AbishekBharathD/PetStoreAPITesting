package stepDefinitions;

import endpoints.IPetStoreEndpoint;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Map;
import base.BaseTest;
import utils.DataUtility;

public class UserStep extends BaseTest{
    Response response;

    @When("I Send GET user request with username {string}")
    public void fetchUserByUsername(String userName) {
        response = RestAssured
                .given()
                    .spec(requestSpec)
                    .pathParam("username", userName)
                .when()
                    .get(IPetStoreEndpoint.GET_USER);
        Assertions.setResponse(response);
    }
    
    @When("I Send POST request to create a user with the following details:")
    public void createUser(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().get(0);
        String body = DataUtility.buildUserJson(row);
 
        response = RestAssured
                .given()
                    .spec(requestSpec)
                    .body(body)
                .when()
                    .post(IPetStoreEndpoint.CREATE_USER);
        Assertions.setResponse(response);
    }
 
    @When("I Send POST request to create a user with empty body")
    public void createUserWithEmptyBody() {
        response = RestAssured
                .given()
                    .spec(requestSpec)
                    .body("{}")
                .when()
                    .post(IPetStoreEndpoint.CREATE_USER);
        Assertions.setResponse(response);
    }

    @When("I Send POST request to create users with array")
    public void createUsersWithArray() {
        String body = "[" +
                DataUtility.buildUserJson(1002, "ArrayUser1", "Alice", "Balaji", "alice@example.com", "Pass@001", "1111111111", 1) +
                "," +
                DataUtility.buildUserJson(1003, "ArrayUser2", "Bob", "Balaji", "bob@example.com", "Pass@002", "2222222222", 1) +
                "]";

        response = RestAssured
                .given()
                    .spec(requestSpec)
                    .body(body)
                .when()
                    .post(IPetStoreEndpoint.CREATE_USER_WITH_ARRAY);
        Assertions.setResponse(response);
    }

    @When("I Send POST request to create users with empty array")
    public void createUsersWithEmptyArray() {
        response = RestAssured
                .given()
                    .spec(requestSpec)
                    .body("[]")
                .when()
                    .post(IPetStoreEndpoint.CREATE_USER_WITH_ARRAY);
        Assertions.setResponse(response);
    }

    @When("I Send POST request to create users with list")
    public void createUsersWithList() {
        String body = "[" +
                DataUtility.buildUserJson(1004, "ListUser1", "Charlie", "Brown", "charlie@example.com", "Pass@003", "3333333333", 1) +
                "," +
                DataUtility.buildUserJson(1005, "ListUser2", "Diana", "Brown", "diana@example.com", "Pass@004", "4444444444", 1) +
                "]";

        response = RestAssured
                .given()
                    .spec(requestSpec)
                    .body(body)
                .when()
                    .post(IPetStoreEndpoint.CREATE_USER_WITH_LIST);
        Assertions.setResponse(response);
    }

    @When("I Send POST request to create users with empty list")
    public void createUsersWithEmptyList() {
        response = RestAssured
                .given()
                    .spec(requestSpec)
                    .body("[]")
                .when()
                    .post(IPetStoreEndpoint.CREATE_USER_WITH_LIST);
        Assertions.setResponse(response);
    }

    @When("I Send GET login request with username {string} and password {string}")
    public void loginUser(String username, String password) {
        response = RestAssured
                .given()
                    .spec(requestSpec)
                    .queryParam("username", username)
                    .queryParam("password", password)
                .when()
                    .get(IPetStoreEndpoint.USER_LOGIN);
        Assertions.setResponse(response);
    }
}