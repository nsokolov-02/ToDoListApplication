Приложение «Список дел» на Java: позволяет вести список задач — добавлять, просматривать, редактировать, отмечать выполненными и удалять.

## Возможности

- Создание задачи
- Просмотр списка задач
- Редактирование задачи
- Отметка задачи как выполненной
- Удаление задачи

## Технологии

- Java [25]
- Maven
- [Spring Boot]
- [Thymeleaf]
- [PostgreSQL]

## Требования

- JDK [25] или новее

## Запуск

1. Клонируйте репозиторий:
   ```
   git clone https://github.com/nsokolov-02/ToDoListApplication.git
   cd ToDoListApplication
   ```
2. Запустите приложение.

   Windows:
   ```
   mvnw.cmd spring-boot:run
   ```
   Linux / macOS:
   ```
   ./mvnw spring-boot:run
   ```
3. Откройте в браузере `http://localhost:8080` (порт по умолчанию).

Запуск из IntelliJ IDEA: откройте проект, найдите класс с `main` в `src/main/java` и запустите его кнопкой ▶.

## Структура проекта

```
ToDoListApplication
├── src/main/java        исходный код
├── src/main/resources   настройки и ресурсы
├── src/test             тесты
└── pom.xml              описание сборки и зависимостей
```

## Автор

[nsokolov-02](https://github.com/nsokolov-02)

