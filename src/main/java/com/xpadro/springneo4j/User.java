package com.xpadro.springneo4j;

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
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }


    @Relationship(type = "READ")
    public Set<Article> articles;

    @Relationship(type = "INTERESTED")
    public Set<InterestRelationship> interestRelationships;

    public void reads(Article article) {
        if (articles == null) {
            articles = new HashSet<>();
        }

        articles.add(article);

        article.getTopics().forEach(this::addInterest);
    }

    private void addInterest(Topic topic) {
        if (interestRelationships == null) {
            interestRelationships = new HashSet<>();
        }

        boolean found = false;

        for (InterestRelationship ir : interestRelationships) {
            if (ir.getTopic().getName().equals(topic.getName())) {
                ir.setWeight(ir.getWeight() + 1);
                found = true;
            }
        }

        if (!found) {
            InterestRelationship newRelationship = new InterestRelationship(this, topic, 1);
            this.interestRelationships.add(newRelationship);
        }
    }

    public String toString() {
        return this.name + "'s reads => "
                + Optional.ofNullable(this.articles).orElse(
                Collections.emptySet()).stream()
                .map(Article::getUrl)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
