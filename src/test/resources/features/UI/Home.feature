Feature: Home page actions

Scenario: Verify Home Page
  Given I open the application URL
  When I check the home page
  Then I verify the request types and endpoints
  And I select a specific option
  Then I verify the request "/api/users/23" , response code "404" and response body "{}" details