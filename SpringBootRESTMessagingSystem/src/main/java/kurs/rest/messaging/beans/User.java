package kurs.rest.messaging.beans;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User implements Serializable {
	
	private static final long serialVersionUID = -1428603333663146697L;

	private int user_id;
	
	private String username;
	
	private String password;
	
	private String imageId;
	
	@Getter
	private String message;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public User(int id, String username, String password) {
		user_id = id;
		this.username = username;
		this.password = password;
	}
	
	public boolean validate() {
		if(username == null) {
			message = "USERNAME can't be NULL!!";
			return false;
		}
		else if(username.isBlank()) {
			message = "USERNAME can't be blank!!!";
			return false;
		}
		else if(!username.matches("\\w+@\\w+\\.\\w+")) {
			message = "EMAIL not VALID!!";
			return false;
		}
		else if(password == null) {
			message = "PASSWORD can't be NULL!!";
			return false;
		}
		else if(password.isBlank()) {
			message = "PASSWORD can't be BLANK!!";
			return false;
		}
		else if(password.isEmpty()) {
			message = "PASSWORD can't be EMPTY!!";
			return false;
		}
		else if(password.matches("\\w*\\s+\\w*")) {
			message = "PASSWORD can't contain SPACES!!!";
			return false;
		}
		else if(password.length() < 10) {
			message = "PASSWORD must have AT LEAST 10 characters!!";
			return false;
		}
		return true;
	}
}
