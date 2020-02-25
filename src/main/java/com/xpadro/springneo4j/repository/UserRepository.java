package com.xpadro.springneo4j.repository;

import com.xpadro.springneo4j.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);
}
