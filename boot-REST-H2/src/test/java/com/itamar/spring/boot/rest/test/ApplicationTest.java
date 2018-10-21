package com.itamar.spring.boot.rest.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itamar.spring.boot.rest.Application;
import com.itamar.spring.boot.rest.model.Item;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = { Application.class })
public class ApplicationTest {

	private TestRestTemplate restTemplate;
	private static final String BASE_URL = "http://localhost:8080/items";

	@Before
	public void beforeTest() {
		restTemplate = new TestRestTemplate();
	}

	@Test
	public void givenResourceUrl_whenSendGetForRequestItem_thenStatusOk() {
		ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/2", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void givenResourceUrl_whenSendGetForRequestItem_thenBodyCorrect() throws IOException {
		ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/2", String.class);

		final ObjectMapper mapper = new ObjectMapper();
		final JsonNode root = mapper.readTree(response.getBody());
		final JsonNode name = root.path("name");
		assertThat(name.asText()).isNotNull();
	}

	@Test
	public void givenResourceUrl_whenRetrievingItem_thenCorrect() throws IOException {

		final Item item = restTemplate.getForObject(BASE_URL + "/2", Item.class);
		assertThat(item.getName()).isNotNull();
		assertThat(item.getNumber()).isEqualTo(2L);
	}

	@Test
	public void givenResourceUrl_whenRetrievingAllItems_thenStatusOk() throws IOException {
		ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void givenItemService_whenPostForObject_thenCreatedObjectIsReturned() {
		HttpEntity<Item> request = new HttpEntity<>(new Item("bar", 34L, "AY93756"));
		Item item = restTemplate.postForObject(BASE_URL, request, Item.class);
		assertThat(item).isNotNull();
		assertThat(item.getName()).isEqualTo("bar");
	}

	@Test
	public void givenItemService_whenDelete_thenItemIsRemoved() {
		// add an item to remove
		final Item item = new Item(11L, "Item to be deleted", 10L, "BF89655");
		ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, item, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		// delete the item
		restTemplate.delete(BASE_URL + "/11");

		// check that the item has been removed
		response = restTemplate.getForEntity(BASE_URL + "/11", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void givenItemService_whenPostResource_thenResourceIsCreated() {
		final Item item = new Item("bar2", 9L, "NL45277");
		ResponseEntity<Item> response = restTemplate.postForEntity(BASE_URL, item, Item.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		final Item itemResponse = response.getBody();
		assertThat(itemResponse).isNotNull();
		assertThat(item.getName()).isEqualTo("bar2");
	}

	@Test
	public void givenItemService_whenPostWithdraw_thenItemAmountUpdated() {
		// add a new item and check it has been added
		final Item item = new Item("bar3", 10L, "BR75893");
		ResponseEntity<Item> response = restTemplate.postForEntity(BASE_URL, item, Item.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Item itemResponse = response.getBody();
		assertThat(itemResponse).isNotNull();

		// withdraw items
		response = restTemplate.postForEntity(BASE_URL + "/" + itemResponse.getNumber() + "/withdraw/3", item,
				Item.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		itemResponse = response.getBody();
		assertThat(itemResponse).isNotNull();
		// check that the items were reduced from the amount
		assertThat(itemResponse.getAmount()).isEqualTo(7L);
	}

	@Test
	public void givenItemService_whenPostOverWithdraw_thenNotFoundReturned() {
		// add a new item and check it has been added
		final Item item = new Item("bar4", 10L, "BF75593");
		ResponseEntity<Item> response = restTemplate.postForEntity(BASE_URL, item, Item.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Item itemResponse = response.getBody();
		assertThat(itemResponse).isNotNull();

		// withdraw items
		response = restTemplate.postForEntity(BASE_URL + "/" + itemResponse.getNumber() + "/withdraw/13", item,
				Item.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void givenItemService_whenPostDeposit_thenItemAmountUpdated() {
		// add a new item and check it has been added
		final Item item = new Item("bar5", 10L, "BF75543");
		ResponseEntity<Item> response = restTemplate.postForEntity(BASE_URL, item, Item.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Item itemResponse = response.getBody();
		assertThat(itemResponse).isNotNull();

		// deposit items
		response = restTemplate.postForEntity(BASE_URL + "/" + itemResponse.getNumber() + "/deposit/13", item,
				Item.class);

		// check that the items were added to the amount
		itemResponse = response.getBody();
		assertThat(itemResponse.getAmount()).isEqualTo(23L);
	}

	@Test
	public void givenItemService_whenPutChangeRequest_thenTheItemChnges() {
		final Item item = new Item(1L, "updated Item", 10L, "BF75543");
		restTemplate.put(BASE_URL + "/1", item);
		final Item itemResponse = restTemplate.getForObject(BASE_URL + "/1", Item.class);
		assertThat(item.getName()).isEqualTo(itemResponse.getName());
		assertThat(item.getAmount()).isEqualTo(itemResponse.getAmount());
		assertThat(item.getInventoryCode()).isEqualTo(itemResponse.getInventoryCode());
	}

	@Test
	public void givenItemService_whenPutChangeRequest_withoutAmmount_thenTheItemChngesAndAmountItZero() {
		final Item item = new Item(1L, "updated Item with null amount", null, "BF75543");
		restTemplate.put(BASE_URL + "/1", item);
		final Item itemResponse = restTemplate.getForObject(BASE_URL + "/1", Item.class);
		assertThat(item.getName()).isEqualTo(itemResponse.getName());
		assertThat(itemResponse.getAmount()).isEqualTo(0L);
		assertThat(item.getInventoryCode()).isEqualTo(itemResponse.getInventoryCode());
	}
}