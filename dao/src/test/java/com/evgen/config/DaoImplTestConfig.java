package com.evgen.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@EnableMongoRepositories(basePackages = "com.evgen.dao")
@ComponentScan(basePackages = "com.evgen.dao")
@EnableTransactionManagement
public class DaoImplTestConfig extends AbstractMongoConfiguration {

  @Override
  protected String getDatabaseName() {
    return "hotel_management_test";
  }

  @Override
  public MongoClient mongoClient() {
    MongoClientURI uri = new MongoClientURI("mongodb://test:qwerty1@ds247410.mlab.com:47410/hotel_management_test");
    return new MongoClient(uri);
  }

  @Override
  protected String getMappingBasePackage() {
    return "com.evgen";
  }
}