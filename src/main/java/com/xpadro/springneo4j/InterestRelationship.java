package com.xpadro.springneo4j;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "INTERESTED")
public class InterestRelationship {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private User user;

    @EndNode
    private Topic topic;

    @Property
    private int weight;

    public InterestRelationship() {
    }

    public InterestRelationship(User user, Topic topic, int weight) {
        this.user = user;
        this.topic = topic;
        this.weight = weight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
