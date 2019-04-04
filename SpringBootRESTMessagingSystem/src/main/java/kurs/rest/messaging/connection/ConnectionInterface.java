package kurs.rest.messaging.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionInterface {
	
	void close() throws SQLException;

	Connection returnConnection() throws SQLException;

}
