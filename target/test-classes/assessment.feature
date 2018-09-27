Feature: Testing Pet Clinic

	Scenario: U1
    Given a vet
    When I click on some records
    Then I can see the care available for animals

	Scenario: U2
    Given an admin
    When I update a record
    Then the correct details are now shown

	Scenario: U3
    Given an admin
    When I delete a animal
    Then emails arent sent to deceased annimals
	
	Scenario: U5
    Given an admin
    When I add new records
    Then the records are correct
	
	Scenario: U5
    Given an admin
    When I add new owners to the records
    Then the details show the change
    
    Scenario: U6
    Given Get the results for a load test
    When create a test load report
    Then end a test load report
    
    Scenario: U7
    Given Get the results for a Stress test
    When create a test stress report
    Then end a test stress report
    
        Scenario: U8
    Given Get the results for a spike test
    When create a test spike report
    Then end a test spike report

 