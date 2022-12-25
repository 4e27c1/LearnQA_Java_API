package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ex10 {
    @Test

    public void testHWCookie(){

        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_header")
                //.prettyPeek()
                .andReturn();

        String header_value = response.getHeader("x-secret-homework-header");
        System.out.println("cookie value has " + header_value);
        System.out.println(response.getBody().asString());

         assertTrue(header_value!=null, "Header value doesnt contais secret value");

             }

}
