package com.evgen.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:sql/sql.properties")
public class ReservationDaoConfig {

  @Bean
  public DataSource dataSource() {
    org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
    datasource.setDriverClassName("org.postgresql.Driver");
    datasource.setUrl("jdbc:postgresql://localhost:5432/postgres");
    datasource.setUsername("evgen");
    datasource.setPassword("26121997");
    datasource.setInitialSize(10);
    datasource.setMaxActive(10);
    datasource.setMaxIdle(10);
    datasource.setMinIdle(10);
    return datasource;
  }

}
