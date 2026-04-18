Feature: Petstore API Testing (Data Driven)

Scenario: TC_PET_01 Verify adding new pet with valid data
Given Petstore API is available
When I send POST request using test data "TC_PET_01"
Then Response status code should be 200
And Response time less than "3000" ms
And Response status line contains "OK"
And Validate "Pet" schema

Scenario: TC_PET_02 Verify created pet details in response
Given Petstore API is available
When I send POST request using test data "TC_PET_02"
Then Response status code should be 200
And Response time less than "3000" ms
And Response status line contains "OK"

Scenario: TC_PET_03 Verify updating pet with valid data
Given Pet with id 2001 exists
When I send PUT request using test data "TC_PET_03"
Then Response status code should be 200
And Response time less than "3000" ms
And Response status line contains "OK"

Scenario: TC_PET_04 Verify updated pet details
Given Pet with id 2001 exists
When I send GET request for pet id 2001
Then Response status code should be 200
And Response time less than "3000" ms
And Response status line contains "OK"

Scenario: TC_PET_05 Verify fetching pet by valid ID
Given Pet with id 2001 exists
When I send GET request for pet id 2001
Then Response status code should be 200
And Response time less than "3000" ms
And Response status line contains "OK"

Scenario: TC_PET_06 Verify fetched pet details match stored data
Given Pet with id 2001 exists
When I send GET request for pet id 2001
Then Response status code should be 200
And Response status line contains "OK"

# Negative scenarios (kept simple because API allows invalid data)

Scenario: TC_PET_07 Missing name
Given Petstore API is available
When I send POST request using test data "TC_PET_07"
Then Response status code should be 400

Scenario: TC_PET_08 Multiple missing fields
Given Petstore API is available
When I send POST request using test data "TC_PET_08"
Then Response status code should be 400

Scenario: TC_PET_09 Invalid data types
Given Petstore API is available
When I send POST request using test data "TC_PET_09"
Then Response status code should be 400
