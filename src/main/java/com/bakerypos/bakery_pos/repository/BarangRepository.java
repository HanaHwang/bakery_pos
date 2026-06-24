package com.bakerypos.bakery_pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bakerypos.bakery_pos.model.Barang;

public interface BarangRepository extends JpaRepository<Barang, Integer>{
}