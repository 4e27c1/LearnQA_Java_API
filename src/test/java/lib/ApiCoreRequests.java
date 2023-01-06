package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests {
    @Step("Make GET-request with token and auth_cookie")
    public Response makeGetRequest(String url, String token, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();


    }
    @Step("Make GET-request with auth_cookie only")
    public Response makeGetRequestWithCookie(String url,  String cookie) {
        return given()
                .filter(new AllureRestAssured())

                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();


    }
    @Step("Make GET-request with token only")
    public Response makeGetRequestWithToken(String url, String token) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))

                .get(url)
                .andReturn();


    }
    @Step("Make POST-request for authorization")
    public Response makePostRequest(String url, Map<String,String> authData) {
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();
    }

    @Step ("POSt request with existing email")
    public Response makeRequestWithExistingEmail (String url,  Map<String,String> userData){
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();
    }

    @Step("POST request with unique email")
    public Response makeRequestWithUniqueEmail(String url, Map<String,String> dataParam){
        return given()
                .filter(new AllureRestAssured())
                .body(dataParam)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

    }
    @Step("POST request with broken email")
    public Response makeRequestWithBrokenEmail(String url, Map<String,String> data) {
        return given()
                .filter(new AllureRestAssured())
                .body(data)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();
    }
    @Step("Make POST request registration without email")
    public Response makePostRequestWithoutEmail(String url,Map<String, String> data) {
        Map<String, String> dataP = new HashMap<>();
        dataP.put("password", "123");
        dataP.put("username", "learnqa");
        dataP.put("firstName", "learnqa");
        dataP.put("lastName", "learnqa");
        return given()
                .filter(new AllureRestAssured())

                .body(dataP)
                .post(url)
                .andReturn();
    }
    @Step("Make POST request registration without pass")
    public Response makePostRequestWithoutPass(String url,Map<String, String> data) {
        Map<String, String> dataP = new HashMap<>();
        dataP.put("email", DataGenerator.getRandomEmail());
        dataP.put("username", "learnqa");
        dataP.put("firstName", "learnqa");
        dataP.put("lastName", "learnqa");
        return given()
                .filter(new AllureRestAssured())

                .body(dataP)
                .post(url)
                .andReturn();
    }
    @Step("Make POST request registration without username")
    public Response makePostRequestWithoutUsername(String url,Map<String, String> data) {
        Map<String, String> dataP = new HashMap<>();
        dataP.put("email", DataGenerator.getRandomEmail());
        dataP.put("password", "123");
        dataP.put("firstName", "learnqa");
        dataP.put("lastName", "learnqa");
        return given()
                .filter(new AllureRestAssured())

                .body(dataP)
                .post(url)
                .andReturn();
    }
    @Step("Make POST request registration without firstName")
    public Response makePostRequestWithoutFirstName(String url,Map<String, String> data) {
        Map<String, String> dataP = new HashMap<>();
        dataP.put("email", DataGenerator.getRandomEmail());
        dataP.put("password", "123");
        dataP.put("username", "learnqa");
        dataP.put("lastName", "learnqa");
        return given()
                .filter(new AllureRestAssured())

                .body(dataP)
                .post(url)
                .andReturn();
    }
    @Step("Make POST request registration without last name")
    public Response makePostRequestWithoutLastName(String url,Map<String, String> data) {
        Map<String, String> dataP = new HashMap<>();
        dataP.put("email", DataGenerator.getRandomEmail());
        dataP.put("password", "123");
        dataP.put("firstName", "learnqa");
        dataP.put("username", "learnqa");
        return given()
                .filter(new AllureRestAssured())

                .body(dataP)
                .post(url)
                .andReturn();
    }

    @Step ("make request with short name")
    public Response makePOSTRequestWithShortName(String url, Map<String,String> data){

        return given()
                .filter(new AllureRestAssured())
                .body(data)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

    }
    @Step ("make request with long name")
    public Response makePOSTRequestWithLongName(String url, Map<String,String> data){

        return given()
                .filter(new AllureRestAssured())
                .body(data)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

    }
    @Step("Get User info with wrong auth")
    public Response makeRequestForUserInfo(String url, String header, String cookie, int eee){
        return given()
                .filter(new AllureRestAssured())
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                //.queryParam(String.valueOf(eee))
                .get("https://playground.learnqa.ru/api/user/"+eee)
                .andReturn();
    }

    @Step("Try to edit user data without authrization")
    public Response editwithoutAuth(String url, Map<String, String> data){
        return given()
                .filter(new AllureRestAssured())
                .body(data)
                .put ("https://playground.learnqa.ru/api/user/2")
                .andReturn();

    }
    @Step("Edit with wrong auth")
    public Response editWithWrongAuth(String url, String header, String cookie, int eee){
        return given()
                .filter(new AllureRestAssured())
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .put("https://playground.learnqa.ru/api/user/"+eee)
                .andReturn();
        }

        @Step("Edit with broken email")
    public Response makePUTRequestWithBrokenEmail(String url,String header,String cookie, Map<String, String> wrongEmail, int eee ){
        return given()
                .filter(new AllureRestAssured())
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .body(wrongEmail)
                .put("https://playground.learnqa.ru/api/user/"+eee)
                .andReturn();
        }
        @Step ("Edit cuurent user with short username")
    public Response makePUTRequestWithShortName(String url,String header,String cookie, Map<String, String> wrongUsername, int eee){
        return given()
                .filter(new AllureRestAssured())
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .body(wrongUsername)
                .put("https://playground.learnqa.ru/api/user/"+eee)
                .andReturn();
        }

        @Step("Delete user id=2 with auth")
    public Response makeDeleteRequestForUser2(String url, String header, String cookie){
        return given()
                .filter(new AllureRestAssured())
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .delete("https://playground.learnqa.ru/api/user/2")
                .andReturn();
        }

    public Response makeDeleteRequest(String url, String header, String cookie, int eee){
        return given()
                .filter(new AllureRestAssured())
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .delete("https://playground.learnqa.ru/api/user/"+eee)
                .andReturn();
    }
}
