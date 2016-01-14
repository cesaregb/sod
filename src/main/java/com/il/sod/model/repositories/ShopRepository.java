package com.il.sod.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.il.sod.model.entities.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {}