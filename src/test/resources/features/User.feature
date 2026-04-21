# ---------------------------------------------------------
# Author : Adithya P and Arun Balaji B
# Module : User
# Description : Testing User Module in PetStore API
# ---------------------------------------------------------

Feature: User Module Testing

# POST /user
Scenario Outline: Create User with Valid Payload
    When I Send POST request to create a user "<id>" "<username>" "<firstName>" "<lastName>" "<email>" "<password>" "<phone>" "<userStatus>":
    Then Response status code should be 200
    And Response status line contains "200 OK"
    And Response time less than 3000 ms
  
  Examples:
    | id       | username       | firstName | lastName | email                 | password  | phone       | userStatus |
    | 1001     | TestUser       | John      | Doe      | john@example.com      | Pass@1234 | 9876543210  | 1          |
    | 1002     | TesterBalaji   | Arun      | Balaji   | arun@example.com      | Pass@1234 | 7894561230  | 1          |
    | 1003     | TesterAdithya  | Adithya   | P        | adithya@example.com   | Pass@1234 | 9874563210  | 1          |

Scenario: Create User with Missing Required Fields
    When I Send POST request to create a user with empty body
    Then Response status code should be 400
    And Response status line contains "Bad Request"
    And Response time less than 2000 ms

# GET /user/{username}
Scenario: Fetching User Valid Username
    When I Send GET user request with username "TestUser"
    Then Response status code should be 200
    And Response status line contains "200 OK"
    And Response time less than 2000 ms
    And Validate "User" schema

Scenario: Fetching User Invalid Username
    When I Send GET user request with username "User@#$%"
    Then Response status code should be 400
    And Response status line contains "Bad Request"
    And Response time less than 2000 ms

Scenario: Fetching User with Non-Existent Username
    When I Send GET user request with username "nonExistentUser99999"
    Then Response status code should be 404
    And Response status line contains "Not Found"
    And Response time less than 2000 ms

# POST /user/createWithArray
Scenario: Create Users with Valid Array Payload
    When I Send POST request to create users with array
    Then Response status code should be 200
    And Response status line contains "200 OK"
    And Response time less than 2000 ms

Scenario: Create Users with Empty Array
    When I Send POST request to create users with empty array
    Then Response status code should be 200
    And Response status line contains "200 OK"
    And Response time less than 2000 ms

# POST /user/createWithList
Scenario: Create Users with Valid List Payload
    When I Send POST request to create users with list
    Then Response status code should be 200
    And Response status line contains "200 OK"
    And Response time less than 2000 ms

Scenario: Create Users with Empty List
    When I Send POST request to create users with empty list
    Then Response status code should be 200
    And Response status line contains "200 OK"
    And Response time less than 2000 ms

# GET /user/login
Scenario: Login with Valid Credentials
    When I Send GET login request with username "TestUser" and password "Pass@1234"
    Then Response status code should be 200
    And Response status line contains "200 OK"
    And Response time less than 2000 ms
    And Response body contains "logged in user session"

Scenario: Login with Invalid Password
    When I Send GET login request with username "TestUser" and password "wrongPass"
    Then Response status code should be 400
    And Response status line contains "Bad Request"
    And Response time less than 2000 ms

Scenario: Login with Empty Username
    When I Send GET login request with username "" and password "Pass@1234"
    Then Response status code should be 400
    And Response status line contains "Bad Request"
    And Response time less than 2000 ms

Scenario: Login with Empty Password
    When I Send GET login request with username "TestUser" and password ""
    Then Response status code should be 400
    And Response status line contains "Bad Request"
    And Response time less than 2000 ms

Scenario: Login with Both Fields Empty
    When I Send GET login request with username "" and password ""
    Then Response status code should be 400
    And Response status line contains "Bad Request"
    And Response time less than 2000 ms

# GET /user/logout
Scenario: Logout User Successfully
    When I Send GET logout request
    Then Response status code should be 200
    And Response status line contains "200 OK"
    And Response time less than 2000 ms

# PUT /user/{username}
Scenario: Update User with Valid Payload
    When I Send PUT request to update user "TestUser" with data from excel
    Then Response status code should be 200
    And Response status line contains "200 OK"
    And Response time less than 2000 ms

Scenario: Update Non-Existent User
    When I Send PUT request to update user "nonExistentUser99999" with the following details:
      | id   | username             | firstName | lastName | email              | password  | phone      | userStatus |
      | 9999 | nonExistentUser99999 | Ghost     | User     | ghost@example.com  | Pass@000  | 0000000000 | 0          |
    Then Response status code should be 404
    And Response status line contains "Not Found"
    And Response time less than 2000 ms

Scenario: Update User with Empty Body
    When I Send PUT request to update user "TestUser" with empty body
    Then Response status code should be 400
    And Response status line contains "Bad Request"
    And Response time less than 2000 ms

# DELETE /user/{username}
Scenario: Delete User with Valid Username
    When I Send DELETE request for user "TestUser"
    Then Response status code should be 200
    And Response status line contains "200 OK"
    And Response time less than 2000 ms

Scenario: Delete Non-Existent User
    When I Send DELETE request for user "nonExistentUser99999"
    Then Response status code should be 404
    And Response status line contains "Not Found"
    And Response time less than 2000 ms

Scenario: Delete User with Invalid Username
    When I Send DELETE request for user "User@#$%"
    Then Response status code should be 400
    And Response status line contains "Bad Request"
    And Response time less than 2000 ms