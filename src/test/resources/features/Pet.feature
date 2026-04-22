# ---------------------------------------------------------
# Author : Aishwarya, Anukraha
# Module : Pet
# Description : Pet API with request chaining + DDT
# ---------------------------------------------------------
Feature: Manage pet operations including creation, update, retrieval, and deletion

  Scenario Outline: Add pet using Excel
    When user sends POST request using excel row <rowNumber>
    Then Response status code should be 200
    When user sends GET request to fetch pet by existing id
    Then Response status code should be 200
    And Response time less than 5000 ms
    And Response status line contains "200 OK"
    When user sends PUT request to update pet
    Then Response status code should be 200
    And Response time less than 5000 ms
    And Response status line contains "200 OK"
    When user sends PUT request to update pet
    And user sends GET request to fetch pet by existing id
    Then Response status code should be 200
    And Response body should reflect updated values
    When user sends DELETE request to delete pet by existing id
    Then Response status code should be 200
    And Response time less than 5000 ms
    And Response status line contains "200 OK"
    When user sends GET request to fetch pet with non existing id
    Then Response status code should be 404
    And Response time less than 5000 ms
    And Response status line contains "404 Not Found"

    Examples:
      | rowNumber |
      |         0 |
      |         1 |
      |         2 |
      |         3 |
      |         4 |
      |         5 |

  Scenario: Adding Pet using DataTable
    When user sends POST request with pet data
      | id   | name   | status    |
      | 2001 | doggie | available |
      | 2002 | cat    | sold      |
      | 2003 | bunny  | pending   |
    Then Response status code should be 200
    And Response time less than 5000 ms
    And Response status line contains "200 OK"

  Scenario: Create pet with missing mandatory data
    When user sends POST request using test data "TC_PET_07"
    Then Response status code should be 400
    And Response time less than 5000 ms
    And Response status line contains "400 Bad Request"

  Scenario: Create pet with invalid data types
    When user sends POST request using test data "TC_PET_09"
    Then Response status code should be 400
    And Response time less than 5000 ms
    And Response status line contains "400 Bad Request"

  Scenario Outline: Retrieve pet with invalid ID
    When user sends GET request for invalid pet id "<id>"
    Then Response status code should be 400
    And Response time less than 5000 ms
    And Response status line contains "400 Bad Request"

    Examples:
      | id   |
      | abc  |
      | -123 |

  Scenario: Upload image for valid pet
    When user uploads image for pet id 2001
    Then Response status code should be 200
    And Response time less than 5000 ms
    And Response status line contains "200 OK"

  Scenario: Upload image for invalid pet
    When user uploads image for pet id 9999
    Then Response status code should be 404
    And Response time less than 5000 ms
    And Response status line contains "404 Not Found"

  Scenario: View pets by status
    When user sends GET request for pets by status "available"
    Then Response status code should be 200
    And Response time less than 5000 ms
    And Response status line contains "200 OK"
