package com.xpadro.springneo4j;

import com.xpadro.springneo4j.entity.Article;
import com.xpadro.springneo4j.entity.Topic;
import com.xpadro.springneo4j.entity.User;
import com.xpadro.springneo4j.repository.ArticleRepository;
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

@SpringBootApplication
@EnableNeo4jRepositories
public class SpringNeo4jApplication {
	private static final Logger logger = LoggerFactory.getLogger(SpringNeo4jApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringNeo4jApplication.class, args);
	}

	@Bean
	CommandLineRunner demo(UserRepository userRepository, TopicRepository topicRepository, ArticleRepository articleRepository) {
		return args -> {
			userRepository.deleteAll();
			articleRepository.deleteAll();
			topicRepository.deleteAll();

			Topic politics = new Topic("politics");
			Topic economy = new Topic("economics");
			Topic sports = new Topic("sports");

			Article article1 = new Article("url1");
			article1.belongs(politics);

			Article article2 = new Article("url2");
			article2.belongs(economy);

			Article article3 = new Article("url3");
			article3.belongs(politics);

			Article article4 = new Article("url4");
			article4.belongs(politics);

			Article article5 = new Article("url5");
			article5.belongs(economy);
			articleRepository.save(article5);

			Article article6 = new Article("url6");
			article6.belongs(sports);
			articleRepository.save(article6);

			Article article7 = new Article("url7");
			article7.belongs(economy);


			User user1 = new User("user1");
			user1.reads(article1);
			user1.reads(article2);
			user1.reads(article5);
			userRepository.save(user1);

			User user2 = new User("user2");
			user2.reads(article3);
			user2.reads(article4);
			user2.reads(article7);
			userRepository.save(user2);


			logger.info("Listing users...");
			List<User> users = Arrays.asList(user1, user2);
			users.forEach(u -> logger.info("\t" + userRepository.findByName(u.getName()).toString()));
		};
	}
}
