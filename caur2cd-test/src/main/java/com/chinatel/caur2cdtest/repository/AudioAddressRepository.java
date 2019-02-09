package com.chinatel.caur2cdtest.repository;

import com.chinatel.caur2cdtest.model.AudioAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;

@RepositoryRestResource(path = "audio-address")
public interface AudioAddressRepository extends CrudRepository<AudioAddress, String> {

    ArrayList<AudioAddress> findAll();
}
