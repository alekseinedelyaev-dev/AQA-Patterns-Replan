
# Домашнее задание «2.3. Patterns»

## Задача № 1. Заказ доставки карты (изменение даты)

![Java CI with Gradle](https://github.com/alekseinedelyaev-dev/AQA-Patterns-Replan/actions/workflows/gradle.yml/badge.svg)

### Описание проекта

Автоматизированное тестирование формы заказа доставки банковской карты с возможностью перепланирования встречи.

### Используемые технологии

- Java 17
- Gradle
- JUnit 5
- Selenide
- Java Faker
- Lombok
- GitHub Actions

### Реализованный сценарий

1. Генерация тестовых данных клиента.
2. Первоначальное бронирование встречи.
3. Проверка успешного бронирования.
4. Изменение даты встречи с сохранением остальных данных.
5. Повторная отправка формы.
6. Подтверждение перепланирования.
7. Проверка успешного изменения даты встречи.

### Запуск приложения

```bash
java -jar ./artifacts/app-replan-delivery.jar
```

Приложение доступно по адресу:

http://localhost:9999

### Запуск автотестов

Windows:

```powershell
.\gradlew.bat test "-Dselenide.headless=true" --info
```

Linux:

```bash
bash ./gradlew test --info -Dselenide.headless=true
```

### Результаты

Автотесты успешно выполнены локально и в GitHub Actions.
