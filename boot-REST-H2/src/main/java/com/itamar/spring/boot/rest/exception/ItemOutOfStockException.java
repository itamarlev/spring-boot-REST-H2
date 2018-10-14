package com.itamar.spring.boot.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemOutOfStockException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ItemOutOfStockException(Long id) {
		super();
	}
}
