
## Начало работы

### Необходимые компоненты

Для локального запуска проекта вам потребуется:

- Java 21
- Gradle 8.x (с Kotlin DSL)
- PostgreSQL
- Docker

### Установка

1. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/RaingGOR/TaskManagementSystems.git
   ```

2. Откройте терминал и перейдите в директорию проекта:

   ```bash
   cd TaskManagementSystems
   ```
3. Запустите докер на вашем компьютере и соберите проект с помощью Gradle:

   ```bash
   ./gradlew clean build
   ```

4. С помощью докера создайте контейнеры, для этого впишите в терминал:

   ```bash
   docker-compose up
   ```
## Использование

После запуска приложения откройте следующий адрес:
#### Swagger : http://localhost:8080/swagger-ui/index.html#/
Вы увидите документацию к Api созданную автоматически, благодаря технологии OpenApi3.

### Тестирование
#### **Аккаунт админа:**
* Логин: admin_user 
* Пароль: useruser
#### **Аккаунт юзера:**
* Логин: regular_user
* Пароль: useruser