# Spring-framework
Изучение основ Spring framework и реализация трех программ: с использованием .xml файла, механизма JdbcTemplate и конфигурации Java (настройка с помощью аннотаций).

## Introduction
- Для написания программ использовалась версия Java 8.
- Отладка кода воспроизводилась на Intellij IDEA CE.
- Правила форматирования кода соответствуют общепринятым стандартам [Oracle](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html).
- Использованы зависимости и плагины для обеспечения корректной работы:
    - spring-context
    - spring-jdbc
    - spring-core
    - postgresql
    - HikariCP
    - junit-jupiter-engine
    - junit-jupiter-params
    - junit-jupiter-api
    - hsqldb

## Contents
1. [Exercise 00](#exercise-00)
2. [Exercise 01](#exercise-01)
3. [Exercise 02](#exercise-02)

### Exercise 00

- Программа расположена в директории: ex00;
- Корневая папка проекта: Spring.

Реализована слабосвязанная система, состоящая из набора компонентов и соответствующая принципам IoC/DI.

Написан интерфейс Printer, предназначенный для отображения определенного сообщения и имеющий две реализации PrinterWithDateTimeImpl и PrinterWithPrefixImpl. 

PrinterWithDateTimeImpl выводит сообщения, указывая дату/время вывода с помощью LocalDateTime, а PrinterWithPrefixImpl можно использовать для установки текстового префикса для сообщения.

В свою очередь, обе реализации принтера зависят от интерфейса Renderer, который отправляет сообщения на консоль. Renderer также имеет две реализации: RendererStandardImpl (выводит сообщение через стандартный System.out) и RendererErrImpl (выводит сообщения через System.err).

Реализации Renderer также зависит от интерфейса PreProcessor, который предварительно обрабатывает сообщения. Реализация PreProcessorToUpperImpl переводит все буквы в верхний регистр, а реализация PreProcessorToLower переводит все буквы в нижний регистр.

Описан файл context.xml для Spring, где указаны все настройки для каждого компонента и связи между ними.

UML-диаграмма классов показана ниже:

![Diagram of classes](misc/images/Diagram.png)

Ниже представлен код, использующий эти классы стандартным образом и Spring с помощью context.xml:

```java
public class Program {
    public static void main(String[] args) {
        System.out.println("-------------Standard way------------");
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererStandardImpl(preProcessor);
        PrinterWithDateTimeImpl printer = new PrinterWithDateTimeImpl(renderer);
        printer.print("Hello!");

        System.out.println("-------------Spring looks------------");
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Printer printerSpring = context.getBean("printPrefixErrLow", Printer.class);
        printerSpring.print("Hello");
    }
}
```
Запуск этого кода даст следующий результат:

```
-------------Standard way------------
(Current time) HELLO!
-------------Spring looks------------
prefix hello
```

### Exercise 01

- Программа расположена в директории: ex01;
- Корневая папка проекта: Service-folder.

В данном проекте реализована модель User со следующими полями:
- Identifier
- Email

Реализован интерфейс CrudRepository<T> со следующими методами:
- `Optional<T>` findById(Long id)
- `List<T>` findAll()
- void save(T entity)
- void update(T entity)
- void delete(Long id)

Интерфейс UsersRepository, объявленный как UsersRepository, расширяет CrudRepository<User>, содержит следующий метод:
- `Optional<T>` findByEmail(String email)

Кроме того, созданы две реализации UsersRepository:<br> UsersRepositoryJdbcImpl (использует стандартные механизмы Statements) и UsersRepositoryJdbcTemplateImpl (основан на NamedParameterJdbcTemaple). Оба класса принимают объект DataSource в качестве аргумента конструктора. 

В файле context.xml объявлены bean-компоненты для обоих типов репозитория с разными идентификаторами, а также два bean-компонента типа DataSource: DriverManagerDataSource и HikariDataSource.

Данные для подключения к БД указаны в файле db.properties и включены в context.xml с помощью заполнителей `${db.url}`.

В классе Main работа методов продемонстрирована с использованием обоих репозиториев.

**Структура проекта**:
- Service
    - src
        - main
            - java
                - school21.spring.service
                    - models
                        - User
                    - repositories
                        - CrudRepository
                        - UsersRepository
                        - UsersRepositoryJdbcImpl
                        - UsersRepositoryJdbcTemplateImpl
                    - application
                        - Main
            - resources
                -	db.properties
                -	context.xml
                -   schema.sql
    -	pom.xml

### Exercise 02

- Программа расположена в директории: ex02;
- Корневая папка проекта: Service.

Проект основан на настройке Spring-приложения с помощью аннотаций. Для этого был реализован класс ApplicationConfig, отмеченный как @Configuration. Внутри этого класса описаны bean-компоненты для подключения к базе данных DataSource с помощью аннотации @Bean. Как и в предыдущей задаче, данные о подключении находятся внутри файла db.properties.

Реализованы интерфейс/класс UsersService/UsersServiceImpl с зависимостью от объявленного в ней UsersRepository. Вставка правильного компонента репозитория реализована с использованием аннотации @Autowired. Коллизии при автоматической привязке разрешаются с помощью аннотации @Qualifier.

Компоненты для UsersService и UsersRepository определяются с использованием аннотации @Component.

В UsersServiceImpl реализован метод String signUp(String email), который регистрирует нового пользователя и сохраняет его данные в БД. Этот метод возвращает временный пароль, назначенный пользователю системой.

Для проверки создан интеграционный тест для UsersServiceImp с использованием базы данных в памяти (HSQLDB). Конфигурация контекста для тестовой среды (DataSource для базы данных в памяти) описана в отдельном классе TestApplicatoinConfig. Этот тест проверяет был ли возвращен временный пароль в методе signUp(String email).

**Структура проекта**:
- Service
    - src
        - main
            - java
                - school21.spring.service
                    - config
                        - ApplicationConfig
                    - models
                        - User
                    - repositories
                        - CrudRepository
                        - UsersRepository
                        - UsersRepositoryJdbcImpl
                        - UsersRepositoryJdbcTemplateImpl
                    - services
                        - UsersService
                        - UsersServiceImpl
                    - application
                        - Main
            - resources
                -	db.properties
        - test
            - java
                - school21.spring.service
                    - config
                        - TestApplicationConfig
                    - services
                        - UsersServiceImplTest
    -	pom.xml
