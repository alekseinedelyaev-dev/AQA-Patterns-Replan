
package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;
import ru.netology.data.DeliveryData;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "http://localhost:9999";
    }

    @Test
    void shouldSuccessfullyReplanDelivery() {

        DeliveryData client = DataGenerator.generateClient();

        String firstDate = DataGenerator.generateDate(4);
        String secondDate = DataGenerator.generateDate(7);

        // Открываем страницу заказа доставки карты
        open("/");

        // Заполняем форму первый раз
        $("[data-test-id='city'] input")
                .setValue(client.getCity());

        setDate(firstDate);

        $("[data-test-id='name'] input")
                .setValue(client.getName());

        $("[data-test-id='phone'] input")
                .setValue(client.getPhone());

        $("[data-test-id='agreement'] .checkbox__box")
                .click();

        // Первое бронирование встречи
        $(byText("Запланировать")).click();

        // Проверяем полное сообщение с первой датой
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText(
                        "Встреча успешно запланирована на " + firstDate
                ));

        // Изменяем только дату встречи
        setDate(secondDate);

        // Повторно отправляем форму
        $(byText("Запланировать")).click();

        // Подтверждаем изменение даты
        $(byText("Перепланировать"))
                .shouldBe(visible, Duration.ofSeconds(15))
                .click();

        // Проверяем полное сообщение с новой датой
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText(
                        "Встреча успешно запланирована на " + secondDate
                ));
    }

    // Вспомогательный метод для изменения даты
    private void setDate(String date) {

        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a"));

        $("[data-test-id='date'] input")
                .sendKeys(Keys.BACK_SPACE);

        $("[data-test-id='date'] input")
                .sendKeys(date);
    }
}
