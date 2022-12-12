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
    public void HomeworkEx7() {


        String link = "https://playground.learnqa.ru/api/long_redirect";

        int statusCode;
        do {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)

                    .when()
                    .get(link);

                statusCode = response.getStatusCode();



             String locationHeader = response.getHeader("Location");
            link = locationHeader;

        System.out.println(statusCode);
        System.out.println(link);

        }
        while (statusCode == 301);








    }
}
