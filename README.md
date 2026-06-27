# Project AviaTicketsSearcher

**Система для поиска авиабилетов через Telegram.**  
Проект, разработанный для удобного подбора авиабилетов через телеграм.

---

## О проекте

AviaTicketsSearcher — это проект, который решает простую задачу: пользователь отправляет запрос в Telegram-бот, а система находит билеты через внешние API-сервисы (Aviasales, Skyscanner и подобные).

Проект состоит из трёх независимых модулей, каждый из которых лежит в своём репозитории:

| Модуль | Назначение | Репозиторий |
| :--- | :--- | :--- |
| **API** | Ядро. Принимает запросы, управляет парсерами, хранит данные в БД. | [AviaTicketsSearcherAPI](https://github.com/haruHishiro/AviaTicketsSearcherAPI) |
| **Parser** | Исполнитель. Ходит во внешние авиа-API, парсит ответы и возвращает результат. Если у сайта есть апи - использует апи. Нет - парсит. 1 сайт - 1 сборщик информации. | [AviaTicketsSearcherParser](https://github.com/haruHishiro/AviaTicketsSearcherParser) |
| **Bot** | Telegram-шлюз. Принимает сообщения от пользователей и отправляет им готовые билеты. | [AviaTicketsSearcherBot](https://github.com/haruHishiro/AviaTicketsSearcherBot) |

---

## Архитектура (общая схема)

Система построена по **микросервисному принципу** с возможностью горизонтального масштабирования парсеров:

```

Пользователь (Telegram) → Telegram Bot → внутреннее API → Parser Core → Внешние авиа-сайты (API внешних авиа-сайтов)
↑                                  ↓
└──────────── Результат ──────────┘

```

**Поток данных:**
1. Пользователь пишет боту: *"Москва → Лондон, 1 июля"*.
2. Бот преобразует сообщение в JSON и отправляет в API.
3. API сохраняет запрос в БД (статус: *"в обработке"*) и передаёт задачу в Parser Core.
4. Parser Core определяет, с какого сайта брать билеты, и отправляет HTTP-запрос во внешний API (Aviasales / Skyscanner и прочие).
5. Парсер получает JSON, преобразует его в единый внутренний формат далее передает в API.
6. API обновляет статус в БД и отправляет уведомление в Bot.
7. Бот присылает пользователю готовый список билетов.

Подробная схема с описанием всех слоёв и компонентов находится в документации каждого модуля.

---

## Быстрый старт (запуск всей системы)

### 1. СКлонируйте все три репозитория
```bash
git clone https://github.com/haruHishiro/AviaTicketsSearcherAPI.git
git clone https://github.com/haruHishiro/AviaTicketsSearcherParser.git
git clone https://github.com/haruHishiro/AviaTicketsSearcherBot.git
```

2. Настройка переменных окружения

В каждом модуле необходимо создать файл .env или указать параметры в application.properties:

· API: URL для подключения к PostgreSQL и адрес Parser'а.
· Parser: API-ключи для Aviasales / Skyscanner.
· Bot: Токен Telegram-бота.


3. Запуск через Docker

В корневой папке необходимо создать docker-compose.yml затем выполнить:

```bash
docker-compose up -d
```

Все три сервиса поднимутся вместе с БД и будут готовы к работе.

---

## Общий стек технологий

Компонент Технология
Язык Java 17
Фреймворк Spring Boot 3
БД PostgreSQL 15
Сборка Maven / Gradle
Контейнеризация Docker, Docker Compose
Внешние API: Aviasales API (REST, JSON)


---

## Структура репозиториев

```
AviaTicketsSearcherAPI/
├── src/main/java/com/aviasearcher/
│   ├── controller/     # REST-контроллеры
│   ├── service/        # Бизнес-логика
│   ├── repository/     # Доступ к БД (Spring Data JPA)
│   ├── model/          # JPA-сущности
│   ├── dto/            # Объекты для передачи данных
│   └── config/         # Конфигурации (Swagger, CORS, etc.)
├── src/test/           # Юнит- и интеграционные тесты
├── Dockerfile
├── docker-compose.yml  # Для локального запуска с БД
└── README.md

AviaTicketsSearcherParser/
├── src/main/java/com/aviasearcher.parser/
│   ├── client/         # HTTP-клиенты для внешних API
│   ├── service/        # Логика парсинга
│   └── model/          # DTO и внешние JSON-модели
├── src/test/           # Тесты с WireMock
├── Dockerfile
└── README.md

AviaTicketsSearcherBot/
├── src/main/java/com/aviasearcher.bot/
│   ├── listener/       # Обработка сообщений от Telegram
│   ├── service/        # Преобразование запросов в DTO
│   └── client/         # HTTP-клиент для связи с API
├── Dockerfile
└── README.md
```

---

Запуск всех тестов в каждом модуле:

```bash
./mvnw test
```

---

## Архитектурные решения

1. Микросервисы: Каждый модуль решает ровно одну задачу (Single Responsibility Principle). С целью упростить масштабирование и поддержку.
2. Горизонтальное масштабирование парсеров: Для добавления нового сайта достаточно создать новый парсер (отдельный микросервис), не меняя API.
3. Асинхронность: Запросы пользователей обрабатываются асинхронно — пользователь не ждёт, пока парсер сходит на сайт.

---

## Лицензия

Проект создан в целях презентации навыков. Использование внешних API (Aviasales, Skyscanner) регулируется их условиями. Для коммерческого использования необходимо приобрести соответствующие лицензии.

---

## Контакты

Автор: Константин Панов
GitHub: haruHishiro
Mail: k.d.panov@gmail.com