
package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private static final Faker faker =
            new Faker(new Locale("ru"));

    private static final Random random = new Random();

    private DataGenerator() {
    }

    // Генерация города
    public static String generateCity() {
        String[] cities = {
                "Москва",
                "Санкт-Петербург",
                "Казань",
                "Новосибирск",
                "Екатеринбург"
        };

        return cities[random.nextInt(cities.length)];
    }

    // Генерация фамилии и имени с помощью Faker
    public static String generateName() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        return lastName + " " + firstName;
    }

    // Генерация телефона с помощью Faker
    // Формат: +7 и 10 случайных цифр
    public static String generatePhone() {
        return faker.numerify("+7##########");
    }

    // Генерация даты относительно текущего дня
    public static String generateDate(int daysFromToday) {
        return LocalDate.now()
                .plusDays(daysFromToday)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    // Генерация данных клиента
    public static DeliveryData generateClient() {
        return new DeliveryData(
                generateCity(),
                generateName(),
                generatePhone()
        );
    }
}
