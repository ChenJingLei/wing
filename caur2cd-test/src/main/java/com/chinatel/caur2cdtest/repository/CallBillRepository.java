package com.chinatel.caur2cdtest.repository;

import com.chinatel.caur2cdtest.model.CallBill;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CallBillRepository extends CrudRepository<CallBill, String> {

    ArrayList<CallBill> findAll();
}
