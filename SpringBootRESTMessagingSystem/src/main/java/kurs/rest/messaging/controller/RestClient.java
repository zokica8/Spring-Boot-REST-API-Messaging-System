package kurs.rest.messaging.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestClient {
	
	@Value("${kurs.demo.endpoints}")
	private String[] endpoints;
	
	public String[] getEndpoints() { return endpoints; }

}
