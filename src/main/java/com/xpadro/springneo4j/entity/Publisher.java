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
public class Publisher {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Publisher() {
    }

    public Publisher(String name) {
        this.name = name;
    }

    @Relationship(type = "PUBLISH")
    public Set<Article> articles;

    public void publishes(Article article) {
        if (articles == null) {
            articles = new HashSet<>();
        }

        articles.add(article);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name + "'s publishes => "
                + Optional.ofNullable(this.articles).orElse(
                Collections.emptySet()).stream()
                .map(Article::getUrl)
                .collect(Collectors.toList());
    }
}
