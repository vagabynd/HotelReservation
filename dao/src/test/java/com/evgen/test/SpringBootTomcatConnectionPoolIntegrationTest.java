package com.evgen.test;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.evgen.config.ReservationDaoConfig;

@RunWith(SpringRunner.class)
@Import(ReservationDaoConfig.class)
public class SpringBootTomcatConnectionPoolIntegrationTest {

  @Autowired
  private DataSource dataSource;

  @Test
  public void givenTomcatConnectionPoolInstance_whenCheckedPoolClassName_thenCorrect() {
    assertThat(dataSource.getClass().getName())
        .isEqualTo("org.apache.tomcat.jdbc.pool.DataSource");
  }
}