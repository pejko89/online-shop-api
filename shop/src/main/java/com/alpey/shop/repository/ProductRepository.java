package com.alpey.shop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alpey.shop.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	
	public Product findByBarcode(String barcode);
	public Product findByName(String name);
	public List<Product> findByPrice(double price);

}
