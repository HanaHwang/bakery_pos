package com.bakerypos.bakery_pos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bakerypos.bakery_pos.model.DetailPenjualan;


public interface DetailPenjualanRepository extends JpaRepository<DetailPenjualan, Integer>{
    List<DetailPenjualan> findByPenjualanIdPenjualan(int idPenjualan);
}