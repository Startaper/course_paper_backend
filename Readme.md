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