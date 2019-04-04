package kurs.rest.messaging.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import kurs.rest.messaging.beans.Comments;
import kurs.rest.messaging.connection.ConnectionInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@org.springframework.stereotype.Service("commentsService")
public class CommentsService extends Service {
	
	// instance variables
	//without Spring Framework
	//private ConnectionInterface connect = ConnectionFactory.returnConnection(StringUtil.DATABASE);
	
	//with Spring Framework
	@Autowired
	private ConnectionInterface connect;
	
	//methods
	//insert comment
	public Comments insertComment(Comments comment) throws SQLException {
		
		String sql = String.format("insert into comments(user_ID, post_ID, content) values (?, ?, ?);");
		
		try(PreparedStatement pstmt = connect.returnConnection().prepareStatement(sql, 
				Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, comment.getUser_Id());
			pstmt.setInt(2, comment.getPost_Id());
			pstmt.setString(3, comment.getContent());
			
			int insert = pstmt.executeUpdate();
			log.info("Record inserted! " + insert + "\n");
			try(ResultSet rs = pstmt.getGeneratedKeys()) {
				rs.next();
				int key = rs.getInt(1);
				log.info("Primary key number: " + key);
			}
		} catch (SQLException e) {
			log.error("Record insert not successful!");
			log.error(e.getMessage());
		}
		
		return comment;
	}
	
	//delete comment
	public void deleteComment(int comment_Id) throws SQLException {
		Comments comment = new Comments();
		comment.setComments_Id(comment_Id);
		
		String sql = "delete from comments where comments_ID = ?";
		
		try(PreparedStatement pstmt = connect.returnConnection().prepareStatement(sql)) {
			pstmt.setInt(1, comment_Id);
			
			int delete = pstmt.executeUpdate();
			log.info("Comment deleted! " + delete + "\n");
		} catch (SQLException e) {
			log.error("Record insert not successful!");
			log.error(e.getMessage());
		}
	}
	
	// all comments for a singular post
	public List<Comments> getCommentsPerPost(int post_Id) throws SQLException {
		List<Comments> comments = new ArrayList<>();
		
		String sql = "select u.user_ID, u.username, p.post_ID, p.content, c.content from comments c "
				+ "join user u on u.user_ID = c.user_ID "
				+ "join post p on p.post_ID = c.post_ID "
				+ "where p.post_ID = ?;";
		
		try(PreparedStatement pstmt = connect.returnConnection().prepareStatement(sql)) {
			pstmt.setInt(1, post_Id);
			pstmt.execute();
			
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					Comments comment = new Comments();
					comment.setUser_Id(rs.getInt(1));
					comment.setUsername(rs.getString(2));
					comment.setPost_Id(rs.getInt(3));
					comment.setPost(rs.getString(4));
					comment.setContent(rs.getString(5));
					
					comments.add(comment);
				}
			}
		}
		
		return comments;
	}
	
	// all comments for all posts
	public List<Comments> countComments(int post_ID) throws SQLException {
		List<Comments> comments = new ArrayList<>();
		
		String sql = "select p.post_ID, p.content, count(c.content) as commentsNumber from post p "
				+ "left join comments c on p.post_ID = c.post_ID "
				+ "where p.post_ID = " + post_ID + " "
				+ "group by p.content";
		
		try(PreparedStatement pstmt = connect.returnConnection().prepareStatement(sql)) {
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					Comments comment = new Comments();
					comment.setPost_Id(rs.getInt(1));
					comment.setPost(rs.getString(2));
					comment.setCommentsNumber(rs.getInt(3));
					
					comments.add(comment);
				}
			}
		}		
		return comments;
	}

}
