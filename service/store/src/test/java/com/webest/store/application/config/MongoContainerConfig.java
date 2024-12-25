package com.webest.store.application.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.mongodb.MongoDBAtlasLocalContainer;

@TestConfiguration
public class MongoContainerConfig implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        startMongoConfig();

    }

    private void startMongoConfig() {

        String dbName = "testdb";
        MongoDBAtlasLocalContainer mongoContainer = new MongoDBAtlasLocalContainer(
            "mongodb/mongodb-atlas-local:7.0.9"
        );

        mongoContainer.start();

        String[] connectionArray = mongoContainer.getConnectionString().split("\\?");

        System.setProperty("spring.data.mongodb.uri",
            connectionArray[0] + dbName + connectionArray[1]);
        System.setProperty("spring.data.mongodb.authentication-database", dbName);

    }
}
