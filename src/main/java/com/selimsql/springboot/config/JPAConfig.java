package com.selimsql.springboot.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.selimsql.springboot.util.Util;

@Configuration
@EnableTransactionManagement

@PropertySource(value = { "classpath:application.properties" })
public class JPAConfig {

  private Environment environment;

  public JPAConfig() {
    final String msg = "construct " + this.getClass().getSimpleName();
    System.out.println(msg);
  }

  @Autowired
  private void setEnvironment(Environment environment) {
    this.environment = environment;
  }


  @Bean
  public DataSource dataSource() {
    System.out.println("Bean: new DataSource");

    org.apache.tomcat.jdbc.pool.PoolProperties poolProperties = new org.apache.tomcat.jdbc.pool.PoolProperties();

    final String jdbcPREFIX = "jdbc";

    String driverClassName = environment.getProperty(jdbcPREFIX + ".driverClassName");
    poolProperties.setDriverClassName(driverClassName);

    String driverUrl = environment.getProperty(jdbcPREFIX + ".url");

    poolProperties.setUrl(driverUrl);
    System.out.println("->driverClassName:" + driverClassName);
    System.out.println("->driverUrl:" + driverUrl);

    String username = environment.getProperty(jdbcPREFIX + ".username");
    poolProperties.setUsername(username);

    String pass = environment.getProperty(jdbcPREFIX + ".password");
    poolProperties.setPassword(pass);

    final int initialSizeDEFAULT = 1;
    int initialSize = Util.getInt(environment.getProperty("jdbc.pool.initialSize"), initialSizeDEFAULT);
    if (initialSize > 0 && initialSize < 100) {
      poolProperties.setInitialSize(initialSize);
      poolProperties.setMinIdle(initialSize);

      System.out.println("->initialSize:" + initialSize);
    }


    final int maxActiveDEFAULT = 5;
    int maxActive = Util.getInt(environment.getProperty("jdbc.pool.maxActive"), maxActiveDEFAULT);
    if (maxActive >= initialSize && maxActive < 200) {
      poolProperties.setMaxIdle(maxActive); //Default: 100
      poolProperties.setMaxActive(maxActive); //Default: 100

      System.out.println("->maxActive:" + maxActive);
    }

    org.apache.tomcat.jdbc.pool.DataSource dsPooled = new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);

    return dsPooled;
  }//data_Source


  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
    System.out.println(">>>Bean:entityManagerFactory!!!");

    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    //factoryBean.setDataSource(dataSource());
    factoryBean.setDataSource(dataSource());

    factoryBean.setPackagesToScan(new String[] { "com.selimsql.springboot.model" });
    factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
    factoryBean.setJpaProperties(jpaProperties());
    return factoryBean;
  }


  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    System.out.println(">>>Bean:jpaVendorAdapter!!!");

    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

    boolean showSql = true;
    adapter.setShowSql(showSql);

    adapter.setGenerateDdl(false);
    return adapter;
  }


  //Here you can specify any provider specific properties.
  private Properties jpaProperties() {
    Properties properties = new Properties();
    String KEYS[] = new String[]
        { "hibernate.dialect",
        "hibernate.hbm2ddl.auto",
        "hibernate.ddl-auto",
        "hibernate.default_schema",
        "hibernate.id.new_generator_mappings",
        "hibernate.show_sql",
        "hibernate.format_sql",
        "hibernate.use_sql_comments"
        };

    int countKEY = KEYS.length;
    for (int i = 0; i < countKEY; i++) {
      String key = KEYS[i];
      String value = environment.getProperty(key);
      if (value != null)
        properties.put(key, value);
    }

    System.out.println("jpaProperties:" + properties);

    return properties;
  }


  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    System.out.println("Bean: new JpaTransactionManager:PlatformTransactionManager");

    PlatformTransactionManager platformTransactionManager = new JpaTransactionManager(entityManagerFactory);
    return platformTransactionManager;
  }
}
