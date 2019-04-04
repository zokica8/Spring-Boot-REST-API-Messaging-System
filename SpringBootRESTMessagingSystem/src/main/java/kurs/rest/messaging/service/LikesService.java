package kurs.rest.messaging.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import kurs.rest.messaging.beans.Likes;
import kurs.rest.messaging.beans.PostsSent;
import kurs.rest.messaging.connection.ConnectionInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@org.springframework.stereotype.Service("likesService")
public class LikesService extends Service {

	// instance variables
	// without Spring Framework
	//private ConnectionInterface connect = ConnectionFactory.returnConnection(StringUtil.DATABASE);
	
	//with Spring Framework
	@Autowired
	private ConnectionInterface connect;

	// methods
	// insert like
	public Likes insertLike(int user_id, int post_id) throws SQLException {
		
		String sql = String.format("insert into likes (user_id, post_id) values (%d, %d)", user_id, post_id);
		Likes like = new Likes();
		try (PreparedStatement pstmt = connect.returnConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS)) {

			int insert = pstmt.executeUpdate();
			log.info("Like inserted! " + insert + "\n");
		} catch (SQLException e) {
			log.error("Like already inserted!!");
			deleteLike(user_id, post_id);
		}

		return like;
	}

	// delete like (unlike)
	public void deleteLike(int user_ID, int post_ID) throws SQLException {
		Likes like = new Likes();
		like.setUser_id(user_ID);
		like.setPost_id(post_ID);

		String sql = "delete from likes where user_ID = ? AND post_ID = ?";

		try (PreparedStatement pstmt = connect.returnConnection().prepareStatement(sql)) {
			pstmt.setInt(1, like.getUser_id());
			pstmt.setInt(2, like.getPost_id());

			int delete = pstmt.executeUpdate();
			log.info("Like deleted from database! " + delete + "\n");
		} catch (SQLException e) {
			log.error("Record not deleted! Error in database!");
			log.error(e.getMessage());
		}
	}

	public List<PostsSent> getLikesPerPerson(int post_id) throws SQLException {
		List<PostsSent> likes = new ArrayList<>();
		
		String sql = "select u.username from user u "
				+ "join likes l on u.user_ID = l.user_ID "
				+ "where l.post_ID = ?";
		
		try(PreparedStatement pstmt = connect.returnConnection().prepareStatement(sql)) {
			pstmt.setInt(1, post_id);
			pstmt.execute();
			try(ResultSet rs = pstmt.getResultSet()) {
				while(rs.next()) {
					PostsSent sent = new PostsSent();
					sent.setUsername(rs.getString(1));
					likes.add(sent);
				}
			}
		}
		return likes;
	}
}
