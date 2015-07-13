CREATE FUNCTION HYLLEBLOMST.GROUP_CONCAT
    ( SEPARATOR CHAR, ARGS VARCHAR(255) ... )
    RETURNS VARCHAR(2000)
    PARAMETER STYLE DERBY
    NO SQL LANGUAGE JAVA
	EXTERNAL NAME 'de.uniba.kinf.projm.hylleblomst.database.utils.GroupConcat.groupConcat';
	
DROP function HYLLEBLOMST.GROUP_CONCAT;

SELECT HYLLEBLOMST.GROUP_CONCAT (', ',QuellenName,FakultaetenNorm) FROM HYLLEBLOMST.quellen, HYLLEBLOMST.fakultaeten WHERE Hylleblomst.quellen.quellenID<=3 AND Hylleblomst.fakultaeten.fakultaetenID<=3; 
SELECT HYLLEBLOMST.GROUP_CONCAT (',',AnredeTrad) FROM HYLLEBLOMST.Anrede_Trad;

CALL SQLJ.INSTALL_JAR (
	'groupconcat.jar','HYLLEBLOMST.groupconcat',0);
CALL SQLJ.REMOVE_JAR (
	'HYLLEBLOMST.groupconcat.jar',0)
