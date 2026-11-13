
package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import ru.netology.data.DataGenerator;
import ru.netology.data.DeliveryData;

import java.io.ByteArrayInputStream;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "http://localhost:9999";

        // Подключаем Allure к Selenide
        SelenideLogger.addListener(
                "AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
                        .includeSelenideSteps(true)
        );
    }

    @Test
    void shouldSuccessfullyReplanDelivery() {

        // Генерируем данные клиента
        DeliveryData client = DataGenerator.generateClient();

        // Генерируем первоначальную и новую даты
        String firstDate = DataGenerator.generateDate(4);
        String secondDate = DataGenerator.generateDate(7);

        // Открываем страницу заказа карты
        open("/");

        // Заполняем форму
        $("[data-test-id='city'] input")
                .setValue(client.getCity());

        setDate(firstDate);

        $("[data-test-id='name'] input")
                .setValue(client.getName());

        $("[data-test-id='phone'] input")
                .setValue(client.getPhone());

        // Соглашаемся с условиями обработки данных
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

        // Изменяем дату встречи
        setDate(secondDate);

        // Повторно отправляем форму
        $(byText("Запланировать")).click();

        // Подтверждаем перепланирование
        $(byText("Перепланировать"))
                .shouldBe(visible, Duration.ofSeconds(15))
                .click();

        // Проверяем полное сообщение с новой датой
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText(
                        "Встреча успешно запланирована на " + secondDate
                ));

        // Прикладываем скриншот успешного теста к Allure
        Allure.addAttachment(
                "Успешное перепланирование встречи",
                "image/png",
                new ByteArrayInputStream(
                        screenshot(OutputType.BYTES)
                ),
                ".png"
        );
    }

    // Вспомогательный метод изменения даты
    private void setDate(String date) {

        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a"));

        $("[data-test-id='date'] input")
                .sendKeys(Keys.BACK_SPACE);

        $("[data-test-id='date'] input")
                .sendKeys(date);
    }
}
