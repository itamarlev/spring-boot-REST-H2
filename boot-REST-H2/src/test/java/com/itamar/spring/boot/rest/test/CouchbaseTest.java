package com.itamar.spring.boot.rest.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.itamar.spring.boot.rest.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = { Application.class })
public class CouchbaseTest {

	private TestRestTemplate restTemplate;
	private static final String BASE_URL = "http://localhost:8080/couchbase/";

	@Before
	public void beforeTest() {
		restTemplate = new TestRestTemplate();
	}

	@Test
	public void givenResourceUrl_whenSendGetForAllDocuments_thenStatusOk() {
		ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}