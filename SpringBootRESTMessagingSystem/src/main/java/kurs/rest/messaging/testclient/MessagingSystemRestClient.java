package kurs.rest.messaging.testclient;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import kurs.rest.messaging.beans.PostsSent;

public class MessagingSystemRestClient {
	
	private RestTemplate template = new RestTemplate();
	
	public List<PostsSent> getGenericResponse(HttpMethod httpMethod, String url) {
		ResponseEntity<List<PostsSent>> response = template.exchange(
				url, httpMethod, null, new ParameterizedTypeReference<List<PostsSent>>() {
				});
		
		List<PostsSent> posts = response.getBody();
		
		return posts;
	}
	
	public void test(int iterations) {
		
		for(int i = 0; i < iterations; i++) {
			String url = "http://localhost:8101/messagingSystem/posts";
			
			List<PostsSent> posts = getGenericResponse(HttpMethod.GET, url);
			for(PostsSent post : posts) {
				System.out.println(post);
			}
		}
	}
	
	public static void main(String[] args) {
		MessagingSystemRestClient client = new MessagingSystemRestClient();
		client.test(10);
	}

}
