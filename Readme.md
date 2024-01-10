В данном репозитории хранится серверная часть моего проекта.

Курсовая работа на тему "Кросс-платформенная система хранения и работы с данными резюме соискателей, осуществляющая парсинг резюме пользователей, передаваемых в json-формате и хранение полученных данных в реляционной БД."

Современные технологии программирования, 3 курс

РЕЛИЗЫ

Обновление от 07.01.2024 - версия №1.1.0
Выполнены следующие работы:
1) Улучшен способ хранения данных в БД.
2) Созданы необходимые exceptions.
3) Созданы два интерфейса работы с API - интерфейс Админа(AdminService) и Менеджера (MainService).
4) Реализована проверка существования соискателя в базе. Если в БД есть схождение, то к "профилю" соискателя добавляется новое резюме. Иначе создается новый соискатель в БД.
   Соискатель в БД ищется по следующим полям - Фамилия, имя, отчество и дата рождения.
   P.S. 4 пункт пока несовершенен, пока что в поисках лучше варианта поиска в базе соискателя. В планах изменить этот метод на поиск по номеру телефона или email.

Список дел, которые осталось сделать по данному проекту указаны в файле TODO

####################

Обновление от 07.01.2024 - версия №1.1.1
Внесены важные исправления в структуру кода. Также вылечены найденные баги в коде программы.

Список дел, которые осталось сделать по данному проекту указаны в файле TODO

####################

Обновление от 08.01.2024 - версия №1.2.0
Внесены важные исправления в структуру кода. Также вылечены найденные баги в коде программы.

Улучшен способ хранения резюме и соискателей в БД. В прошлом релизе был баг - при повторной отправке одного и того же запроса на создание резюме или соискателя в БДб сервис все обрабатывал. В связи с чем в БД наблюдались дубли.
Теперь передаваемый id резюме в структуре json сохраняется в БД как externalId и по нему в дальнейшем будет поиск на дубль в БД. Также для соискателя системой генерируется специальный externalId по следующему шаблону - "Возраст_ФИО" - фио указывается полностью.

Список дел, которые осталось сделать по данному проекту указаны в файле TODO

####################

Обновление от 10.01.2024 - версия №1.3.0
Внесены важные исправления в структуру кода. Также вылечены найденные баги в коде программы.

Настроена безопасность. Тестируется.
Реализован метод выдачи списка резюме по определенным фильтрам.

Список дел, которые осталось сделать по данному проекту указаны в файле TODO