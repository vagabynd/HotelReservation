package com.evgen.config;


import javax.sql.DataSource;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.evgen.ReservationDao;
import com.evgen.dao.ReservationDaoImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootConfiguration
@ComponentScan(basePackages = "com.evgen.dao")
@EnableTransactionManagement
public class DaoImplTestConfig  {

  @Bean
  public ReservationDao reservationDao() {
    return new ReservationDaoImpl(namedParameterJdbcTemplate());
  }

  @Bean
  public DataSource getDataSource() {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();

    return builder
        .setType(EmbeddedDatabaseType.H2)
        .addScript("schema-postgresql.sql")
        .addScript("data-postgresql.sql")
        .build();
  }

  @Bean
  public PlatformTransactionManager txManager() {
    return new DataSourceTransactionManager(getDataSource());
  }

  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
    return new NamedParameterJdbcTemplate(getDataSource());
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}