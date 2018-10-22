package com.evgen.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@EnableMongoRepositories(basePackages = "com.evgen.dao")
@PropertySource("classpath:db.properties")
public class MongoConfig extends AbstractMongoConfiguration {

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

}