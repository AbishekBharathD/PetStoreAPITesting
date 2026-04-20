package stepDefinitions;

import java.util.ArrayList;
import java.util.List;

import base.BaseTest;
import endpoints.IPetStoreEndpoint;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.Order;
import utils.ExcelUtility;

public class StoreStep extends BaseTest{

	Response response;
	static List<Integer> orderIds = new ArrayList<>();
	
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
		
		Object[][] data = ExcelUtility.getSheetData("testData.xlsx", "StoreModule");
		for(int rowNum=0; rowNum<data.length; rowNum++) {
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
				
				response = RestAssured
									.given()
										.spec(requestSpec)
										.body(order)
									.when()
										.post(IPetStoreEndpoint.CREATE_ORDER);
				
				response.then().log().all();
				
				Assertions.setResponse(response);
				int orderId = response.jsonPath().getInt("id");
				orderIds.add(orderId);
				
		}
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
		
		for(Integer orderId : orderIds) {
				response = RestAssured
										.given()
											.spec(requestSpec)
											.pathParam("orderId", orderId)
										.when()
											.get(IPetStoreEndpoint.GET_ORDER_BY_ID);
				
				response.then().log().all();
				Assertions.setResponse(response);
		}
	}
	
	@When("user sends DELETE request to delete order by existing id")
	public void deleteOrderByExistingId() {
		for(Integer orderId : orderIds) {
			response = RestAssured
								.given()
									.spec(requestSpec)
									.pathParam("orderId", orderId)
								.when()
									.delete(IPetStoreEndpoint.DELETE_ORDER_ID);
			Assertions.setResponse(response);
		}
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
		for(Integer orderId : orderIds) {
			response = RestAssured
							.given()
								.spec(requestSpec)
								.pathParam("orderId", orderId)
							.when()
								.get(IPetStoreEndpoint.GET_ORDER_BY_ID);
			
			response.then().log().all();
			Assertions.setResponse(response);
		}
	}
	
	@When("user sends DELETE request to delete the order with invalid order id {string}")
	public void deleteOrderByNonExistOrderId(String id) {
			response = RestAssured
								.given()
									.spec(requestSpec)
									.pathParam("orderId", id)
								.when()
									.delete(IPetStoreEndpoint.DELETE_ORDER_ID);
			Assertions.setResponse(response);
	}
	
}
