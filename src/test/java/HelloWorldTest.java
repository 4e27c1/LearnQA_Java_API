import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.requestSpecification;

public class HelloWorldTest {
    @Test

        public void testRestAssured() throws InterruptedException {


            JsonPath responseFirstRequest = RestAssured

                    .get( "https://playground.learnqa.ru/ajax/api/longtime_job")
                    .jsonPath();
            String TokenData = responseFirstRequest.get("token");
            int Timer = responseFirstRequest.get("seconds");
            System.out.println(TokenData);
            System.out.println(Timer);

            Map<String, String> Token1 = new HashMap<>();
            Token1.put("token", TokenData);
            //if (responseCookie!=null){
            //     cookies.put("auth_cookie", responseCookie);
            // }

            Thread.sleep(Timer*1000);


            Response responseSecondRequest = RestAssured
                    .given()
                    .queryParams(Token1)
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .andReturn();
            responseSecondRequest.print();
            System.out.println(TokenData);









        }
}
