package com.xpadro.springneo4j.repository;

import com.xpadro.springneo4j.entity.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {

    Publisher findByName(String name);
}
