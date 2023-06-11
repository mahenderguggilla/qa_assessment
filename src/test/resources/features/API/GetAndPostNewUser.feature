Feature: User Management As a user, I want to be able to retrieve a specific user And create a new user
		
Scenario: Get Specific User
		Given the API is available
		When I send a GET request to "/api/users/2"
		Then the GET call response status code should be 200
		And the GET call response body should contain the following details:
		|data.email 	  	| janet.weaver@reqres.in 	|
		|data.first_name	| Janet										|
		|data.last_name		|	Weaver									|
		
Scenario: Post New User
		Given the API is available
		When I send a POST request to "/api/users" and the request body contains the following details:
		|name		| morpheus 	|
		|job		| leader 		|
		Then the POST call response status code should be 201
		And the POST call response body should contain the following details:
		|name | morpheus|
		|job 	| leader |
				
		

		
