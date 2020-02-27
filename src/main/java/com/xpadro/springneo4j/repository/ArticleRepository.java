package com.xpadro.springneo4j.repository;

import com.xpadro.springneo4j.entity.Article;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    Article findByUrl(String url);

    @Query("" +
            "MATCH (u:User {name: $0})-[r:READ]->(a:Article), (a)-[:BELONGS]->(t:Topic)<-[:BELONGS]-(rec:Article) " +
            "WHERE NOT EXISTS( (u)-[:READ]->(rec)) AND a.url CONTAINS $1 " +
            "WITH rec " +
            "RETURN rec.url AS recommendation")
    List<String> findRelatedArticleUrls(String user, String readArticleUrl);

    // You also have to return the relationship 'b'. Otherwise, Spring Data does not bind the related entity 't'
    @Query("" +
            "MATCH (u:User {name: $0})-[r:READ]->(a:Article), (a)-[:BELONGS]->(t:Topic)<-[b:BELONGS]-(rec:Article) " +
            "WHERE NOT EXISTS( (u)-[:READ]->(rec)) AND a.url CONTAINS $1 " +
            "WITH rec, t, b " +
            "RETURN rec AS recommendation, collect(t) AS topics, b")
    List<Article> findRelatedArticles(String user, String readArticleUrl);

}
