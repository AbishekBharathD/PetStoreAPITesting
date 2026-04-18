Feature: User Module Testing

Scenario: Fetching User Valid Username
    When I Send GET user request with valid username "TestUser"
    Then Response status code should be 200
    And Response status line contains "200 OK"
    And Response time less than 2000 ms
    And Validate "User" schema

Scenario: Fetching User Invalid Username
    When I Send GET user request with valid username "User@#$%"
    Then Response status code should be 400
    And Response status line contains "Bad Request"
    And Response time less than 2000 ms