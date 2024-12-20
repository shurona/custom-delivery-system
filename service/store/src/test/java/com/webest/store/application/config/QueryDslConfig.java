package com.webest.store.application.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.webest.store.store.domain.repository.CustomStoreRepository;
import com.webest.store.store.infrastructure.querydsl.CustomStoreRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@TestConfiguration
public class QueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public CustomStoreRepository customStoreRepository() {
        return new CustomStoreRepositoryImpl(jpaQueryFactory());
    }


}
