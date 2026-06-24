package com.bakerypos.bakery_pos.service;

import org.springframework.stereotype.Service;
import com.bakerypos.bakery_pos.repository.BarangRepository;
import java.util.List;
import com.bakerypos.bakery_pos.model.Barang;

@Service
public class BarangService {
    private final BarangRepository barangRepository;
    public BarangService(BarangRepository barangRepository){
        this.barangRepository = barangRepository;
    }

    public List<Barang> getAllBarang(){
        return barangRepository.findAll();
    }

    public Barang getBarangById(int id){
        return barangRepository.findById(id).orElse(null);
    }

    public void save(Barang barang){
        barangRepository.save(barang);
    }

    public void delete(int id){
        barangRepository.deleteById(id);
    }
}