package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.BaseTestcase;
import lib.Assertions;
import lib.ApiCoreRequests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.http.Headers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;


@Epic("Authorezition cases")
@Feature("Authorization")
public class UserAuthTest extends BaseTestcase{
    String cookie;
    String header;
    int userIDOnAuth;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @BeforeEach
    public void loginUser(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        this.userIDOnAuth = this.getIntFromJson(responseGetAuth,"user_id");

    }

    @Test
    @Description("This test successfully authorize user by email and password")
    @DisplayName("Test positive auth user")
    public void testAuthUser(){


        //Map<String, String> cookies = responseGetAuth.getCookies();
        //Headers headers = responseGetAuth.getHeaders();
       // int userIDOnAuth = responseGetAuth.jsonPath().getInt("user_id");

       // assertEquals (200, responseGetAuth.statusCode(), "Unexpected status code");
        //assertTrue (cookies.containsKey("auth_sid"), "Response doesn't have 'auth_sid' cookie");
       // assertTrue (headers.hasHeaderWithName("x-csrf-token"), "Response doesn't have 'x-csrf-token' headers");
       // assertTrue(responseGetAuth.jsonPath().getInt("user_id") >0, "User ID should be greater than 0" );

        Response responseCheckAuth = apiCoreRequests
                .makeGetRequest(
                        "https://playground.learnqa.ru/api/user/auth",
                        this.header,
                        this.cookie);


       Assertions.asserJsonByName(responseCheckAuth, "user_id", this.userIDOnAuth);

    }
    @Description("This test checks authorizatio without sending auth cookie or token")
    @DisplayName("Test negative auth user")
    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    public void testNegativeUserAuth (String condition){

        if (condition.equals("cookie")) {
            Response responseForCheck = apiCoreRequests.makeGetRequestWithCookie(
                    "https://playground.learnqa.ru/api/user/auth",
                    this.cookie
            );
            Assertions.asserJsonByName(responseForCheck, "user_id", 0);
        } else if (condition.equals("headers")) {
            Response responseForCheck = apiCoreRequests.makeGetRequestWithToken(
                    "https://playground.learnqa.ru/api/user/auth",
                    this.header
            );
            Assertions.asserJsonByName(responseForCheck,"user_id", 0);
        } else {
            throw new IllegalArgumentException("Condition value is not known: "+condition);
        }

    }
}
