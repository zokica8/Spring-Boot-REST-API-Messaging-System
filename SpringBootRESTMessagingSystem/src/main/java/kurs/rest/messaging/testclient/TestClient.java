package kurs.rest.messaging.testclient;

import java.util.Random;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TestClient {
	private RestTemplate templ = new RestTemplate();
	private Random rnd = new Random();
	
	public <T> T getResponse(String url, Class<T> tClass) {
		
		T resp = (T) templ.getForObject(url, tClass);
		
		return resp;
	}
	
	public <T, K> T getGenericResponse(HttpMethod method, String url, K reqEnt) {
		HttpEntity<K> reqEntity = reqEnt != null ? new HttpEntity<K>(reqEnt) : null;
		ResponseEntity<T> response = templ.exchange(url, method, reqEntity,
				new ParameterizedTypeReference<T>() {
				});

		T resp = response.getBody();
		
		return resp;
	}
	
//	public List<Topic> getGenericResponse(HttpMethod method, String url) {
//		ResponseEntity<List<Topic>> response = templ.exchange(url, method, null,
//				new ParameterizedTypeReference<List<Topic>>() {
//				});
//
//		List<Topic> topics = response.getBody();
//		
//		return topics;
//	}
	
	public int getRandomInt(int min, int max) {
		return rnd.nextInt((max - min) + 1) + min;
	}
	
//	public void test(int iterations, String[] endpoints) {
//		
//		for (int i = 0; i < iterations; i++) {
//			int randomIndex = getRandomInt(0, endpoints.length - 1);
//			String epoint = endpoints[randomIndex];
//			String urlGetTopic = String.format("%s/api/topics/%d", epoint, getRandomInt(1, 5));
//			String urlGetAllTopics = String.format("%s/api/topics", epoint);
//			
//			Topic t = getResponse(urlGetTopic, Topic.class);
//			System.out.println(t);
//			
//			List<Topic> topics = getGenericResponse(HttpMethod.GET, urlGetAllTopics);
//			for (Topic top : topics) {
//				System.out.println(top);
//			}
//			System.out.println(Arrays.toString(topics.toArray()));
//			
//			Topic tp = topics.get(topics.size() - 1);
//			tp.setId(topics.size());
//			boolean added = getGenericResponse(HttpMethod.POST, urlGetAllTopics, tp);
//			System.out.println("ADDED: " + added);
			
//			tp.setName(tp.getName() + " UPDATED");
//			boolean updated = getGenericResponse(HttpMethod.PUT, urlGetAllTopics, tp);
//			System.out.println("UPDATED: " + updated);
			
//			boolean deleted = getGenericResponse(HttpMethod.DELETE, urlGetTopic);
//			System.out.println("DELETED: " + deleted);			
			
//		}
//	}
//
//	public static void main(String[] args) {
//		TestClient cli = new TestClient();
//		cli.test(150, args);
//
//	}

}
