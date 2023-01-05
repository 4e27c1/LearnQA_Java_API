package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.BaseTestcase;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserGetTest extends BaseTestcase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    public void testGetUserDataNotAuth(){
        Response responseUserData = RestAssured
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();
        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotField(responseUserData, "firstName");
        Assertions.assertJsonHasNotField(responseUserData, "lastName");
        Assertions.assertJsonHasNotField(responseUserData, "email");
    }

    @Test
    public void testGetUserDetailsAuthSameUser(){
        Map<String,String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        String[] expectedFields = {"username", "firstName", "lastName", "email"};
        Assertions.assertJsonHasFields(responseUserData, expectedFields);

        }


    @Test
    @Description("This test tryes to get info from other user with authrization on standart user")
    @DisplayName("Test negative, we can receive only username, not all scope")
   public void testGetOtherUserDetailsfromAuthUser(){

            //CREATING USER
            Map<String, String> userData = DataGenerator.getRegistrationData();


            Response responseRegUniqueEmail = apiCoreRequests
                    .makeRequestWithUniqueEmail(
                            "https://playground.learnqa.ru/api/user/",
                            userData
                    );
            Assertions.assertResponseCodeEquals(responseRegUniqueEmail, 200);
            Assertions.assertJsonHasField(responseRegUniqueEmail,"id");
           int eee = responseRegUniqueEmail.jsonPath().getInt("id");

           /* String newID = responseRegUniqueEmail.getBody().asString();
            String[] prts = newID.split(":");
            String number = prts[1];
            int foo = Integer.parseInt(number);*/

        //AUTHRIZATION
        Map<String,String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = apiCoreRequests
                .makePostRequest(
                        "https://playground.learnqa.ru/api/user/login",
                        authData);
        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        //GET INFO FROM CREATED USER

        Response responseGetInfoOtherUser = apiCoreRequests
                .makeRequestForUserInfo(
                        "https://playground.learnqa.ru/api/user/{id}",
                        header,
                        cookie,
                        eee
                           );
        Assertions.assertJsonHasField(responseGetInfoOtherUser, "username");
        Assertions.assertJsonHasNotField(responseGetInfoOtherUser, "firstName");
        Assertions.assertJsonHasNotField(responseGetInfoOtherUser, "lastName");
        Assertions.assertJsonHasNotField(responseGetInfoOtherUser, "email");

        }


}
