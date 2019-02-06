package com.chinatel.caur2cdsecurity.repository;

import com.chinatel.caur2cdsecurity.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findUserByUsername(String username);
}