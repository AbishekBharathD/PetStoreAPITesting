package stepDefinitions;

import java.util.Map;

import base.BaseTest;
import endpoints.IPetStoreEndpoint;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.Order;
import utils.DataUtility;
import utils.ExcelUtility;
import utils.ScenarioContext;

public class StoreStep extends BaseTest {

	Response response;

	@When("user sends GET request to fetch store inventory")
	public void getInventory() {
		response = RestAssured.given().spec(requestSpec).when().get(IPetStoreEndpoint.GET_INVENTORY);

		response.then().log().all();

		Assertions.setResponse(response);

	}
	
	@When("user sends POST request to create a order")
	public void createOrderWithFullBody() {
		Order order = DataUtility.getOrderRequestBody();
		response = RestAssured
								.given()
									.spec(requestSpec)
									.body(order)
								.when()
									.post(IPetStoreEndpoint.CREATE_ORDER);
		response.then().log().all();
		Assertions.setResponse(response);
		ScenarioContext.set("orderId", response.jsonPath().getInt("id"));
	}

	@When("user sends POST request to place order with {int}")
	public void createOrder(int rowNum) {

		Object[][] data = ExcelUtility.getSheetData("testData.xlsx", "StoreModule");
		String id = data[rowNum][0].toString();
		String petId = data[rowNum][1].toString();
		String quantity = data[rowNum][2].toString();
		String shipDate = data[rowNum][3].toString();
		String status = data[rowNum][4].toString();
		String complete = data[rowNum][5].toString();

		Order order = new Order();
		order.setId((int) Double.parseDouble(id));
		order.setPetId((int) Double.parseDouble(petId));
		order.setQuantity((int) Double.parseDouble(quantity));
		order.setShipDate(shipDate);
		order.setStatus(status);
		order.setComplete(Boolean.parseBoolean(complete));

		response = RestAssured.given().spec(requestSpec).body(order).when().post(IPetStoreEndpoint.CREATE_ORDER);

		response.then().log().all();

		Assertions.setResponse(response);
		ScenarioContext.set("orderId", response.jsonPath().getInt("id"));

	}

	@When("user sends POST request to place order with mandatory field")
	public void createOrderWithMandatoryField() {
		response = RestAssured.given().spec(requestSpec)
				.body("{\r\n" + "  \"id\": 10002,\r\n" + "  \"petId\": 101,\r\n" + "  \"quantity\": 3\r\n" + "}").when()
				.post(IPetStoreEndpoint.CREATE_ORDER);

		response.then().log().all();
		Assertions.setResponse(response);
	}

	@When("user sends GET request to get order by existing id")
	public void getOrderByExistingId() {

		response = RestAssured.given().spec(requestSpec).pathParam("orderId", ScenarioContext.get("orderId")).when()
				.get(IPetStoreEndpoint.GET_ORDER_BY_ID);

		response.then().log().all();
		Assertions.setResponse(response);
	}
	
	@When("user sends GET request to fetch the order with non existing order id {int}")
	public void getOrderByNonExistingId(int id) {

		response = RestAssured.given().spec(requestSpec).pathParam("orderId", id).when()
				.get(IPetStoreEndpoint.GET_ORDER_BY_ID);

		response.then().log().all();
		Assertions.setResponse(response);
	}

	@When("user sends DELETE request to delete order by existing id")
	public void deleteOrderByExistingId() {
		response = RestAssured.given().spec(requestSpec).pathParam("orderId", ScenarioContext.get("orderId")).when()
				.delete(IPetStoreEndpoint.DELETE_ORDER_ID);
		Assertions.setResponse(response);
	}

	@When("user sends POST request to create a order with invalid id")
	public void createOrderByInvalidIdType(DataTable dataTable) {

		Map<String, String> data = dataTable.asMap(String.class, String.class);

		/*
		 * Map<String, Object> request = new HashMap<>();
		 * request.put("id",data.get("id")); request.put("petId",
		 * Integer.parseInt(data.get("petId"))); request.put("quantity",
		 * Integer.parseInt(data.get("quantity"))); request.put("status",
		 * data.get("status")); request.put("complete",
		 * Boolean.parseBoolean(data.get("complete")));
		 */

		/*
		 * String request = "{\n" + "  \"id\": \"" + data.get("id") + "\",\n" +
		 * "  \"petId\": " + Integer.parseInt(data.get("petId")) + ",\n" +
		 * "  \"quantity\": " + Integer.parseInt(data.get("quantity")) + ",\n" +
		 * "  \"shipDate\": \"" + data.get("shipDate") + "\",\n" + "  \"status\": \"" +
		 * data.get("status") + "\",\n" + "  \"complete\": " +
		 * Boolean.parseBoolean(data.get("complete")) + "\n" + "}";
		 */
		
		String request = "{\r\n"
				+ "  \"id\": "+data.get("id")+",\r\n"
				+ "  \"petId\": "+data.get("petId")+",\r\n"
				+ "  \"quantity\": "+data.get("quantity")+",\r\n"
				+ "  \"shipDate\": \""+data.get("shipDate")+"\",\r\n"
				+ "  \"status\": \""+data.get("status")+"\",\r\n"
				+ "  \"complete\": "+data.get("complete")+"\r\n"
				+ "}";

		response = RestAssured
								.given()
									.spec(requestSpec)
										.body(request)
									.when()
										.post(IPetStoreEndpoint.CREATE_ORDER);

		response.then().log().all();
		Assertions.setResponse(response);
	}

	@When("user sends POST request to create a order with missing mandatory field")
	public void createOrderByMissingMandatoryField() {
		response = RestAssured.given().spec(requestSpec)
				.body("{\r\n" + "  \"id\": 1001,\r\n" + "  \"quantity\": 3\r\n" + "}").when()
				.post(IPetStoreEndpoint.CREATE_ORDER);

		response.then().log().all();
		Assertions.setResponse(response);
	}

	@When("user sends GET request to fetch the order with invalid order id {string}")
	public void getOrderByInvaidOrderId(String orderId) {
		response = RestAssured.given().spec(requestSpec).pathParam("orderId", orderId).when()
				.get(IPetStoreEndpoint.GET_ORDER_BY_ID);

		response.then().log().all();
		Assertions.setResponse(response);
	}

	@When("user sends GET request to fetch the order with non existing order id")
	public void getOrderByNonExistValidOrderId() {
		response = RestAssured.given().spec(requestSpec).pathParam("orderId", ScenarioContext.get("orderId")).when()
				.get(IPetStoreEndpoint.GET_ORDER_BY_ID);

		response.then().log().all();
		Assertions.setResponse(response);
	}

	@When("user sends DELETE request to delete the order with invalid order id {string}")
	public void deleteOrderByNonExistOrderId(String id) {
		response = RestAssured.given().spec(requestSpec).pathParam("orderId", id).when()
				.delete(IPetStoreEndpoint.DELETE_ORDER_ID);
		Assertions.setResponse(response);
	}

}
