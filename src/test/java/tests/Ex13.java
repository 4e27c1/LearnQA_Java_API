package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Ex13 {

    final String UA1 = "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30^Mobile^No^Android";

    final String UA2 = "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1^Mobile^Chrome^iOS";
    final String UA3 = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)^Googlebot^Unknown^Unknown";
    final String UA4 = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0^Web^Chrome^No";
    final String UA5 = "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1^Mobile^browser^iPhone";

        
    @ParameterizedTest
    @ValueSource (strings = {UA1, UA2, UA3, UA4,UA5})
    public void testUAmethod (String condition) {

        String[] parts = condition.split("\\^");
        String UATest = parts[0];
        String ExpPlatform = parts[1];
        String ExpBrowser = parts[2];
        String ExpDevise = parts[3];


        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("User-Agent",UATest);


        JsonPath response = RestAssured
                .given()
                .headers(queryParams)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();
        response.prettyPrint();
        assertEquals(ExpPlatform, response.getString("platform"), "Не совпадают платформы, юзерагент "+UATest);
        assertEquals(ExpBrowser, response.getString("browser"), "Не совпадают браузеры, юзерагент "+UATest);
        assertEquals(ExpDevise, response.getString("device"), "Не совпадают устройства, юзерагент "+UATest);






    }
}
