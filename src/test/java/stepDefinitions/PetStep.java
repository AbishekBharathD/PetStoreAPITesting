package stepDefinitions;

import base.BaseTest;
import endpoints.IPetStoreEndpoint;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.io.File;
import java.util.*;

import pojo.Pet;

import static io.restassured.RestAssured.given;

public class PetStep extends BaseTest {

    private Response response;
    // private int currentPetId; // used for request chaining
    static private Pet pet;
    static List<Integer> petIds = new ArrayList<>();

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

    @When("user sends POST request to add pet with {int} {string} {string}")
    public void add_pet(int id, String name, String status) {
        
        pet = new Pet(id, name, status);

        response = given().spec(requestSpec).body(pet).post(IPetStoreEndpoint.ADD_PET);
        Assertions.setResponse(response);
        petIds.add(response.jsonPath().getInt("id"));
    }

    @When("user sends GET request to fetch pet by existing id")
    public void get_pet_by_existing_id() {
        for(Integer id : petIds){
            response = given().spec(requestSpec)
                .pathParam("petId", id)
                .get(IPetStoreEndpoint.GET_PET_BY_ID);
            Assertions.setResponse(response);
        }
        
    }

    @When("user sends PUT request to update pet")
    public void update_pet() {
        for(Integer id : petIds){
            pet.setId(id);
            pet.setStatus("sold");
            response = given().spec(requestSpec).body(pet).put(IPetStoreEndpoint.UPDATE_PET);
        Assertions.setResponse(response);
        }
        
    }

    @When("user sends DELETE request to delete pet by existing id")
    public void delete_pet_by_existing_id() {
        for(Integer id : petIds)
        response = given().spec(requestSpec)
                .pathParam("petId", id)
                .delete(IPetStoreEndpoint.DELETE_PET);
        Assertions.setResponse(response);
    }

    /* =========================
       WHEN - Negative Cases
       ========================= */

    @When("user sends GET request to fetch pet with non existing id")
    public void get_pet_non_existing() {
        for(Integer id : petIds){
            response = given().spec(requestSpec)
                .pathParam("petId", id)
                .get(IPetStoreEndpoint.GET_PET_BY_ID);
            Assertions.setResponse(response);
        }
        
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
}
