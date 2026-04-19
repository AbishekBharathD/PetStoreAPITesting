package stepDefinitions;

import base.BaseTest;
import endpoints.IPetStoreEndpoint;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class StoreStep extends BaseTest{

	Response response;
	
	@When("user sends GET request to fetch store inventory")
	public void getInventory() {
		response = RestAssured
								.given()
									.spec(requestSpec)
								.when()
									.get(IPetStoreEndpoint.GET_INVENTORY);
		
		response.then().log().all();
		
		Assertions.setResponse(response);
		
	}
	
	@When("user sends POST request to place order")
	public void createOrderWithFullBody() {
		response = RestAssured
								.given()
									.spec(requestSpec)
									.body("{\r\n"
											+ "  \"id\": 10001,\r\n"
											+ "  \"petId\": 101,\r\n"
											+ "  \"quantity\": 2,\r\n"
											+ "  \"shipDate\": \"2026-04-18T08:00:21.525Z\",\r\n"
											+ "  \"status\": \"placed\",\r\n"
											+ "  \"complete\": true\r\n"
											+ "}")
								.when()
									.post(IPetStoreEndpoint.CREATE_ORDER);
		
		response.then().log().all();
		
		Assertions.setResponse(response);
		
	}
	
	@When("user sends POST request to place order with mandatory field")
	public void createOrderWithMandatoryField() {
		response = RestAssured
								.given()
									.spec(requestSpec)
									.body("{\r\n"
											+ "  \"id\": 10002,\r\n"
											+ "  \"petId\": 101,\r\n"
											+ "  \"quantity\": 3\r\n"
											+ "}")
								.when()
									.post(IPetStoreEndpoint.CREATE_ORDER);
		
		response.then().log().all();
		Assertions.setResponse(response);
	}
	
	@When("user sends GET request to get order by existing id {int}")
	public void getOrderByExistingId(int id) {
		response = RestAssured
								.given()
									.spec(requestSpec)
									.pathParam("orderId", id)
								.when()
									.get(IPetStoreEndpoint.GET_ORDER_BY_ID);
		
		response.then().log().all();
		Assertions.setResponse(response);
	}
	
	@When("user sends DELETE request to delete order by existing id {int}")
	public void deleteOrderByExistingId(int id) {
		response = RestAssured
							.given()
								.spec(requestSpec)
								.pathParam("orderId", id)
							.when()
								.delete(IPetStoreEndpoint.DELETE_ORDER_ID);
		Assertions.setResponse(response);
	}
	
	@When("user sends POST request to create a order with invalid id {string}")
	public void createOrderByInvalidIdType(String id) {
		response = RestAssured
								.given()
									.spec(requestSpec)
									.body("{\r\n"
											+ "  \"id\": "+id+",\r\n"
											+ "  \"petId\": 101,\r\n"
											+ "  \"quantity\": 3\r\n"
											+ "}")
								.when()
									.post(IPetStoreEndpoint.CREATE_ORDER);

		response.then().log().all();
		Assertions.setResponse(response);
	}
	
	@When("user sends POST request to create a order with missing mandatory field")
	public void createOrderByMissingMandatoryField() {
		response = RestAssured
							.given()
								.spec(requestSpec)
								.body("{\r\n"
										+ "  \"id\": 1001,\r\n"
										+ "  \"quantity\": 3\r\n"
										+ "}")
							.when()
								.post(IPetStoreEndpoint.CREATE_ORDER);
			
			response.then().log().all();
			Assertions.setResponse(response);
	}
	
	@When("user sends GET request to fetch the order with invalid order id {string}")
	public void getOrderByInvaidOrderId(String id) {
		response = RestAssured
										.given()
											.spec(requestSpec)
											.pathParam("orderId", id)
										.when()
											.get(IPetStoreEndpoint.GET_ORDER_BY_ID);
		
		response.then().log().all();
		Assertions.setResponse(response);
	}
	
	@When("user sends GET request to fetch the order with non existing vaild id {int}")
	public void getOrderByNonExistValidOrderId(int id) {
			response = RestAssured
							.given()
								.spec(requestSpec)
								.pathParam("orderId", id)
							.when()
								.get(IPetStoreEndpoint.GET_ORDER_BY_ID);
			
			response.then().log().all();
			Assertions.setResponse(response);
	}
	
	@When("user sends DELETE request to delete the non existing order id {int}")
	public void deleteOrderByNonExistOrderId(int id) {
		response = RestAssured
							.given()
								.spec(requestSpec)
								.pathParam("orderId", id)
							.when()
								.delete(IPetStoreEndpoint.DELETE_ORDER_ID);
			Assertions.setResponse(response);
	}
	
}
