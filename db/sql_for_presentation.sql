CREATE SCHEMA presentation;

CREATE TABLE presentation.quellen(QuellenID integer PRIMARY KEY NOT NULL, QuellenName varchar(255));

CREATE TABLE presentation.fakultaten(FakultaetID integer PRIMARY KEY NOT NULL, Fakultaetsname varchar(255));
CREATE TABLE presentation.name_norm(NameNormID integer PRIMARY KEY NOT NULL, Name varchar(255));
CREATE TABLE presentation.seminar_norm(SeminarNormID integer PRIMARY KEY NOT NULL, Seminar varchar(255));


CREATE TABLE presentation.seminar_trad(
	SeminarTradID integer PRIMARY KEY NOT NULL, 
	Schreibweise varchar(255), 
	SeminarNormID integer NOT NULL,
	
	CONSTRAINT SeminarTrad_SeminarNorm_FK
	FOREIGN KEY (SeminarNormID)
	REFERENCES presentation.seminar_norm(SeminarNormID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT
);

CREATE TABLE presentation.name_trad(
	NameTradID integer PRIMARY KEY NOT NULL, 
	NameTrad varchar(255), 
	NameNormID integer NOT NULL, 
	
	CONSTRAINT NameTrad_NameNorm_FK 
	FOREIGN KEY (NameNormID) 
	REFERENCES presentation.name_norm(NameNormID) 
	ON DELETE RESTRICT
	ON UPDATE RESTRICT
);

CREATE TABLE presentation.seminar_info(
	SeminarSchreibungenID integer PRIMARY KEY NOT NULL,
	QuellenID integer NOT NULL,
	SeminarTradID integer NOT NULL,
	
	CONSTRAINT SeminarInfo_Quelle_FK
	FOREIGN KEY (QuellenID)
	REFERENCES PRESENTATION.QUELLEN(QuellenID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT,
	
	CONSTRAINT SeminarInfo_SeminarTrad_FK
	FOREIGN KEY (SeminarTradID)
	REFERENCES PRESENTATION.SEMINAR_TRAD(SeminarTradID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT
);
CREATE TABLE presentation.name_info(NameInfoID integer PRIMARY KEY NOT NULL, QuellenID integer NOT NULL,	NameTradID integer NOT NULL, PersonID integer NOT NULL,
	CONSTRAINT NameInfo_Quelle_FK FOREIGN KEY (QuellenID) REFERENCES PRESENTATION.Quellen(QuellenID) ON DELETE RESTRICT	ON UPDATE RESTRICT,
	CONSTRAINT NameInfo_NameTrad_FK	FOREIGN KEY (NameTradID) REFERENCES PRESENTATION.Name_Trad(NameTradID) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT NameInfo_Person_FK FOREIGN KEY (PersonID) REFERENCES Presentation.Person(PersonID) ON DELETE RESTRICT ON UPDATE RESTRICT
);


CREATE TABLE presentation.person(
	PersonID integer PRIMARY KEY NOT NULL, FakultaetID integer NOT NULL,
	CONSTRAINT Person_Fakultat_FK FOREIGN KEY (FakultaetID) REFERENCES PRESENTATION.FAKULTATEN(FakultaetID) ON DELETE RESTRICT ON UPDATE RESTRICT
);
