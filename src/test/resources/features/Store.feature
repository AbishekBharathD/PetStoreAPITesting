# ---------------------------------------------------------
# Author : Abishek Bharath
# Module : Store
# Description : Store API with request chaining + DDT
# ---------------------------------------------------------


Feature: Manage store operations including inventory and pet orders 

	@positive @store @inventory 
	Scenario: View store inventory
		When user sends GET request to fetch store inventory
		And Validate "Store" schema
		And Response status line contains "200 OK"
		
	@positive @store @order 
	Scenario: Create order with complete data 
		When user sends POST request to create a order
		Then Response status code should be 200
		And Response time less than 5000 ms
		And Validate "Order" schema
		And Response status line contains "200 OK"
		
	@positive @store @order
	Scenario: Fetch order with valid order id 
		When user sends GET request to get order by existing id
		Then Response status code should be 200
		And Response time less than 5000 ms
		And Response status line contains "200 OK"
	
	@positive @store @order
	Scenario: Delete order with valid order id		
		When user sends DELETE request to delete order by existing id
		Then Response status code should be 200
		And Response time less than 5000 ms
		And Response status line contains "200 OK"
	
	@positive @store @order
	Scenario: Create order with mandatory data
		When user sends POST request to place order with mandatory field
		Then Response status code should be 200
		And Response time less than 5000 ms
		And Response status line contains "200 OK"
	
	@negative @store @order	
	Scenario: Create order with invalid data 
		When user sends POST request to create a order with invalid id
		|id|abc|
		|petId|2001|
		|quantity|3|
		|shipDate|2026-04-22T02:30:11.721Z|
		|status|placed|
		|complete|true|
		Then Response status code should be 400
		And Response time less than 5000 ms
		And Validate "Error" schema
		And Response status line contains "400 Bad Request"
		
	@negative @store @order
	Scenario: Create order with missing fields 
		When user sends POST request to create a order with missing mandatory field
		Then Response status code should be 400
		And Response time less than 5000 ms
		And Validate "Error" schema
		And Response status line contains "400 Bad Request"
		
	@negative @store @order
	Scenario: Retrieve order with invalid ID
		When user sends GET request to fetch the order with invalid order id "abc"
		Then Response status code should be 400
		And Response time less than 5000 ms
		And Validate "Error" schema
		And Response status line contains "400 Bad Request"
		
	@negative @store @order
	Scenario Outline: Deleting order with invalid order ID
		When user sends DELETE request to delete the order with invalid order id "<id>"
		Then Response status code should be 400
		And Response time less than 5000 ms
		And Validate "Error" schema
		And Response status line contains "400 Bad Request"
		
		Examples: 
		|id|
		|abc|

		@e2e @store
		Scenario Outline: Creating, Fetching and Deleting the order with valid data 
			When user sends POST request to place order with <rowNum>
			Then Response status code should be 200
			And Response time less than 5000 ms
			And Validate "Order" schema
			And Response status line contains "200 OK"
			
			When user sends GET request to get order by existing id
			Then Response status code should be 200
			And Response time less than 5000 ms
			And Response status line contains "200 OK"
			
			When user sends DELETE request to delete order by existing id
			Then Response status code should be 200
			And Response time less than 5000 ms
			And Response status line contains "200 OK"
			
			When user sends GET request to fetch the order with non existing order id
			Then Response status code should be 404
			And Response time less than 5000 ms
			And Validate "Error" schema
			And Response status line contains "404 Not Found"
			
			Examples: 
			|rowNum|
			|0|
			|1|
