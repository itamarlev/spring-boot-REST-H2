package com.itamar.spring.boot.rest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Item {

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long number;
	private String name;
	private Long amount;
	private String inventorycode;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getInventoryCode() {
		return inventorycode;
	}

	public void setInventoryCode(String inventoryCode) {
		this.inventorycode = inventoryCode;
	}

	public Item(String name, Long amount, String inventoryCode) {
		super();
		this.name = name;
		this.amount = amount;
		this.inventorycode = inventoryCode;
	}

	public Item(Long number, String name, Long amount, String inventoryCode) {
		super();
		this.number = number;
		this.name = name;
		this.amount = amount;
		this.inventorycode = inventoryCode;
	}

	public Item() {
		super();
	}

	@Override
	public String toString() {
		return String.format("Item [number=%s, name=%s, ammount=%s, inventoryCode=%s ]", number, name, amount,
				inventorycode);
	}

}
