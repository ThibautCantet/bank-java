package com.bank.infrastructure.controller;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bank.domain.AccountCreated;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.redis.core.RedisTemplate;

import static io.restassured.RestAssured.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class AccountControllerTest {

    private final UUID ACCOUNT_ID = UUID.fromString("00000000-000-0000-0000-000000000002");

    @LocalServerPort
    protected int port;

    @Autowired
    private RedisTemplate<String, Events> redisTemplate;

    @BeforeEach
    void init() {
        RestAssured.basePath += AccountController.PATH;
        RestAssured.port = port;
    }

    @Test
    void get_balance_should_return_zero_when_no_operation() {
        redisTemplate.opsForValue().set(ACCOUNT_ID.toString(), new Events(List.of(new AccountCreated(ACCOUNT_ID))));

        // @formatter:off
        given()
        .when()
                .get(ACCOUNT_ID+"/balance/")
        .then()
                .log().ifValidationFails()
                .statusCode(SC_OK)
                .body("balance", is(0.0f));
        // @formatter:on
    }

    @Test
    void deposit_should_increase_balance() throws JsonProcessingException {
        redisTemplate.opsForValue().set(ACCOUNT_ID.toString(), new Events(List.of(new AccountCreated(ACCOUNT_ID))));

        // @formatter:off
        given()
            .contentType(ContentType.JSON)
            .body("""
            {
                "amount": 100
            }""")
        .when()
            .post(ACCOUNT_ID+"/deposit/")
        .then()
            .log().ifValidationFails()
            .statusCode(SC_NO_CONTENT);
        // @formatter:on

        // @formatter:off
        given()
        .when()
            .get(ACCOUNT_ID+"/balance/")
        .then()
            .log().ifValidationFails()
            .statusCode(SC_OK)
            .body("balance", is(100.0f));
        // @formatter:on
    }
}
