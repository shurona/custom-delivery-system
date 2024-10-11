package com.webest.order.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.webest.order", //mysql을 사용하는 폴더 이름
        entityManagerFactoryRef = "mysqlEntityManagerFactory", //entityManager 이름
        transactionManagerRef = "mysqlTx") // transactionManager 이름
@EnableTransactionManagement
public class JpaConfig {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl_auto;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;



    @Primary
    @Bean  // (1)
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(mysqlDatasource()); //Datasource 설정
        em.setPackagesToScan("com.webest.order.domain.model"); //mysql을 사용하는 entity 패키지 설정

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        em.setJpaVendorAdapter(vendorAdapter); // JPA 구현체인 hibernate를 사용하기 위해 등록해준다.

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", ddl_auto);
        properties.put("hibernate.show_sql","true");
        properties.put("hibernate.format_sql","true");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean  // (2)
    public DataSource mysqlDatasource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }


    @Primary
    @Bean(name = "mysqlTx")
    public PlatformTransactionManager mysqlTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(mysqlEntityManagerFactory().getObject());
        return transactionManager;
    }

}
