## 11. Управляем параметрами в коде и в Jenkins.
1. Передаем параметры в код из командной строки
2. Прячем приватные данные с библиотекой owner
3. Передаем параметры из jenkins

## Задание
1. Доработать свой код:

1.1 Передать из дженкинса адрес удаленного браузера

1.2 Спрятать логин/пароль к удаленному браузеру в .properties файл, считывать его нужно в коде с owner



### Проект по тестированию  страниц сайта компании "Т1 Консалтинг", – российский разработчик и интегратор, входит в Группу Т1.
> <a target="_blank" href="https://www.t1-consulting.ru/">Ссылка на главную страницу сайта</a>


### :drop_of_blood: Содержание:

- [Технологии и инструменты](#earth_africa-технологии-и-инструменты)
- [Реализованные проверки](#earth_africa-Реализованные-проверки)
- [Сборка в Jenkins](#earth_africa-Jenkins-job)
- [Запуск из терминала](#earth_africa-Запуск-тестов-из-терминала)
- [Allure отчет](#earth_africa-Allure-отчет)
- [Отчет в Telegram](#earth_africa-Уведомление-в-Telegram-при-помощи-бота)
 
### Проект реализован с использованием

### Список проверок, реализованных в автотестах
- [x] Наличие  заголовка и меню на главной странице
- [x] Наличие заданных пунктов подменю в разделе "О компании" и проверка девиза компании
- [x] Проверка главного меню сайта с переменными названиями разделов меню, их наполнение страниц разделов
- [x] Наличие раздела "О компании" и достоверности раздела меню 
- [x] Проверка  меню и наполнение разделов меню "О компании"
- [x] Проверка наличия и доступности к скачиванию инструкции по эксплуатации системы "T1 Watchman"
- [x] Проверка на наличие ошибок в console log


### For run remote tests need fill remote.properties or to pass value:

* browser (default chrome)
* browserVersion (default 89.0)
* browserSize (default 1920x1080)
* browserMobileView (mobile device name, for example iPhone X)
* remoteDriverUrl (url address from selenoid or grid)
* videoStorage (url address where you should get video)
* threads (number of threads)


Run tests with filled remote.properties:
```bash
gradle clean test
```

 
:heart: <a target="_blank" href="https://qa.guru">qa.guru</a><br/>
:blue_heart: <a target="_blank" href="https://t.me/qa_automation">t.me/qa_automation</a>
