package com.itamar.spring.boot.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itamar.spring.boot.rest.exception.ItemNotFoundException;
import com.itamar.spring.boot.rest.exception.ItemOutOfStockException;
import com.itamar.spring.boot.rest.model.Item;
import com.itamar.spring.boot.rest.repository.ItemRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/items")
@Api(value="Item Resources")
public class AppRESTController {

	@Autowired
	ItemRepository iRepository;

	AppRESTController(ItemRepository repository) {
		this.iRepository = repository;
	}

	@ApiOperation(value = "returns all the available items")
	@GetMapping("/")
	List<Item> all() {
		return iRepository.findAll();
	}

	@ApiOperation(value = "returns a specific item")
	@GetMapping("/{number}")
	Item find(@PathVariable Long number) {
		return iRepository.findById(number).orElseThrow(() -> new ItemNotFoundException(number));
	}

	@ApiOperation(value = "create a new item")	
	@PostMapping
	Item newItem(@RequestBody Item newItem) {
		return iRepository.save(newItem);
	}

	@ApiOperation(value = "withdraw specific amount from an item")
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "not enough items to withdraw the amount given")
			}
			)
	@PostMapping("/{number}/withdraw/{amount}")
	Item withdraw(@PathVariable Long number, @PathVariable Long amount) {
		Item item = find(number);
		if (item.getAmount() > amount)
			item.setAmount(item.getAmount() - amount);
		else
			throw new ItemOutOfStockException(number);
		iRepository.save(item);
		return item;
	}

	@ApiOperation(value = "deposit specific amount to an item")
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "the amount has been added as requested")
			}
			)	
	@PostMapping("/{number}/deposit/{amount}")
	Item deposit(@PathVariable Long number, @PathVariable Long amount) {
		Item item = find(number);
		item.setAmount(item.getAmount() + amount);
		iRepository.save(item);
		return item;
	}

	@ApiOperation(value = "replace an item with new data")
	@PutMapping("/{number}")
	Item replaceItem(@RequestBody Item newItem) {
		if (null == newItem.getAmount())
			newItem.setAmount(0L);
		return iRepository.save(newItem);
	}

	@ApiOperation(value = "delete an item")	
	@DeleteMapping("/{number}")
	void deleteItem(@PathVariable Long number) {
		iRepository.deleteById(number);
	}
}
