# Day Travel

Проект для тестирования сервиса для покупки тура "Путешествие дня".

## Начало работы

1. Клонировать репозиторий https://github.com/ViktoriaGuschina/testing-tour-purchase-service командой git clone.
2. Открыть проект в IntelliJ IDEA Community Edition 2024.2.1 или IntelliJ IDEA Ultimate.

## Prerequisites

**Установить следующие приложения:**

1. IntelliJ IDEA Community Edition 2024.2.1.
2. Google Chrome браузер.
3. Docker Desktop.
4. DBeaver.
5. aqa-shop.jar.

## Запуск SUT

1. Открыть проект в IntelliJ IDEA Ultimate.
2. Запустить контейнеры БД командой docker-compose up -d.
3. Ввести в терминале команду "java -jar artifacts/aqa-shop.jar".
4. Открыть в браузере адрес "localhost:8085".

## Запуск тестов и отчетов Allure

1. В терминале ввести команду ./gradlew clean test.
2. В терминале ввести команду ./gradlew allureReport.

## Документация

* docs/Plan.md 
* docs/allureReport.png