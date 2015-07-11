CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.fullAccessUsers', 'admin');
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.admin', 'r+l=j');
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.defaultConnectionMode','noAccess');

CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.readOnlyAccessUsers', 'guest');
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.guest', 'nedstark');

CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.requireAuthentication', 'true');
