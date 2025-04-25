package utilities;

import com.github.javafaker.Faker;

public class MockDataGenerator {

    private static final Faker faker = new Faker();

    public static String getFirstName(){
        return faker.name().firstName();
    }

    public static String generateLastName() {
        return faker.name().lastName();
    }

    public static String generateFullName() {
        return faker.name().fullName();
    }

    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    public static String generatePhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }

    public static String generateAddress() {
        return faker.address().fullAddress();
    }

    public static String generateJobTitle() {
        return faker.job().title();
    }

    public static String generateRandomUsername() {
        return faker.name().username();
    }
}
