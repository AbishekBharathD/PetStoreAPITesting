package stepDefinitions;

import base.BaseTest;
import endpoints.IPetStoreEndpoint;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PetStep extends BaseTest {

    private Response response;

    private final String filePath = System.getProperty("user.dir")
            + "/src/test/resources/testData/testData.xlsx";

    private final String sheetName = "PetModule";

    /* =========================
       GIVEN
       ========================= */

    @Given("Petstore API is available")
    public void petstore_api_is_available() {
        // Base setup already handled in BaseTest
    }

    @Given("Pet with id {int} exists")
public void pet_with_id_exists(int petId) {

    String body = "{ \"id\": " + petId + ", \"name\": \"dog\", \"status\": \"available\" }";

    response = given()
            .spec(requestSpec)
            .body(body)
            .post(IPetStoreEndpoint.ADD_PET);

    Assertions.setResponse(response);
}

    /* =========================
       WHEN
       ========================= */

    @When("I send POST request using test data {string}")
    public void send_post_request_excel(String testCaseName) {

        String body = buildRequestBody(testCaseName);
        System.out.println("POST Body: " + body);

        response = given()
                .spec(requestSpec)
                .body(body)
                .post(IPetStoreEndpoint.ADD_PET);

        Assertions.setResponse(response); 
    }

    @When("I send PUT request using test data {string}")
    public void send_put_request_excel(String testCaseName) {

        String body = buildRequestBody(testCaseName);
        System.out.println("PUT Body: " + body);

        response = given()
                .spec(requestSpec)
                .body(body)
                .put(IPetStoreEndpoint.UPDATE_PET);

        Assertions.setResponse(response);   
    }

    @When("I send GET request for pet id {int}")
    public void send_get_request(int petId) {

        response = given()
                .spec(requestSpec)
                .pathParam("petId", petId)
                .get(IPetStoreEndpoint.GET_PET_BY_ID);

        Assertions.setResponse(response);   
    }

@When("I send POST request with invalid JSON structure")
public void send_invalid_json_request() {

    String invalidJson = "{\n" +
            "\"id\": 3003,\n" +
            "\"name\": [\"dog\"],\n" +
            "\"status\": {\"value\":\"available\"}\n" +
            "}";

    response = given()
            .spec(requestSpec)
            .body(invalidJson)
            .post(IPetStoreEndpoint.ADD_PET);

    Assertions.setResponse(response);
}

@When("I send GET request for pets by status {string}")
public void get_pets_by_status(String status) {

    response = given()
            .spec(requestSpec)
            .queryParam("status", status)
            .get(IPetStoreEndpoint.FIND_BY_STATUS);

    Assertions.setResponse(response);
}

@When("I upload image for pet id {int}")
public void upload_image(int petId) {

    File file = new File(System.getProperty("user.dir") + "/src/test/resources/Dog.jpg");

    response = given()
            .spec(requestSpec)
            .contentType(ContentType.MULTIPART)
            .pathParam("petId", petId)
            .multiPart("file", file)
            .post(IPetStoreEndpoint.UPLOAD_IMAGE);

    Assertions.setResponse(response);
}


@When("I send GET request for invalid pet id {string}")
public void get_pet_string_id(String petId) {
    response = given()
            .spec(requestSpec)
            .pathParam("petId", petId)
            .get(IPetStoreEndpoint.GET_PET_BY_ID);

    Assertions.setResponse(response);
}

@When("I send DELETE request for pet id {int}")
public void delete_pet(int petId) {
    response = given()
            .spec(requestSpec)
            .pathParam("petId", petId)
            .delete(IPetStoreEndpoint.DELETE_PET);

    Assertions.setResponse(response);
}


/* =========================
       EXCEL HANDLING
       ========================= */

    private Map<String, String> getTestData(String testCaseName) {

        Map<String, String> data = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                String currentTC = getCellValue(row.getCell(0));

                if (testCaseName.equalsIgnoreCase(currentTC)) {

                    for (int i = 1; i < headerRow.getLastCellNum(); i++) {
                        data.put(
                                headerRow.getCell(i).getStringCellValue(),
                                getCellValue(row.getCell(i))
                        );
                    }
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    private String getCellValue(Cell cell) {

        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    /* =========================
       JSON BUILDER
       ========================= */

    private String buildRequestBody(String testCaseName) {

        Map<String, String> data = getTestData(testCaseName);

        if (data.isEmpty()) {
            throw new RuntimeException("No data found for: " + testCaseName);
        }

        StringBuilder json = new StringBuilder("{");

        if (!data.getOrDefault("id", "").isEmpty()) {
            json.append("\"id\": ").append(data.get("id")).append(",");
        }

        if (!data.getOrDefault("name", "").isEmpty()) {
            json.append("\"name\": \"").append(data.get("name")).append("\",");
        }

        if (!data.getOrDefault("status", "").isEmpty()) {
            json.append("\"status\": \"").append(data.get("status")).append("\",");
        }

        // Remove trailing comma
        if (json.charAt(json.length() - 1) == ',') {
            json.deleteCharAt(json.length() - 1);
        }

        json.append("}");

        return json.toString();
    }
}