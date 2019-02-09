package com.chinatel.caur2cdoauth2.repository;


import com.chinatel.caur2cdoauth2.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findUserByUsername(String username);
}