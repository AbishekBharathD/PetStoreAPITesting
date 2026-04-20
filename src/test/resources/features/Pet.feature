Feature: Petstore API Testing (Data Driven)

  Scenario: TC_PET_01 Verify adding new pet with valid data
    Given Petstore API is available
    When I send POST request using test data "TC_PET_01"
    Then Response status code should be 200
    And Response time less than "5000" ms
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

  Scenario: TC_PET_10 Invalid JSON
    Given Petstore API is available
    When I send POST request with invalid JSON structure
    Then Response status code should be 400

  Scenario: TC_PET_12 Delete pet with valid ID
    Given Pet with id 1711 exists
    When I send DELETE request for pet id 1711
    Then Response status code should be 200
#-------------------------------------------------------------------

  Scenario: TC_PET_11 Find pets by status
    Given Petstore API is available
    When I send GET request for pets by status "available"
    Then Response status code should be 200

  Scenario: TC_PET_14 Upload image with valid ID
    Given Pet with id 1711 exists
    When I upload image for pet id 1711
    Then Response status code should be 200

  Scenario: TC_PET_13 Delete pet with invalid ID
    Given Petstore API is available
    When I send DELETE request for pet id 143
    Then Response status code should be 404

  Scenario: TC_PET_15 Upload image with invalid ID
    Given Petstore API is available
    When I upload image for pet id 143
    Then Response status code should be 404

  Scenario: TC_PET_16 Get pet with negative ID
    Given Petstore API is available
    When I send GET request for pet id -1
    Then Response status code should be 400

  Scenario: TC_PET_17 Get pet with string ID
    Given Petstore API is available
    When I send GET request for invalid pet id "abc"
    Then Response status code should be 400

  Scenario: TC_PET_18 Delete pet with negative ID
    Given Petstore API is available
    When I send DELETE request for pet id -1
    Then Response status code should be 400

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
