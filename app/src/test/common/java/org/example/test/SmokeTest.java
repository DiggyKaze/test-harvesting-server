package org.example.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public abstract class SmokeTest extends BaseHttpTest {

    private static final String BACKEND_URL =
            System.getenv().getOrDefault("TEST_BACKEND_URL", "http://localhost:8080");

    @BeforeAll
    static void requireDevOrStageProfile() {
        String profile = serverProfile();
        boolean allowed = "dev".equals(profile) || "stage".equals(profile);

        String reason = "Smoke tests skipped: server at " + BACKEND_URL
                + " runs profile '" + profile + "', expected dev or stage";
        if (!allowed) {
            System.out.println(reason);
        }
        assumeTrue(allowed, reason);
    }

    private static String serverProfile() {
        try {
            Response info = RestAssured.given()
                    .baseUri(BACKEND_URL)
                    .get("/actuator/info");
            return info.statusCode() == 200 ? info.path("profile") : null;
        } catch (Exception e) {
            return null;
        }
    }
}
