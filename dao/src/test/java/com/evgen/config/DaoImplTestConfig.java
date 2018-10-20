package com.evgen.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@EnableMongoRepositories(basePackages = "com.evgen.dao")
@ComponentScan(basePackages = "com.evgen.dao")
@PropertySource("classpath:dbTest.properties")
@EnableTransactionManagement
public class DaoImplTestConfig extends AbstractMongoConfiguration {

  @Value("${reservations.url}")
  private String url;

  @Value("${reservations.name}")
  private String name;

  @Override
  protected String getDatabaseName() {
    return name;
  }

  @Override
  public MongoClient mongoClient() {
    MongoClientURI uri = new MongoClientURI(url);
    return new MongoClient(uri);
  }

  @Override
  protected Collection<String> getMappingBasePackages() {
    return Collections.singleton("com.evgen");
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}