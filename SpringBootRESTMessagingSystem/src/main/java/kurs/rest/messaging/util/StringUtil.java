package kurs.rest.messaging.util;

import java.util.UUID;

public class StringUtil {
	
	public static final String USER_CSV = "User.csv";
	public static final String POST_CSV = "post.csv";
	public static final String LIKE_CSV = "like.csv";
	public static final String TEST_DATABASE = "messagingsystemtest";
	public static final String DATABASE = "messagingsystem";
	public static final String DATABASE_TRANSACTION = "messagingsystem";
	public static final String TEST_DATABASE_TRANSACTION = "messagingsystemtest";
	public static final String UPLOAD_DIRECTORY = "userImages";
	public static final String UNIQUE_PIC_FILENAME = UUID.randomUUID().toString().replace("-", "") + ".jpg";
	
	private StringUtil() {
		
	}

}
