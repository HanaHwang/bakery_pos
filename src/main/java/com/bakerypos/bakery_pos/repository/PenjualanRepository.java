package com.bakerypos.bakery_pos.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bakerypos.bakery_pos.model.Penjualan;

public interface PenjualanRepository extends JpaRepository<Penjualan, Integer>{
    List<Penjualan> findByTanggalBetween(LocalDateTime awal, LocalDateTime akhir);
}