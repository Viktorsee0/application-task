INSERT INTO PROJECTS (NAME, DESCRIPTION)
VALUES ('PROJECT1', 'PROJECT1'),
       ('PROJECT2', 'PROJECT2'),
       ('PROJECT3', 'PROJECT3'),
       ('PROJECT4', 'PROJECT4');

INSERT INTO USERS (FIRSTNAME, LASTNAME, EMAIL, PROJECT_ID)
VALUES ('USER1', 'USER1', 'USER1@gmail.com', 1),
       ('USER2', 'USER2', 'USER2@gmail.com', null),
       ('USER3', 'USER3', 'USER3@gmail.com', 2),
       ('USER4', 'USER4', 'USER4@gmail.com', null),
       ('USER5', 'USER5', 'USER5@gmail.com', 3),
       ('USER6', 'USER6', 'USER6@gmail.com', 4);

INSERT INTO TASKS (title, DESCRIPTION, CREATED_DATE,
                   DUE_DATE, STATUS, ASSIGNEE_ID, PROJECT_ID)
VALUES ('TASK1', 'create 1', '2023-10-19 13:52:29.697271 +00:00', '2023-10-19 13:52:29.697271 +00:00', 'IN PROGRESS', 1,
        1),
       ('TASK2', 'create 2', '2023-10-19 13:52:29.697271 +00:00', '2023-10-19 13:52:29.697271 +00:00', 'IN PROGRESS', 2,
        1),
       ('TASK3', 'create 3', '2023-10-19 13:52:29.697271 +00:00', '2023-10-19 13:52:29.697271 +00:00', 'IN PROGRESS', 3,
        2),
       ('TASK4', 'create 4', '2023-10-19 13:52:29.697271 +00:00', '2023-10-19 13:52:29.697271 +00:00', 'IN PROGRESS',
        null, null);
