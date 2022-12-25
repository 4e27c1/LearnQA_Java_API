package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ex10 {
    @Test

    public void testHWCookie(){

        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_cookie")

                .andReturn();

        String cookie_value = response.getCookie("Homework");
        System.out.println("cookie value is " + cookie_value);

         assertTrue(cookie_value!=null, "Cookie value is null");

             }

}
