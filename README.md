# Sharing

# Инструкция по получению информации о дисках

+ [{url: '/user/disks/all', method: 'GET'}] — список дисков пользователя
+ [{url: '/user/disks/free', method: 'GET'}] — список свободных дисков (которые можно взять)
+ [{url: '/user/disks/taken/by_me', method: 'GET'}] — список дисков, взятых пользователем
+ [{url: '/user/disks/taken/from_me', method: 'GET'}] — список дисков, взятых у пользователя (с указанием, кто взял)

## Инструкция по обменным операциям

+ [{url: '/user/disk/take/{diskId}', method: 'PUT'}] — взять диск взаймы
+ [{url: '/user/disk/return/{diskId}}', method: 'PUT'}] — вернуть диск, который был взят ранее

## Инструкция по развёртыванию

### Сборка
Для сборки нужны JDK 11+, Maven 3.x+.
Команда для сборки, из корня проекта: `mvn install`

### Запуск

#### Внешние зависимости

JDK 11+,
PostgreSQL 11+

#### Конфигурация

Для соединения с БД нужно задать параметры :

CONNECTION к БД — --spring.datasource.url=`value`,
***
DB_USERNAME — --spring.datasource.username=`value`
***
DB_PASSWORD — --spring.datasource.password=`value`

По умолчанию приложение соединяется с `localhost:5432, DB_NAME=sharing, DB_USERNAME=postgres, DB_PASSWORD=1234`

Команда для запуска

`java -jar PATH_TO_JAR --param1=value --param2=value ...`

#### Установка
Перед первым запуском приложения нужно выполнить команду `CREATE DATABASE <database_name>;`
