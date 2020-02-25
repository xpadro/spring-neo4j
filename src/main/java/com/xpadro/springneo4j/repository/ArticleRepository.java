package com.xpadro.springneo4j.repository;

import com.xpadro.springneo4j.entity.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    Article findByUrl(String url);
}
