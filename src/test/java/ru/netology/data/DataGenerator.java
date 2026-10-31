
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

    // Генерация фамилии и имени
    public static String generateName() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        return (lastName + " " + firstName)
                .replace('ё', 'е')
                .replace('Ё', 'Е');
    }

    // Генерация телефона в формате +7XXXXXXXXXX
    public static String generatePhone() {
        StringBuilder phone = new StringBuilder("+7");

        for (int i = 0; i < 10; i++) {
            phone.append(random.nextInt(10));
        }

        return phone.toString();
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
