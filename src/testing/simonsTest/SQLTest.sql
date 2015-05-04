CREATE schema studenten;

CREATE table studenten.person ( id integer PRIMARY KEY NOT NULL, name varchar(20));

INSERT INTO studenten.person VALUES (1, 'Johannes');

SELECT * FROM studenten.person;
