package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestcase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserEditTest extends BaseTestcase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
  /*  @Test
    public void testEditJustCreatedTest(){
        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        String userId = responseCreateAuth.getString("id");

        //LOGIN
        Map<String,String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        //EDIT
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token",this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/"+ userId)
                .andReturn();

        //GET
        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token",this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .get("https://playground.learnqa.ru/api/user/"+ userId)
                .andReturn();
        Assertions.asserJsonByName(responseUserData, "firstName", newName);


         }*/
         // EDIT USER WITH AUTH FOR OTHER USER
    @Test
    @Description("This test tryes to edit user info with authorization for another user")
    @DisplayName("EDIT Test negative, need auth for current user")
    public void testEditUserWithOtherUserAuth() {

        //CREATING USER
        Map<String, String> userData = DataGenerator.getRegistrationData();


        Response responseRegUniqueEmail = apiCoreRequests
                .makeRequestWithUniqueEmail(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );
        Assertions.assertResponseCodeEquals(responseRegUniqueEmail, 200);
        Assertions.assertJsonHasField(responseRegUniqueEmail, "id");
        int eee = responseRegUniqueEmail.jsonPath().getInt("id");

           /* String newID = responseRegUniqueEmail.getBody().asString();
            String[] prts = newID.split(":");
            String number = prts[1];
            int foo = Integer.parseInt(number);*/

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
        // EDIT OTHER USER
        Response responseEditWithWrongAuth = apiCoreRequests
                .editWithWrongAuth(
                        "https://playground.learnqa.ru/api/user/{id}",
                        cookie,
                        header,
                        eee
                );
        Assertions.assertResponseTextEquals(responseEditWithWrongAuth, "Auth token not supplied");

    }

    //EDIT WITHOUT AUTH
    @Test
    @Description("This test tryes to edit user info with no auth")
    @DisplayName("EDIT Test negative, need auth ")
    public void testEditUserWithoutAuth() {


        Map<String, String> userData = DataGenerator.getRegistrationData();



        Response responseEditNoAuth = apiCoreRequests
                .editwithoutAuth(
                        "https://playground.learnqa.ru/api/user/{id}",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseEditNoAuth, 400);
        Assertions.assertResponseTextEquals(responseEditNoAuth,"Auth token not supplied");
    }
    // EDIT WITH DROKEN EMAIL
    @Test
    @Description("This test tryes to edit user info with wrong email")
    @DisplayName("EDIT Test negative, need correct email ")
    public void testEditUserWithBrokenEmail() {

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
       // Assertions.assertResponseTextEquals(responseCheckAuth, "user_id");

        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        //get wrong email and try to edit
        Map<String, String> wrongEmail = DataGenerator.getRegistrationDataBroken();
        Response responseWithBrokenEmail = apiCoreRequests
                .makePUTRequestWithBrokenEmail(
                        "https://playground.learnqa.ru/api/user/{id}",
                        header,
                        cookie,
                        wrongEmail,
                        eee
                );
        Assertions.assertResponseTextEquals(responseWithBrokenEmail,"Invalid email format");

    }

    //EDIT WITH VERY SHORT USERNAME
    @Test
    @Description("This test tryes to edit user info with very short username")
    @DisplayName("EDIT Test negative, need correct username")
    public void testEditUserWithShortUsername() {

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
        // Assertions.assertResponseTextEquals(responseCheckAuth, "user_id");

        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        //get wrong Username and try to edit
        Map<String, String> wrongUsername = DataGenerator.getRegistrationDataWithShortName();
        Response responseEditWithShortName = apiCoreRequests
                .makePUTRequestWithShortName(
                        "https://playground.learnqa.ru/api/user/{id}",
                        header,
                        cookie,
                        wrongUsername,
                        eee
                );
        Assertions.assertResponseTextEquals(responseEditWithShortName,"{\"error\":\"Too short value for field username\"}");
    }




}
