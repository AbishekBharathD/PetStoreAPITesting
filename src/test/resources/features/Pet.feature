# ---------------------------------------------------------
# Author : Aishwarya
# Module : Pet
# Description : Pet API with request chaining + DDT
# ---------------------------------------------------------

Feature: Manage pet operations including creation, update, retrieval, and deletion

    

    Scenario Outline: Adding Pet

        When user sends POST request to add pet with <id> "<name>" "<status>"
        Then Response status code should be 200
        And Response time less than 5000 ms
        And Response status line contains "200 OK"

        Examples:
        |id   |name   |status     |
        |2001 |dog    |available  |
        |2002 |cat    |pending    |
        |2003 |parrot |sold       |
        |2004 |rabbit |available  |
        |2005 |horse  |pending    |

    Scenario: Get Vaild Pet
        When user sends GET request to fetch pet by existing id
        Then Response status code should be 200
        And Response time less than 5000 ms
        And Response status line contains "200 OK"

    Scenario: Update Vaild Pet
        When user sends PUT request to update pet
        Then Response status code should be 200
        And Response time less than 5000 ms
        And Response status line contains "200 OK"

    Scenario: Delete the created Pet
        When user sends DELETE request to delete pet by existing id
        Then Response status code should be 200
        And Response time less than 5000 ms
        And Response status line contains "200 OK"

    Scenario: Get the deleted Pet
        When user sends GET request to fetch pet with non existing id
        Then Response status code should be 404
        And Response time less than 5000 ms
        And Response status line contains "404 Not Found"
        
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

    Scenario: Retrieve pet with invalid ID
        When user sends GET request for invalid pet id "abc"
        Then Response status code should be 400
        And Response time less than 5000 ms
        And Response status line contains "400 Bad Request"

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