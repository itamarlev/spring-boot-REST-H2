package com.itamar.spring.boot.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itamar.spring.boot.rest.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
