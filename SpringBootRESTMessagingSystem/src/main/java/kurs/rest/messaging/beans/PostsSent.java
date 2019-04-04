package kurs.rest.messaging.beans;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostsSent {
	
	private int post_ID;
	private String username;
	private String content;
	private int likesCount;
	private LocalDateTime timeOfMessage;
	private String imageId;

}
