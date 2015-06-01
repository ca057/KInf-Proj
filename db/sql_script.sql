--CREATE the schemas for the tables

--CREATE SCHEMA hylleblomst;CREATE SCHEMA quelle;CREATE SCHEMA person;CREATE SCHEMA anrede;CREATE SCHEMA ort;CREATE SCHEMA titel;CREATE SCHEMA seminar;CREATE SCHEMA wirtschaft;CREATE SCHEMA zusatz;CREATE SCHEMA vorname;CREATE SCHEMA name;CREATE SCHEMA fach;Fundorte, Fakultäten,CREATE SCHEMA einzeltabellen;

--CREATE tables which do not require foreign key relations
--DROP table hylleblomst.anrede_norm;
CREATE TABLE hylleblomst.anrede_norm(AnredeNormID integer PRIMARY KEY NOT NULL,Name varchar(255));
--DROP table hylleblomst.fach_norm;
CREATE TABLE hylleblomst.fach_norm(FachNormID integer PRIMARY KEY,Name varchar(255));
--DROP table hylleblomst.fakultaeten;
CREATE TABLE hylleblomst.fakultaeten(FakultaetID integer PRIMARY KEY NOT NULL, Fakultaetsname varchar(255));
--DROP table hylleblomst.fundorte;
CREATE TABLE hylleblomst.fundorte(FundorteID integer PRIMARY KEY NOT NULL,Name varchar(255));
--DROP table hylleblomst.name_norm;
CREATE TABLE hylleblomst.name_norm(NameNormID integer PRIMARY KEY NOT NULL, Name varchar(255));
--DROP table hylleblomst.ort_abweichung_norm;
CREATE TABLE hylleblomst.ort_abweichung_norm(AbweichungNormID integer PRIMARY KEY NOT NULL,Name varchar(255),Anmerkung varchar(255));
--DROP table hylleblomst.quellen;
CREATE TABLE hylleblomst.quellen(QuellenID integer PRIMARY KEY NOT NULL, QuellenName varchar(255));
--DROP table hylleblomst.seminar_norm;
CREATE TABLE hylleblomst.seminar_norm(SeminarNormID integer PRIMARY KEY NOT NULL, Seminar varchar(255));
--DROP table hylleblomst.titel_norm;
CREATE TABLE hylleblomst.titel_norm(TitelNormID integer PRIMARY KEY NOT NULL,Name varchar(255));
--DROP table hylleblomst.wirtschaftslage_norm;
CREATE TABLE hylleblomst.wirtschaftslage_norm(WirtschaftslageNormID integer PRIMARY KEY NOT NULL, Name varchar(255));

--CREATE tables which do require foreign key relations, cautious about order in which these are CREATEd
--DROP table hylleblomst.anrede_trad;
CREATE TABLE hylleblomst.anrede_trad(AnredeTradID integer PRIMARY KEY NOT NULL,Name varchar(255),AnredeNormID integer NOT NULL, 
	CONSTRAINT AnredeTrad_AnredeNorm_FK FOREIGN KEY (AnredeNormID) references hylleblomst.anrede_norm(AnredeNormID) ON DELETE RESTRICT ON UPDATE RESTRICT);

--CREATE table for person	
--DROP table hylleblomst.person;
CREATE TABLE hylleblomst.person(
	PersonID integer PRIMARY KEY NOT NULL,	SeiteOriginal integer,	NummerHess integer,	Jesuit varchar(100), Adlig varchar(100), Datum date, Einschreibejahr date, Graduiert varchar(100), Anmerkung varchar(255),
	AnredeID integer NOT NULL, FakultaetID integer NOT NULL, FundortID integer NOT NULL, TitelID integer NOT NULL,	
	CONSTRAINT Person_Anrede_FK FOREIGN KEY (AnredeID) REFERENCES hylleblomst.Anrede_Trad(AnredeTradID) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT Person_Fakultat_FK FOREIGN KEY (FakultaetID) REFERENCES hylleblomst.fakultaeten(FakultaetID) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT Person_Fundort_FK FOREIGN KEY (FundortID) REFERENCES HYLLEBLOMST.FUNDORTE(FundorteID) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT Person_Titel_FK FOREIGN KEY (TitelID) REFERENCES HYLLEBLOMST.Titel_Norm(TitelNormID) ON DELETE RESTRICT ON UPDATE RESTRICT);

--DROP table hylleblomst.fach_trad;	
CREATE TABLE hylleblomst.fach_trad(FachTradID integer PRIMARY KEY NOT NULL, Name varchar(255), FachNormID integer NOT NULL,
	CONSTRAINT FachTrad_FachNorm_FK FOREIGN KEY (FachNormID) REFERENCES hylleblomst.fach_norm(FachNormID) ON DELETE RESTRICT ON UPDATE RESTRICT);	
--DROP table hylleblomst.fach_info;
CREATE TABLE hylleblomst.fach_info(FachTradID integer NOT NULL,	PersonID integer NOT NULL, QuellenID integer NOT NULL,
	CONSTRAINT FachInfo_FachTrad_FK FOREIGN KEY (FachTradID) REFERENCES HYLLEBLOMST.FACH_TRAD(FachTradID) ON DELETE RESTRICT ON UPDATE RESTRICT,	CONSTRAINT FachInfo_Person_FK FOREIGN KEY (PersonID) REFERENCES HYLLEBLOMST.Person(PersonID) ON DELETE RESTRICT ON UPDATE RESTRICT,	CONSTRAINT FachInfo_Quelle_FK FOREIGN KEY (QuellenID) REFERENCES HYLLEBLOMST.Quellen(QuellenID) ON DELETE RESTRICT ON UPDATE RESTRICT);
--DROP table hylleblomst.name_trad;
CREATE TABLE hylleblomst.name_trad(NameTradID integer PRIMARY KEY NOT NULL, NameTrad varchar(255), NameNormID integer NOT NULL, 
	CONSTRAINT NameTrad_NameNorm_FK FOREIGN KEY (NameNormID) REFERENCES hylleblomst.name_norm(NameNormID) ON DELETE RESTRICT ON UPDATE RESTRICT);
--DROP table hylleblomst.name_info;
CREATE TABLE hylleblomst.name_info(QuellenID integer NOT NULL,	NameTradID integer NOT NULL, PersonID integer NOT NULL,
	CONSTRAINT NameInfo_Quelle_FK FOREIGN KEY (QuellenID) REFERENCES hylleblomst.Quellen(QuellenID) ON DELETE RESTRICT	ON UPDATE RESTRICT,	CONSTRAINT NameInfo_NameTrad_FK	FOREIGN KEY (NameTradID) REFERENCES hylleblomst.Name_Trad(NameTradID) ON DELETE RESTRICT ON UPDATE RESTRICT, CONSTRAINT NameInfo_Person_FK FOREIGN KEY (PersonID) REFERENCES hylleblomst.Person(PersonID) ON DELETE RESTRICT ON UPDATE RESTRICT);
--DROP table hylleblomst.ort_norm;
CREATE TABLE hylleblomst.ort_norm(OrtNormID integer PRIMARY KEY NOT NULL, OrtNorm varchar(255), OrtAbweichungID integer NOT NULL,
	CONSTRAINT OrtNorm_OrtAbweichung_FK FOREIGN KEY (OrtAbweichungID) REFERENCES HYLLEBLOMST.ORT_ABWEICHUNG_NORM(AbweichungNormID) ON DELETE RESTRICT ON UPDATE RESTRICT);
--DROP TABLE hylleblomst.ort_trad;
CREATE TABLE hylleblomst.ort_trad(OrtTradID integer PRIMARY KEY NOT NULL,Schreibweise varchar(255),OrtNormID integer NOT NULL,
	CONSTRAINT OrtTrad_OrtNorm_FK FOREIGN KEY (OrtNormID) REFERENCES hylleblomst.ort_norm(OrtNormID) ON DELETE RESTRICT ON UPDATE RESTRICT);
--DROP TABLE hylleblomst.ort_info;
CREATE TABLE hylleblomst.ort_info(	OrtTradID integer NOT NULL,	PersonID integer NOT NULL,	QuellenID integer NOT NULL,	
	CONSTRAINT OrtInfo_OrtTrad_FK FOREIGN KEY (OrtTradID) REFERENCES hylleblomst.ort_trad(OrtTradID) ON DELETE RESTRICT ON UPDATE RESTRICT,	CONSTRAINT OrtInfo_PersonID_FK FOREIGN KEY (PersonID) REFERENCES hylleblomst.person(PersonID)  ON DELETE RESTRICT ON UPDATE RESTRICT, CONSTRAINT OrtInfo_QuellenID_FK FOREIGN KEY (QuellenID) REFERENCES hylleblomst.quellen(QuellenID) ON DELETE RESTRICT ON UPDATE RESTRICT);	
--DROP table hylleblomst.seminar_trad;
CREATE TABLE hylleblomst.seminar_trad(SeminarTradID integer PRIMARY KEY NOT NULL, Schreibweise varchar(255), SeminarNormID integer NOT NULL,
	CONSTRAINT SeminarTrad_SeminarNorm_FK FOREIGN KEY (SeminarNormID) REFERENCES hylleblomst.seminar_norm(SeminarNormID) ON DELETE RESTRICT ON UPDATE RESTRICT);
--DROP table hylleblomst.seminar_info;
CREATE TABLE hylleblomst.seminar_info(QuellenID integer NOT NULL, SeminarID integer NOT NULL, PersonID integer NOT NULL,
	CONSTRAINT SeminarInfo_Quelle_FK FOREIGN KEY (QuellenID) REFERENCES hylleblomst.QUELLEN(QuellenID) ON DELETE RESTRICT ON UPDATE RESTRICT, CONSTRAINT SeminarInfo_SeminarTrad_FK FOREIGN KEY (SeminarID) REFERENCES hylleblomst.SEMINAR_TRAD(SeminarTradID) ON DELETE RESTRICT ON UPDATE RESTRICT, CONSTRAINT SeminarInfo_Person_FK FOREIGN KEY (PersonID) REFERENCES hylleblomst.PERSON(PersonID) ON DELETE RESTRICT ON UPDATE RESTRICT);
--DROP table hylleblomst.titel;
CREATE table hylleblomst.titel(TitelID integer PRIMARY KEY NOT NULL,Titel varchar(255),TitelNormID integer NOT NULL,
	CONSTRAINT Titel_TitelNorm_FK FOREIGN KEY (TitelNormID) REFERENCES HYLLEBLOMST.TITEL_NORM(TitelNormID) ON DELETE RESTRICT ON UPDATE RESTRICT);
--DROP table hylleblomst.wirtschaftslage_trad;
CREATE TABLE hylleblomst.wirtschaftslage_trad(WirtschaftslageTradID integer PRIMARY KEY NOT NULL, Name varchar(255), WirtschaftslageNormID integer NOT NULL,
	CONSTRAINT WirtschaftslageTrad_WirtschaftslageNorm_FK FOREIGN KEY (WirtschaftslageNormID) REFERENCES HYLLEBLOMST.WIRTSCHAFTSLAGE_NORM(WirtschaftslageNormID) ON DELETE RESTRICT ON UPDATE RESTRICT);
--DROP table hylleblomst.wirtschaftslage_info;
CREATE TABLE hylleblomst.wirtschaftslage_info(WirtschaftslageID integer NOT NULL, PersonID integer NOT NULL, QuellenID integer NOT NULL,
	CONSTRAINT WirtschaftslageInfo_Wirtschaftslage_FK FOREIGN KEY (WirtschaftslageID) REFERENCES HYLLEBLOMST.Wirtschaftslage_TRAD(WirtschaftslageTradID) ON DELETE RESTRICT ON UPDATE RESTRICT,	CONSTRAINT WirtschaftslageInfo_Person_FK FOREIGN KEY (PersonID) REFERENCES HYLLEBLOMST.Person(PersonID) ON DELETE RESTRICT ON UPDATE RESTRICT, CONSTRAINT WirtschaftslageInfo_Quellen_FK FOREIGN KEY (QuellenID) REFERENCES HYLLEBLOMST.Quellen(QuellenID) ON DELETE RESTRICT ON UPDATE RESTRICT);
--DROP table hylleblomst.zusaetze;	
CREATE TABLE hylleblomst.zusaetze(ZusaetzeID integer PRIMARY KEY NOT NULL,Inhalt varchar(255),QuellenID integer NOT NULL,
	CONSTRAINT Zusaetze_Quelle_FK FOREIGN KEY (QuellenID) REFERENCES hylleblomst.Quellen(QuellenID) ON DELETE RESTRICT ON UPDATE RESTRICT);
--DROP TABLE HYLLEBLOMST.zusaetze_info;
CREATE TABLE hylleblomst.zusaetze_info(ZusaetzeID integer NOT NULL, PersonID integer NOT NULL, QuellenID integer NOT NULL,	
	CONSTRAINT ZusaetzeInfo_Zusaetze_FK FOREIGN KEY (ZusaetzeID) REFERENCES HYLLEBLOMST.ZUSAETZE(ZusaetzeID) ON DELETE RESTRICT ON UPDATE RESTRICT,	CONSTRAINT ZusaetzeInfo_Person_FK FOREIGN KEY (ZusaetzeID) REFERENCES HYLLEBLOMST.Person(PersonID) ON DELETE RESTRICT ON UPDATE RESTRICT, CONSTRAINT ZusaetzeInfo_Quellen_FK FOREIGN KEY (QuellenID) REFERENCES HYLLEBLOMST.Quellen(QuellenID) ON DELETE RESTRICT ON UPDATE RESTRICT);


	