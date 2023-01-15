package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestcase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class UserDeleteTest extends BaseTestcase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("This test tryes to delete user with authorization ")
    @DisplayName("DELETE Test negative, cant delete user id2")
    @Owner ("Akchurina AV")
    @Severity(value = SeverityLevel.BLOCKER)

    public void testDeleteUserWithID2()  {

        //AUTHRIZATION
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = apiCoreRequests
                .makePostRequest(
                        "https://playground.learnqa.ru/api/user/login",
                        authData);
        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        //DELETING USER
        Response responseDeleteUser2 = apiCoreRequests
                .makeDeleteRequestForUser2(
                        "https://playground.learnqa.ru/api/user/2",
                        header,
                        cookie
                );
        Assertions.assertResponseTextEquals(responseDeleteUser2, "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");

    }
    @Test
    @Description("This test tryes to delete user with authorization ")
    @DisplayName("DELETE Test posititve, creating and deleting user")
    public void testDeleteNewUser(){
        //creating user
        Map<String, String> userData = DataGenerator.getRegistrationData();
        Response responseRegUniqueEmail = apiCoreRequests
                .makeRequestWithUniqueEmail(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );
        System.out.println(responseRegUniqueEmail);
        String em = userData.get("email");
        String pass = "123";
        int eee = responseRegUniqueEmail.jsonPath().getInt("id");

        Assertions.assertResponseCodeEquals(responseRegUniqueEmail, 200);
        Assertions.assertJsonHasField(responseRegUniqueEmail,"id");


        //login user
        Map<String,String>authData = new HashMap<>();
        authData.put("email",em);
        authData.put("password",pass);
        Response responseGetAuth = apiCoreRequests
                .makePostRequest(
                        "https://playground.learnqa.ru/api/user/login",
                        authData);
        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        //DELETE USER
        Response responseDeleteUser = apiCoreRequests
                .makeDeleteRequest(
                        "https://playground.learnqa.ru/api/user/{id}",
                        header,
                        cookie,

                        eee
                );

        //GET INFO FROM CREATED USER

        Response responseGetInfoOtherUser = apiCoreRequests
                .makeRequestForUserInfo(
                        "https://playground.learnqa.ru/api/user/{id}",
                        header,
                        cookie,
                        eee
                );
        Assertions.assertResponseTextEquals(responseGetInfoOtherUser, "User not found");

    }
    @Test
    @Description("This test tryes to delete user with authorization on another user ")
    @DisplayName("DELETE Test negative, cant delete user without right auth")
    public void testDeleteUserWithOtherUserAuth(){
        //creating user
     Map<String, String> userData = DataGenerator.getRegistrationData();
        Response responseRegUniqueEmail = apiCoreRequests
                .makeRequestWithUniqueEmail(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );
        System.out.println(responseRegUniqueEmail);
        String em = userData.get("email");
        String pass = "123";
        int eee = responseRegUniqueEmail.jsonPath().getInt("id")-1;

        Assertions.assertResponseCodeEquals(responseRegUniqueEmail, 200);
        Assertions.assertJsonHasField(responseRegUniqueEmail,"id");

        //login user
        Map<String,String>authData = new HashMap<>();
        authData.put("email",em);
        authData.put("password",pass);
        Response responseGetAuth = apiCoreRequests
                .makePostRequest(
                        "https://playground.learnqa.ru/api/user/login",
                        authData);
        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");


        //DELETE USER
        Response responseDeleteUser = apiCoreRequests
                .makeDeleteRequest(
                        "https://playground.learnqa.ru/api/user/{id}",
                        header,
                        cookie,
                        eee
                );
        Assertions.assertResponseTextEquals(responseDeleteUser,"");
        //GET INFO FROM CREATED USER

        Response responseGetInfoOtherUser = apiCoreRequests
                .makeRequestForUserInfo(
                        "https://playground.learnqa.ru/api/user/{id}",
                        header,
                        cookie,
                        eee
                );
        Assertions.assertResponseTextEquals(responseGetInfoOtherUser, "User not found");



    }


}


