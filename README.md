<p align="center">
  <img src="https://thumb.cloud.mail.ru/weblink/thumb/xw1/aray/6orn3HUDy/Logo.jpg" alt="Simplegram"/>
</p>

# Простой мессенджер "Simplegram" (используя Spring)
![Разработка](https://img.shields.io/static/v1?label=Разработка&message=В+процессе&color=red)
![JDK version](https://img.shields.io/static/v1?label=JDK&message=1.8%2B&color=<COLOR>)
![Database](https://img.shields.io/static/v1?label=Database&message=PostgreSQL&color=<COLOR>)
![build](https://img.shields.io/static/v1?label=build&message=Maven&color=<COLOR>)
![SpringBoot](https://img.shields.io/static/v1?label=SpringBoot&message=2.5.3&color=<COLOR>)
![ORM](https://img.shields.io/static/v1?label=ORM&message=Hibernate&color=<COLOR>)
![Frontend](https://img.shields.io/static/v1?label=Frontend&message=Vue&color=<COLOR>)

Простой месседжер с регистрацией/авторизацией. [**В разработке**]

Java, Spring, Hibernate, JWT, Vue.js, VueRouter, Vuetify, Vuex, SockJs, Axios.
 
## _Причины создания_

В целях личного обучения 
 
## _Скриншоты_
[![Simplegram](https://thumb.cloud.mail.ru/weblink/thumb/xw1/aray/6orn3HUDy/Screenshot_1.jpg)]()

[![Simplegram](https://thumb.cloud.mail.ru/weblink/thumb/xw1/aray/6orn3HUDy/Screenshot_7.jpg)]()

[![Simplegram](https://thumb.cloud.mail.ru/weblink/thumb/xw1/aray/6orn3HUDy/Screenshot_2.jpg)]()

[![Simplegram](https://thumb.cloud.mail.ru/weblink/thumb/xw1/aray/6orn3HUDy/Screenshot_3.jpg)]()

## _Краткая сводка_

При регистрации пользователь может загрузить свою аватарку, если он не захочет этого делать, она будет автоматически сгенерирована с использованием сервиса api.multiavatar.com на основе его логина. 

Каждый пользователь должен иметь уникальный логин, но не уникальное имя. 

Присутствует валидация формы при помощи библиотеки (поэтому все предупреждения на англ. языке).

После регистрации создается JWT токен. Данные хранятся в локальном хранилище:

[![Simplegram](https://thumb.cloud.mail.ru/weblink/thumb/xw1/aray/6orn3HUDy/Screenshot_4.jpg)]()

После регистрации (/signup) и авторизации (/signin) пользователь будет переброшен на главную страницу с чатами (/). (Без авторизации он не сможет туда попасть)

**Задачи на текущий момент времени:**

- [X] Авторизация
- [X] Регистрация (Пароль берется из поля "Введите пароль" не проверятся с "Повторите пароль", скоро исправлю)
- [X] Интерфейс при помощи Vuetify
- [X] JWT
- [ ] Отправка сообщений (HTTP POST)
- [ ] Синхронизация сообщений через SockJs
- [ ] Отображение списка контактов(есть просто визуально)
- [ ] Отправка сообщений должна поддерживать загрузку/скачивание файлов (По сути при регистрации я загружаю файл, думаю, в сообщениях будет так же, так что на половину готово)
- [ ] Статус онлайн/последнее время в сети 
- [ ] Возможность создавать группы контактов
- [ ] Перенести БД на Hibernate (случайно начал использвовать Spring Data Jpa)

**Новые задачи (по состоянию на 02.08.21)**
- [X] миграции через liquibase в yml
- [X] insert тоже через миграции
- [X] maven -> gradle
- [ ] models -> entity
- [X] фикс названия пакетов
- [X] lombok
- [ ] ReponseEntity
- [ ] ModelAttribute -> RequestBody
- [X] System.out - @Log4j2
- [ ] spring messagesource
- [ ] все Autowired заменить на private final
- [ ] удалить Role заменить на Enumeratated
- [X] application.properties -> yaml
- [ ] @Value заменить на ApplicationConfiguration
- [ ] кастомные exception + @ExceptionAdvice
- [ ] адвайс для валидации
- [ ] исправить gradle
- [ ] убрать webjars

Сейчас соединение по SockJs устанавливается в тестовом режиме (просто, что оно есть):
[![Simplegram](https://thumb.cloud.mail.ru/weblink/thumb/xw1/aray/6orn3HUDy/Screenshot_5.jpg)]()


Актуальный вопрос с отображением файлов: https://github.com/TesterReality/Simplegram/commit/3ecf4b18c63f53f00fa1678c5be1b48ff4fa1919

## _Участие в разработке_

Для того, чтобы запустить проект, вам необходимо отредактировать файл `application.properties` в директории `src/main/resources`.

Пример для **localhost** :

```
spring.datasource.url=jdbc:postgresql://localhost/searchViazmus
spring.datasource.username=postgres
spring.datasource.password=<Ваш пароль>
spring.jpa.generate-ddl=true

spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto= update

#Наш секретный ключ, которым все шифруется/расшифровывается
simplegram.app.jwtSecret= SimplegramSecretKey
#Срок действия JWT токета (в мс) (1 день)
simplegram.app.jwtExpirationMs= 86400000
#В какую директорию будут загружаться файлы при регистрации
simplegram.app.uploadPath = uploads/
```

После запуска Spring проекта (бек) автоматически создадутся необходимые таблицы. Но данных в них не будет. Вам необходимо их добавить выполнив следующий запрос:
```
INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');
```

По умолчанию tomcat запустит ваше Spring проект на порту 8080 (Приложение будет запущено на http://localhost:8080)

Чтобы указать, на каком порту будет запущен Ваш фронт, Вам нужно отредактировать файл `vue.config.js` в директории `src/frontend/`. (Сейчас стоит 3000 порт)

Перейдите в корневую папку `frontend` (должен содержать `package.json`)(`../Simplegram/src/frontend>`) и выполните через консоль следующие команды:
```
npm install

npm run serve
```
Фронтэнд приложение будет запущено на http://localhost:3000