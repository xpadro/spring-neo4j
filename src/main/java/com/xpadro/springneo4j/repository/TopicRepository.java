package com.xpadro.springneo4j.repository;

import com.xpadro.springneo4j.Topic;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Long> {

    Topic findByName(String name);
}
