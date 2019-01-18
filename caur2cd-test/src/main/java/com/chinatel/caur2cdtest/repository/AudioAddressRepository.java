package com.chinatel.caur2cdtest.repository;

import com.chinatel.caur2cdtest.model.AudioAddress;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface AudioAddressRepository extends CrudRepository<AudioAddress, String> {

    ArrayList<AudioAddress> findAll();
}
