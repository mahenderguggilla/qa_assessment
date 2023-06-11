Feature: User Management As a user, I want to be able to Click support button and verify support page and upgrade details

Scenario: Verify Home Page
  Given I open the application URL
  When I click on the support button
  Then I verify the support page for "One-time payment ($)" and "Monthly support ($5/month)"
  When I click on upgrade button and provide details "test@example.com"