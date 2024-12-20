package com.company.key;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class KeyResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/key")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }

}