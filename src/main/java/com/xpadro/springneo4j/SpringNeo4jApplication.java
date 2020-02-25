package com.xpadro.springneo4j;

import com.xpadro.springneo4j.entity.Article;
import com.xpadro.springneo4j.entity.Publisher;
import com.xpadro.springneo4j.entity.Topic;
import com.xpadro.springneo4j.entity.User;
import com.xpadro.springneo4j.repository.ArticleRepository;
import com.xpadro.springneo4j.repository.PublisherRepository;
import com.xpadro.springneo4j.repository.TopicRepository;
import com.xpadro.springneo4j.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

@SpringBootApplication
@EnableNeo4jRepositories
public class SpringNeo4jApplication {
	private static final Logger logger = LoggerFactory.getLogger(SpringNeo4jApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringNeo4jApplication.class, args);
	}

	@Bean
	CommandLineRunner demo(UserRepository userRepository,
						   TopicRepository topicRepository,
						   ArticleRepository articleRepository,
						   PublisherRepository publisherRepository) {

		return args -> {
			userRepository.deleteAll();
			articleRepository.deleteAll();
			topicRepository.deleteAll();
			publisherRepository.deleteAll();

			Topic politics = new Topic("politics");
			topicRepository.save(politics);
			Topic economy = new Topic("economics");
			topicRepository.save(economy);
			Topic sports = new Topic("sports");
			topicRepository.save(sports);

			Publisher publisher1 = new Publisher("publisher1");
			Publisher publisher2 = new Publisher("publisher2");
			Publisher publisher3 = new Publisher("publisher3");
			Publisher publisher4 = new Publisher("publisher4");

			Article article1 = publishArticle(publisher1, "url1", politics, publisherRepository);
			Article article2 = publishArticle(publisher1, "url2", economy, publisherRepository);
			Article article3 = publishArticle(publisher1, "url3", politics, publisherRepository);

			Article article4 = publishArticle(publisher2, "url4", politics, publisherRepository);
			Article article5 = publishArticle(publisher2, "url5", economy, publisherRepository);

			publishArticle(publisher3, "url6", sports, publisherRepository);

			Article article7 = publishArticle(publisher4, "url7", economy, publisherRepository);
			
			User user1 = new User("user1");
			readArticles(user1, userRepository, article1, article2, article5);

			User user2 = new User("user2");
			readArticles(user2, userRepository, article3, article4, article7);

			logger.info("Listing topics...");
			topicRepository.findAll().forEach(t -> logger.info(format("\t%s", t.toString())));

			logger.info("Listing users...");
			List<User> users = Arrays.asList(user1, user2);
			users.forEach(u -> logger.info(format("\t%s", userRepository.findByName(u.getName()).toString())));
		};
	}

	private Article publishArticle(Publisher publisher, String url, Topic topic, PublisherRepository publisherRepository) {
		Article article = new Article(url);
		article.belongs(topic);
		publisher.publishes(article);

		publisherRepository.save(publisher);

		return article;
	}

	private void readArticles(User user, UserRepository userRepository, Article... articles) {
		Arrays.stream(articles).forEach(user::reads);

		userRepository.save(user);
	}
}
