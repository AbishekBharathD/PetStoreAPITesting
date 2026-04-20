# ---------------------------------------------------------
# Author : Abishek Bharath
# Module : Store
# Description : Store API with request chaining + DDT
# ---------------------------------------------------------

Feature: Manage store operations including inventory and pet orders 

	Scenario: View store inventory
		When user sends GET request to fetch store inventory
		And Validate "Store" schema
		And Response status line contains "200 OK"
		
	Scenario Outline: End-to-End Order Flow 
	
		When user sends POST request to place order with <id> <petId> <quantity> "<shipDate>" "<status>" "<complete>"
		Then Response status code should be 200
		And Response time less than 3000 ms
		And Validate "Order" schema
		And Response status line contains "200 OK"
		
		When user sends GET request to get order by existing id
		Then Response status code should be 200
		And Response time less than 3000 ms
		And Response status line contains "200 OK"
		
		When user sends DELETE request to delete order by existing id
		Then Response status code should be 200
		And Response time less than 3000 ms
		And Response status line contains "200 OK"
		
		When user sends GET request to fetch the order with non existing order id
		Then Response status code should be 404
		And Response time less than 3000 ms
		And Validate "Error" schema
		And Response status line contains "404 Not Found"
		
		When user sends DELETE request to delete the non existing order id
		Then Response status code should be 404
		And Response time less than 3000 ms
		And Validate "Error" schema
		And Response status line contains "404 Not Found"
		
		Examples: 
		|id|petId|quantity|shipDate|status|complete|
		|10011|101|2|2026-04-18T08:00:00.000Z|placed|true|
		|10012|102|1|2026-04-18T09:15:00.000Z|approved|true|
		|10013|103|5|2026-04-18T10:30:00.000Z|delivered|false|
		|10014|104|3|2026-04-18T11:45:00.000Z|placed|true|
		|10015|105|4|2026-04-18T12:00:00.000Z|approved|false|
		
	Scenario: Create order with mandatory data
		When user sends POST request to place order with mandatory field
		Then Response status code should be 200
		And Response time less than 3000 ms
		And Response status line contains "200 OK"
		
	Scenario: Create order with invalid data 
		When user sends POST request to create a order with invalid id "abc"
		Then Response status code should be 400
		And Response time less than 3000 ms
		And Validate "Error" schema
		And Response status line contains "400 Bad Request"
		
	Scenario: Create order with missing fields 
		When user sends POST request to create a order with missing mandatory field
		Then Response status code should be 400
		And Response time less than 3000 ms
		And Validate "Error" schema
		And Response status line contains "400 Bad Request"
		
	Scenario: Retrieve order with invalid ID
		When user sends GET request to fetch the order with invalid order id "abc"
		Then Response status code should be 400
		And Response time less than 3000 ms
		And Validate "Error" schema
		And Response status line contains "400 Bad Request"
		
