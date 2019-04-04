package kurs.rest.messaging.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Likes {
	
	private int user_id;
	private int post_id;

}
