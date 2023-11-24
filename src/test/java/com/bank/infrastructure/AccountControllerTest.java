package com.bank.infrastructure;

import com.bank.domain.EventStore;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class AccountControllerTest {

    @LocalServerPort
    protected int port;

    @Autowired
    private EventStore eventStore;

    @BeforeEach
    void init() {
        RestAssured.basePath += AccountController.PATH;
        RestAssured.port = port;
    }

    @Test
    void get_balance_should_return_zero_when_no_operation() {
        // @formatter:off
        given()
        .when()
                .get("/balance")
        .then()
                .log().ifValidationFails()
                .statusCode(SC_OK)
                .body("balance", is(0));
        // @formatter:on
    }

    @Test
    void deposit_should_increase_balance() {
        // @formatter:off
        given()
            .contentType(ContentType.JSON)
            .body("""
            {
                "amount": 100
            }""")
        .when()
            .post("/deposit")
        .then()
            .log().ifValidationFails()
            .statusCode(SC_OK);
        // @formatter:on

        // @formatter:off
        given()
        .when()
            .get("/balance")
        .then()
            .log().ifValidationFails()
            .statusCode(SC_OK)
            .body("balance", is(0));
        // @formatter:on
    }
}
