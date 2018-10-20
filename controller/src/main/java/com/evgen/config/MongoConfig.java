package com.evgen.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@EnableMongoRepositories(basePackages = "com.evgen.dao")
public class MongoConfig extends AbstractMongoConfiguration {

  @Override
  protected String getDatabaseName() {
    return "hotel_management";
  }

  @Override
  public MongoClient mongoClient() {
    MongoClientURI uri = new MongoClientURI("mongodb://rotar:rotar2612@ds125683.mlab.com:25683/hotel_management");
    return new MongoClient(uri);
  }

  @Override
  protected Collection<String> getMappingBasePackages() {
    return Collections.singleton("com.evgen");
  }
}