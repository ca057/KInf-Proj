CREATE DERBY AGGREGATE HYLLEBLOMST.AGGREGATE_VARCHAR FOR VARCHAR(255) RETURNS VARCHAR(2000)	EXTERNAL NAME 'de.uniba.kinf.projm.hylleblomst.database.utils.GroupConcat';
	
DROP DERBY AGGREGATE HYLLEBLOMST.AGGREGATE_VARCHAR RESTRICT;

SELECT HYLLEBLOMST.AGGREGATE_VARCHAR(QuellenName) FROM HYLLEBLOMST.QUELLEN;

CREATE SCHEMA test;
CREATE TABLE test.test(a varchar(10), b varchar(10));
INSERT INTO  test.test values('eins','zwei');

SELECT a,b FROM test.test;
SELECT a, HYLLEBLOMST.AGGREGATE_VARCHAR(b) FROM test.test;


CALL SQLJ.INSTALL_JAR (
	'C:\Users\Simon\git\KInf-Projekt\lib\groupconcat.jar','groupconcat',0);
CALL SQLJ.REMOVE_JAR (
	'sys.groupconcat.jar',0);
