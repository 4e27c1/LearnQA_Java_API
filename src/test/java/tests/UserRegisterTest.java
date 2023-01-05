package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestcase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;
@Epic("User registeration cases")
@Feature("Register user")
public class UserRegisterTest extends BaseTestcase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("This test tryes to create user with existing email")
    @DisplayName("Test negative, email is already exists")
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.com";


        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseExistEmail = apiCoreRequests
                .makeRequestWithExistingEmail(
                        "https://playground.learnqa.ru/api/user/",

                        userData
                );

        Assertions.assertResponseCodeEquals(responseExistEmail, 400);
        Assertions.assertResponseTextEquals(responseExistEmail, "Users with email '"+ email+"' already exists");
    }


    @Test
    @Description("This test creating user with new generated email")
    @DisplayName("Test positive, email is unique")
    public void testCreateUsersuccessfully() {
       // String email = DataGenerator.getRandomEmail();

        Map<String, String> userData = DataGenerator.getRegistrationData();


        Response responseRegUniqueEmail = apiCoreRequests
                .makeRequestWithUniqueEmail(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseRegUniqueEmail, 200);
        Assertions.assertJsonHasField(responseRegUniqueEmail,"id");
    }
    @Test
    @Description("This test tryes to create user with email without @")
    @DisplayName("Test negative, email is broken")
    public void testCreateUserWithBrokenEmail() {


        Map<String, String> userData = DataGenerator.getRegistrationDataBroken();


        Response responseRegWithBrokenEmail = apiCoreRequests
                .makeRequestWithBrokenEmail(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseRegWithBrokenEmail, 400);
        Assertions.assertResponseTextEquals(responseRegWithBrokenEmail,"Invalid email format");
    }

    @Description("This test tryes to register user without one parametr")
    @DisplayName("Test negative register user(without one parametr)")
    @ParameterizedTest
    @ValueSource(strings = {"email", "username", "firstName", "lastName"})
    public void testNegativeUserRegisterWithoutParam (String condition){
        Map<String, String> dataParam = DataGenerator.getRegistrationData();
        String[] expectedFields = {"email", "password", "username", "firstName", "lastName"};

        if (condition.equals("email")) {
            Response responseForReg = apiCoreRequests
                    .makePostRequestWithoutEmail(
                            "https://playground.learnqa.ru/api/user/",
                            dataParam
            );
            Assertions.assertResponseTextEquals(responseForReg, "The following required params are missed: email");
        } else if (condition.equals("password")) {
            Response responseForReg = apiCoreRequests.makePostRequestWithoutPass(
                    "https://playground.learnqa.ru/api/user/",
                    dataParam
            );
            Assertions.assertResponseTextEquals(responseForReg,"The following required params are missed: password");
        }
        else if (condition.equals("username")) {
            Response responseForReg = apiCoreRequests.makePostRequestWithoutUsername(
                    "https://playground.learnqa.ru/api/user/",
                    dataParam
            );
            Assertions.assertResponseTextEquals(responseForReg,"The following required params are missed: username" );
        }
        else if (condition.equals("lastName")) {
            Response responseForReg = apiCoreRequests.makePostRequestWithoutLastName(
                    "https://playground.learnqa.ru/api/user/",
                    dataParam
            );
            Assertions.assertResponseTextEquals(responseForReg,"The following required params are missed: lastName");
        }
        else if (condition.equals("firstName")) {
            Response responseForReg = apiCoreRequests.makePostRequestWithoutFirstName(
                    "https://playground.learnqa.ru/api/user/",
                    dataParam
            );
            Assertions.assertResponseTextEquals(responseForReg,"The following required params are missed: firstName");}
        else {
            throw new IllegalArgumentException("Condition value is not known: "+condition);
        }

    }
    @Test
    @Description("This test tryes to create user with 1-symbol name")
    @DisplayName("Test negative, name is too short")
    public void testCreateUserWithShortName() {


        Map<String, String> userData = DataGenerator.getRegistrationDataWithShortName();


        Response responseRegWithShortName = apiCoreRequests
                .makePOSTRequestWithShortName(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseRegWithShortName, 400);
        Assertions.assertResponseTextEquals(responseRegWithShortName,"The value of 'username' field is too short");
    }
    @Test
    @Description("This test tryes to create user with 251-symbol name")
    @DisplayName("Test negative, name is too long")
    public void testCreateUserWithLongName() {


        Map<String, String> userData = DataGenerator.getRegistrationDataWithLongName();


        Response responseRegWithLongName = apiCoreRequests
                .makePOSTRequestWithShortName(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseRegWithLongName, 400);
        Assertions.assertResponseTextEquals(responseRegWithLongName,"The value of 'username' field is too long");
    }



}
