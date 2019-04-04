package kurs.rest.messaging.service;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;

import kurs.rest.messaging.beans.User;
import kurs.rest.messaging.connection.ConnectionInterface;
import kurs.rest.messaging.connection.MessagingSystemConnectionTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@org.springframework.stereotype.Service(value = "userService")
public class UserService extends Service {

	// instance variables
	// without Spring Framework
	//private ConnectionInterface connect = ConnectionFactory.returnConnection(StringUtil.DATABASE);
	
	//with Spring Framework
	@Autowired
	private ConnectionInterface connect;

	// methods
	// insert user
	public User insertUser(User user) throws SQLException {

		String sql = "insert into user (username, password) values (?, sha2(?, 512))";
		try (PreparedStatement pstmt = connect.returnConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());

			int insert = pstmt.executeUpdate();
			log.info("Record inserted! " + insert);
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				rs.next();
				int value = rs.getInt(1);
				log.info("Primary key number: " + value);
			}

		} catch (SQLException e) {
			log.error("Record insert not successful!");
			log.error(e.getMessage());
		}

		return user;
	}

	// updating users password
	public User updateUser(User user) throws SQLException {

		String sql = "update user set password = ? where user_ID = ?";

		try (PreparedStatement pstmt = connect.returnConnection().prepareStatement(sql)) {
			pstmt.setString(1, user.getPassword());
			pstmt.setInt(2, user.getUser_id());

			int update = pstmt.executeUpdate();
			log.info("Record updated! " + update + "\n");
		} catch (SQLException e) {
			log.error("Record update not successful!");
			log.error(e.getMessage());
		}

		return user;
	}

	// deleting user, and also deleting ALL of his messages!!
	public void deleteUser(int userId) throws SQLException {
		connectTransaction = new MessagingSystemConnectionTransaction();
		User user = new User();
		user.setUser_id(userId);

		String sql = "{call deleteUser(?)}";

		try (CallableStatement cstmt = connectTransaction.returnConnection().prepareCall(sql)) {
			cstmt.setInt(1, user.getUser_id());

			int delete = cstmt.executeUpdate();
			log.info("User deleted from database! " + delete + "\n");
		} catch (SQLException e) {
			log.error("Record not deleted! Error in database!");
			log.error(e.getMessage());
		}
	}

	// user login functionality to check if the user is in the database!
	// login functionality FINALLY COMPLETED!!!
	public User loginUser(String username) throws SQLException {
		User user = new User();
		String sql = "select user_ID, username, password, imageId from user where "
				+ "binary username = ?";

		try (PreparedStatement pstmt = connect.returnConnection().prepareStatement(sql)) {
			pstmt.setString(1, username);
			pstmt.execute();
			
			try (ResultSet rs = pstmt.getResultSet()) {
				while(rs.next()) {
					user.setUser_id(rs.getInt(1));
					user.setUsername(rs.getString(2));
					user.setPassword(rs.getString(3));
					user.setImageId(rs.getString(4));
				}
				log.info("The user is in the database!!");
			}
		} catch(SQLException e) {
			log.error("Error in the database!!");
			log.error(e.getMessage());
		}
		return user;
	}
	
	public User updateImageForUser(User user, String imageUUID) throws SQLException {
		String sql = "update user set imageId = ? where username = ?";
		
		try(PreparedStatement pstmt = connect.returnConnection().prepareStatement(sql)) {
			pstmt.setString(1, imageUUID);
			pstmt.setString(2, user.getUsername());
			
			int update = pstmt.executeUpdate();
			log.info("Record updated! " + update + "\n");	
		} catch (SQLException e) {
			log.error("Record update not successful!");
			log.error(e.getMessage());
		}
		return user;
	}
}
