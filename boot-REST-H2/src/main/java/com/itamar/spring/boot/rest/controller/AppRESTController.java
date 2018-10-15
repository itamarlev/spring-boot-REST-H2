package com.itamar.spring.boot.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itamar.spring.boot.rest.exception.ItemNotFoundException;
import com.itamar.spring.boot.rest.exception.ItemOutOfStockException;
import com.itamar.spring.boot.rest.model.Item;
import com.itamar.spring.boot.rest.repository.ItemRepository;

@RestController
public class AppRESTController {

	@Autowired
	ItemRepository iRepository;

	AppRESTController(ItemRepository repository) {
		this.iRepository = repository;
	}

	@GetMapping("/items")
	List<Item> all() {
		return iRepository.findAll();
	}

	@GetMapping("/items/{number}")
	Item find(@PathVariable Long number) {
		return iRepository.findById(number).orElseThrow(() -> new ItemNotFoundException(number));
	}

	@PostMapping("/items/{number}/withdraw/{amount}")
	Item withdraw(@PathVariable Long number, @PathVariable Long amount) {
		Item item = find(number);
		if (item.getAmount() > amount)
			item.setAmount(item.getAmount() - amount);
		else
			throw new ItemOutOfStockException(number);
		iRepository.save(item);
		return item;
	}

	@PostMapping("/items/{number}/deposit/{amount}")
	Item deposit(@PathVariable Long number, @PathVariable Long amount) {
		Item item = find(number);
		item.setAmount(item.getAmount() + amount);
		iRepository.save(item);
		return item;
	}

	@PostMapping
	Item newItem(@RequestBody Item newItem) {
		return iRepository.save(newItem);
	}

	@PutMapping("/items/{number}")
	Item replaceItem(@RequestBody Item newItem) {
		if(null == newItem.getAmount())
			newItem.setAmount(0L);
		return iRepository.save(newItem);
	}

	@DeleteMapping("/items/{number}")
	void deleteItem(@PathVariable Long number) {
		iRepository.deleteById(number);
	}
}
