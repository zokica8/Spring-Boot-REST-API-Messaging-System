package kurs.rest.messaging.beans;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Post {
	
	private int post_ID;
	private String content;
	private LocalDateTime timeOfMessage;
	private int user_ID;
	
	@Getter
	private String message;
	
	public Post(String content, LocalDateTime timeOfMessage, int user_ID) {
		this.content = content;
		this.timeOfMessage = timeOfMessage;
		this.user_ID = user_ID;
	}
	
	public boolean validate() {
		if(content == null) {
			message = "Content can't be NULL!!";
			return false;
		}
		
		if(content.isBlank()) {
			message = "Content can't be blank!!";
			return false;
		}
		
		return true;
	}
}
