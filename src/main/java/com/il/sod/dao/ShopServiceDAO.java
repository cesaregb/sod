package com.il.sod.dao;

import java.util.List;

import com.il.sod.exceptions.SODException;
import com.il.sod.model.entities.Shop;

public interface ShopServiceDAO {
	public Shop create(Shop shop);
	public Shop delete(int id) throws SODException;
	public List<Shop> findAll();
	public Shop update(Shop shop) throws SODException;
	public Shop findById(int id);
}
