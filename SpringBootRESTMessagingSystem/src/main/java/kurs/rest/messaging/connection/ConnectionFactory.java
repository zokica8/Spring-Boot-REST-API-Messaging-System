package kurs.rest.messaging.connection;

import kurs.rest.messaging.util.StringUtil;

// factory method pattern!
public class ConnectionFactory {
	
	public static ConnectionInterface returnConnection(String database) {
		if(database.equalsIgnoreCase(StringUtil.DATABASE)) {
			return new MessagingSystemConnection();
		}
		if(database.equalsIgnoreCase(StringUtil.TEST_DATABASE)) {
			return new MessagingSystemConnectionForTests();
		}
		if(database.equalsIgnoreCase(StringUtil.DATABASE_TRANSACTION)) {
			return new MessagingSystemConnectionTransaction();
		}
		if(database.equalsIgnoreCase(StringUtil.TEST_DATABASE_TRANSACTION)) {
			return new MessagingSystemConnectionTransactionForTests();
		}
		return null;
	}

}
