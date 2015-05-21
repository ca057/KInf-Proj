CREATE SCHEMA hylleblomst;

CREATE TABLE hylleblomst.quellen(QuellenID integer PRIMARY KEY NOT NULL, QuellenName varchar(255));

CREATE TABLE hylleblomst.fakultaten(FakultaetID integer PRIMARY KEY NOT NULL, Fakultaetsname varchar(255));
CREATE TABLE hylleblomst.name_norm(NameNormID integer PRIMARY KEY NOT NULL, Name varchar(255));
CREATE TABLE hylleblomst.seminar_norm(SeminarNormID integer PRIMARY KEY NOT NULL, Seminar varchar(255));

CREATE TABLE hylleblomst.person(PersonID integer PRIMARY KEY NOT NULL, FakultaetID integer NOT NULL,
	CONSTRAINT Person_Fakultat_FK FOREIGN KEY (FakultaetID) REFERENCES hylleblomst.FAKULTATEN(FakultaetID) ON DELETE RESTRICT ON UPDATE RESTRICT);
CREATE TABLE hylleblomst.seminar_trad(SeminarTradID integer PRIMARY KEY NOT NULL, Schreibweise varchar(255), SeminarNormID integer NOT NULL,
	CONSTRAINT SeminarTrad_SeminarNorm_FK FOREIGN KEY (SeminarNormID) REFERENCES hylleblomst.seminar_norm(SeminarNormID) ON DELETE RESTRICT ON UPDATE RESTRICT);
CREATE TABLE hylleblomst.name_trad(NameTradID integer PRIMARY KEY NOT NULL, NameTrad varchar(255), NameNormID integer NOT NULL, 
	CONSTRAINT NameTrad_NameNorm_FK FOREIGN KEY (NameNormID) REFERENCES hylleblomst.name_norm(NameNormID) ON DELETE RESTRICTON UPDATE RESTRICT);
CREATE TABLE hylleblomst.seminar_info(SeminarSchreibungenID integer PRIMARY KEY NOT NULL, QuellenID integer NOT NULL, SeminarTradID integer NOT NULL,
	CONSTRAINT SeminarInfo_Quelle_FK FOREIGN KEY (QuellenID) REFERENCES hylleblomst.QUELLEN(QuellenID) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT SeminarInfo_SeminarTrad_FK FOREIGN KEY (SeminarTradID) REFERENCES hylleblomst.SEMINAR_TRAD(SeminarTradID) ON DELETE RESTRICT ON UPDATE RESTRICT);
CREATE TABLE hylleblomst.name_info(NameInfoID integer PRIMARY KEY NOT NULL, QuellenID integer NOT NULL,	NameTradID integer NOT NULL, PersonID integer NOT NULL,
	CONSTRAINT NameInfo_Quelle_FK FOREIGN KEY (QuellenID) REFERENCES hylleblomst.Quellen(QuellenID) ON DELETE RESTRICT	ON UPDATE RESTRICT,
	CONSTRAINT NameInfo_NameTrad_FK	FOREIGN KEY (NameTradID) REFERENCES hylleblomst.Name_Trad(NameTradID) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT NameInfo_Person_FK FOREIGN KEY (PersonID) REFERENCES hylleblomst.Person(PersonID) ON DELETE RESTRICT ON UPDATE RESTRICT);
