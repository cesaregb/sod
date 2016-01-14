package com.il.sod.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.il.sod.dao.ShopServiceDAO;
import com.il.sod.exceptions.SODException;
import com.il.sod.model.entities.Shop;
import com.il.sod.model.repositories.ShopRepository;

@Service
public class ShopServiceDaoImpl implements ShopServiceDAO{

	@Resource
	private ShopRepository shopRepository;

	@Override
	@Transactional
	public Shop create(Shop shop) {
		Shop createdShop = shop;
		return shopRepository.save(createdShop);
	}

	@Override
	@Transactional
	public Shop findById(int id) {
		return shopRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=SODException.class)
	public Shop delete(int id) throws SODException {
		Shop deletedShop = shopRepository.findOne(id);

		if (deletedShop == null)
			throw new SODException();

		shopRepository.delete(deletedShop);
		return deletedShop;
	}

	@Override
	@Transactional
	public List<Shop> findAll() {
		return shopRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=SODException.class)
	public Shop update(Shop shop) throws SODException {
		Shop updatedShop = shopRepository.findOne(shop.getId());

		if (updatedShop == null)
			throw new SODException();

		updatedShop.setName(shop.getName());
		updatedShop.setEmplNumber(shop.getEmplNumber());
		return updatedShop;
	}

}