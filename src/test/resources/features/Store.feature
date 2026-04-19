Feature: Manage store operations including inventory and pet orders 

	Scenario: View store inventory
		When user sends GET request to fetch store inventory
		Then Response status code should be 200
		And Response time less than 3000 ms
		And Validate "Store" schema
		And Response status line contains "200 OK"
		
	Scenario: Create order with valid data
		When user sends POST request to place order
		Then Response status code should be 200
		And Response time less than 3000 ms
		And Validate "Order" schema
		And Response status line contains "200 OK"
		
	Scenario: Create order with mandatory data
		When user sends POST request to place order with mandatory field
		Then Response status code should be 200
		And Response time less than 3000 ms
		And Response status line contains "200 OK"

	Scenario: Retrieve existing order 
		When user sends GET request to get order by existing id 10001
		Then Response status code should be 200
		And Response time less than 3000 ms
		And Response status line contains "200 OK"

	Scenario: Delete existing order 
		When user sends DELETE request to delete order by existing id 10001
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
		
	Scenario: Retrieve non-existing order 
		When user sends GET request to fetch the order with non existing vaild id 99999
		Then Response status code should be 404
		And Response time less than 3000 ms
		And Validate "Error" schema
		And Response status line contains "404 Not Found"
		
	Scenario: Delete order with invalid ID 
		When user sends DELETE request to delete the non existing order id 9999999
		Then Response status code should be 404
		And Response time less than 3000 ms
		And Validate "Error" schema
		And Response status line contains "404 Not Found"
				