package com.chinatel.caur2cdtest.repository;

import com.chinatel.caur2cdtest.model.CallBill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;

@RepositoryRestResource(path = "call-bill")
public interface CallBillRepository extends CrudRepository<CallBill, String> {

    ArrayList<CallBill> findAll();
}
