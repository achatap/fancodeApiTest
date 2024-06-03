@FanCodeAPI
Feature: Test FanCode scenario

  Scenario: All the users of City `FanCode` should have more than half of their todos task completed.
    Given User has the "/todos" tasks
    And "/users" belongs to the city FanCode
    Then User Completed task percentage should be greater than 50%

