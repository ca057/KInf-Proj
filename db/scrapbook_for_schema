CREATE SCHEMA presentation;

CREATE TABLE presentation.person(PersonID integer PRIMARY KEY NOT NULL, SeiteOriginal integer, NummerHess integer, Jesuit varchar(40), Adlig varchar(40), OrtsschreibungenID integer NOT NULL, Datum date, Einschreibejahr date, FakultaetID integer NOT NULL, Graduiert varchar(40), TitelID integer NOT NULL, ZusaetzeID integer NOT NULL, FundortID integer NOT NULL, Anmerkung varchar(255));

CREATE TABLE presentation.fakultaten(FakultaetID integer PRIMARY KEY NOT NULL, Fakultaetsname varchar(255));

CREATE TABLE presentation.name_norm(NameNormID integer PRIMARY KEY NOT NULL, name varchar(255));

CREATE TABLE presentation.name_tradierung(NameTradierungID integer PRIMARY KEY NOT NULL, name varchar(255), NameNormID integer, CONSTRAINT NameTrad_NameNorm_FK FOREIGN KEY (NameNormID) REFERENCES name_norm(NameNormID) ON DELETE RESTRICT);
