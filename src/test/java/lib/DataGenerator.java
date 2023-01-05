package lib;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;

public class DataGenerator {
    public static String getRandomEmail() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa" + timestamp + "@example.com";

    }

    public static String getBrokenEmail() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa" + timestamp + "example.com";
    }
    public static String getMinName() {
        String shortName = RandomStringUtils.randomAlphabetic(1);
        return shortName;
    }
    public static String getMaxName() {
        String longName = RandomStringUtils.randomAlphabetic(251);
        return longName;
    }


    public static Map<String, String> getRegistrationData() {
        Map<String, String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", "learnqa");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");
        return data;


    }

    public static Map<String, String> getRegistrationData(Map<String, String> nonDefaultValues) {
        Map<String, String> defaultValues = DataGenerator.getRegistrationData();

        Map<String, String> userData = new HashMap<>();
        String[] keys = {"email", "password", "username", "firstName", "lastName"};
        for (String key : keys) {
            if (nonDefaultValues.containsKey(key)) {
                userData.put(key, nonDefaultValues.get(key));
            } else {
                userData.put(key, defaultValues.get(key));
            }
        }
        return userData;


    }

    public static Map<String, String> getRegistrationDataBroken() {
        Map<String, String> data = new HashMap<>();
        data.put("email", DataGenerator.getBrokenEmail());
        data.put("password", "123");
        data.put("username", "learnqa");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");
        return data;
    }
    public static Map<String,String> getRegistrationDataParam(){
        //Map <String,String> dataParam = new HashMap<>();
        Map<String, String> defaultValues = DataGenerator.getRegistrationData();
        //String[] expectedValue = {"email", "username", "firstName", "lastName"};
        return defaultValues;

    }

    public static Map<String, String> getRegistrationDataWithShortName() {
        Map<String, String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", DataGenerator.getMinName());
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");
        return data;}
    public static Map<String, String> getRegistrationDataWithLongName() {
        Map<String, String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", DataGenerator.getMaxName());
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");
        return data;}
}