package com.xpadro.springneo4j.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@NodeEntity
public class Article {
    @Id
    @GeneratedValue
    private Long id;

    private String url;

    @Relationship(type = "BELONGS")
    private Set<Topic> topics;

    public Article() {
    }

    public Article(String url) {
        this.url = url;
    }

    public void belongs(Topic topic) {
        if (topics == null) {
            topics = new HashSet<>();
        }

        topics.add(topic);
    }

    public String toString() {
        return this.url + "'s belongs => "
                + Optional.ofNullable(this.topics).orElse(
                Collections.emptySet()).stream()
                .map(Topic::getName)
                .collect(Collectors.toList());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Topic> getTopics() {
        return topics;
    }
}
