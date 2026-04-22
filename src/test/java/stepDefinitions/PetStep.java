package stepDefinitions;

import base.BaseTest;
import endpoints.IPetStoreEndpoint;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.io.File;
import java.util.*;
import static org.hamcrest.Matchers.*;


import pojo.Pet;
import utils.ExcelUtility;

import static io.restassured.RestAssured.given;

public class PetStep extends BaseTest {

    private Response response;
    // private int currentPetId; // used for request chaining
    // static private Pet pet;
    Integer petId;

    /* =========================
       WHEN - CRUD Flow
       ========================= */

    @When("user sends GET request for pets by status {string}")
    public void get_pets_by_status(String status) {
        response = given().spec(requestSpec)
                .queryParam("status", status)
                .get(IPetStoreEndpoint.FIND_BY_STATUS);
        Assertions.setResponse(response);
    }

    @When("user sends POST request using excel row {int}")
    public void add_pet(int rowNum) {
        
       Object[][] excelData = ExcelUtility.getSheetData("testData.xlsx", "PetModule");

       
            String id = excelData[rowNum][0].toString();
            String name = excelData[rowNum][1].toString();
            String status = excelData[rowNum][2].toString();

            Pet pet = new Pet(((int) Double.parseDouble(id)), name, status);

            response = given().spec(requestSpec).body(pet).post(IPetStoreEndpoint.ADD_PET);
            Assertions.setResponse(response);
           petId = response.jsonPath().getInt("id");
            response.then().log().all();
        
    }

    @When("user sends GET request to fetch pet by existing id")
    public void get_pet_by_existing_id() {
        
            response = given().spec(requestSpec)
                .pathParam("petId", petId)
                .get(IPetStoreEndpoint.GET_PET_BY_ID);
            Assertions.setResponse(response);
            response.then().log().all();
        
        
    }

    @When("user sends PUT request to update pet")
    public void update_pet() {
        
            Pet pet = new Pet();
            pet.setId(petId);
            pet.setName("UpdatedPet");
            pet.setStatus("sold");
            response = given().spec(requestSpec).body(pet).put(IPetStoreEndpoint.UPDATE_PET);
        Assertions.setResponse(response);
        response.then().log().all();
        
        
    }
    

    @When("user sends DELETE request to delete pet by existing id")
    public void delete_pet_by_existing_id() {
        
        response = given().spec(requestSpec)
                .pathParam("petId", petId)
                .delete(IPetStoreEndpoint.DELETE_PET);
        Assertions.setResponse(response);
        response.then().log().all();
    }

    /* =========================
       WHEN - Negative Cases
       ========================= */

    @When("user sends GET request to fetch pet with non existing id")
    public void get_pet_non_existing() {
        
            response = given().spec(requestSpec)
                .pathParam("petId", petId)
                .get(IPetStoreEndpoint.GET_PET_BY_ID);
            Assertions.setResponse(response);

            response.then().log().all();
        
        
    }

    @When("user sends DELETE request to delete non existing pet id")
    public void delete_pet_non_existing() {
        response = given().spec(requestSpec)
                .pathParam("petId", 9999)
                .delete(IPetStoreEndpoint.DELETE_PET);
        Assertions.setResponse(response);
    }

    @When("user sends GET request for invalid pet id {string}")
    public void get_pet_invalid_id(String petId) {
        response = given().spec(requestSpec)
                .pathParam("petId", petId)
                .get(IPetStoreEndpoint.GET_PET_BY_ID);
        Assertions.setResponse(response);
    }

    @When("user uploads image for pet id {int}")
    public void upload_image(int petId) {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/Dog.jpg");
        response = given().spec(requestSpec)
                .contentType(ContentType.MULTIPART)
                .pathParam("petId", petId)
                .multiPart("file", file)
                .post(IPetStoreEndpoint.UPLOAD_IMAGE);
        Assertions.setResponse(response);
    }

    @When("user sends POST request with pet data")
public void add_pet(DataTable dataTable) {

    List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row : data) {

        int id = Integer.parseInt(row.get("id"));
        String name = row.get("name");
        String status = row.get("status");

        Pet pet = new Pet(id, name, status);

        response = given()
                .spec(requestSpec)
                .log().all()
                .body(pet)
                .post(IPetStoreEndpoint.ADD_PET);

        Assertions.setResponse(response);

        response.then().log().all();
    }
}

    /* =========================
       WHEN - Negative Data Cases
       ========================= */

    @When("user sends POST request using test data {string}")
    public void post_with_test_data(String testCaseName) {
        if (testCaseName.equals("TC_PET_07")) {
            // Missing mandatory field (no id)
            String body = "{ \"name\": \"dog\", \"status\": \"available\" }";
            response = given().spec(requestSpec).body(body).post(IPetStoreEndpoint.ADD_PET);
        } else if (testCaseName.equals("TC_PET_09")) {
            // Invalid data type for status
            String body = "{ \"id\": 1234, \"name\": \"cat\", \"status\": 123 }";
            response = given().spec(requestSpec).body(body).post(IPetStoreEndpoint.ADD_PET);
        } else {
            throw new RuntimeException("Unsupported test case: " + testCaseName);
        }
        Assertions.setResponse(response);
    }
    @Then("Response body should reflect updated values")
public void validate_updated_pet() {

    response.then()
            .assertThat()

            .body("status", equalTo("sold"))

            .body("id", instanceOf(Integer.class))
            .body("name", instanceOf(String.class))
            .body("status", instanceOf(String.class));
}
}


