package stepDefinitions;

import base.BaseTest;
import endpoints.IPetStoreEndpoint;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.Order;

public class StoreStep extends BaseTest{

	Response response;
	int orderId;
	
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
	
	@When("user sends POST request to place order with {int} {int} {int} {string} {string} {string}")
	public void createOrderWithFullBody(int id, int petId, int quantity, String shipDate, String status, String complete) {
		
		Order order = new Order();
		order.setId(id);
		order.setPetId(petId);
		order.setQuantity(quantity);
		order.setShipDate(shipDate);
		order.setStatus(status);
		order.setComplete(Boolean.parseBoolean(complete));
		
		response = RestAssured
								.given()
									.spec(requestSpec)
									.body(order)
								.when()
									.post(IPetStoreEndpoint.CREATE_ORDER);
		
		response.then().log().all();
		
		Assertions.setResponse(response);
		orderId = response.jsonPath().getInt("id");
		
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
	
	@When("user sends GET request to get order by existing id")
	public void getOrderByExistingId() {
		response = RestAssured
								.given()
									.spec(requestSpec)
									.pathParam("orderId", orderId)
								.when()
									.get(IPetStoreEndpoint.GET_ORDER_BY_ID);
		
		response.then().log().all();
		Assertions.setResponse(response);
	}
	
	@When("user sends DELETE request to delete order by existing id")
	public void deleteOrderByExistingId() {
		response = RestAssured
							.given()
								.spec(requestSpec)
								.pathParam("orderId", orderId)
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
	public void getOrderByInvaidOrderId(String orderId) {
		response = RestAssured
										.given()
											.spec(requestSpec)
											.pathParam("orderId", orderId)
										.when()
											.get(IPetStoreEndpoint.GET_ORDER_BY_ID);
		
		response.then().log().all();
		Assertions.setResponse(response);
	}
	
	@When("user sends GET request to fetch the order with non existing order id")
	public void getOrderByNonExistValidOrderId() {
			response = RestAssured
							.given()
								.spec(requestSpec)
								.pathParam("orderId", orderId)
							.when()
								.get(IPetStoreEndpoint.GET_ORDER_BY_ID);
			
			response.then().log().all();
			Assertions.setResponse(response);
	}
	
	@When("user sends DELETE request to delete the non existing order id")
	public void deleteOrderByNonExistOrderId() {
		response = RestAssured
							.given()
								.spec(requestSpec)
								.pathParam("orderId", orderId)
							.when()
								.delete(IPetStoreEndpoint.DELETE_ORDER_ID);
			Assertions.setResponse(response);
	}
	
}
