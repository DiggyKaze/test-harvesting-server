package org.example;

import org.example.test.SmokeTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class ActuatorSmokeTest extends SmokeTest{
	
	@Test
	@DisplayName("Smoke-01: Health endpoint reports up")
	void healthEndpointReportsUp(){
		given()
		.when()
			.get ("/actuator/health")
		.then()
			.statusCode(200)
			.body("status", equalTo("UP"));

	}

	@Test
	@DisplayName("Smoke-02: Health endpoint responds with JSON")
	void healthEndpointRespondWithJson() {
		given()
		.when()
			.get("/actuator/health")
		.then()
			.statusCode(200)
			.contentType(containsString("json"));
	}

	@Test
	@DisplayName("Smoke-03: Health endpoints respond within 2 seconds")
	void healthEndpointRespondsWithTwoSeconds(){
		given()
		.when()
			.get("/actuator/health")
		.then()
			.statusCode(200)
			.time(lessThan(2000l));

	}

	@Test
	@DisplayName("Smoke-04: Info endpoint reports active profile")
	void infoEndpointReportsActiveProfile(){
		given()
		.when()
			.get("/actuator/info")
		.then()
			.statusCode(200)
			.body("profile", anyOf(equalTo("dev"), equalTo("stage")));
	}

	@Test
	@DisplayName("Smoke-05: Sensitive actuator endpoints stay closed")
	void envEndpointsIsNotExposed(){
		given()
		.when()
			.get("/actuator/env")
		.then()
			.statusCode(404);

	}

	@Test
	@DisplayName("Smoke-06: unkon path return 404")
	void unknownPathReturnsNotFound(){
		given()
		.when()
			.get("/this-path-does-not-exist")
		.then()
			.statusCode(404);
	}


}
