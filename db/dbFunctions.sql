CREATE SCHEMA SQL_UTIL

CREATE FUNCTION SQL_UTIL.TO_DEGREES
( RADIANS DOUBLE )
RETURNS DOUBLE
PARAMETER STYLE JAVA
NO SQL LANGUAGE JAVA
EXTERNAL NAME 'java.lang.Math.toDegrees'

CREATE FUNCTION function-name ( [ FunctionParameter 
   [, FunctionParameter] ] * ) RETURNS ReturnDataType [ FunctionElement ] *
   
   
CREATE FUNCTION SQL_UTIL.GROUP_CONCAT 

EXTERNAL NAME 'de.uniba.kinf.projm.hylleblomst.database.sql.util.GroupConcat.groupConcat'