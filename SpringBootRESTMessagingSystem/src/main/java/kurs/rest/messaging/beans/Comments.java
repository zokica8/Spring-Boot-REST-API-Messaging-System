package kurs.rest.messaging.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
	
	private int comments_Id;
	private int user_Id;
	private String username;
	private int post_Id;
	private String post;
	private String content;
	private int commentsNumber;
	
	public Comments(int user_Id, int post_Id, String content) {
		this.user_Id = user_Id;
		this.post_Id = post_Id;
		this.content = content;
	}
	
	

}
