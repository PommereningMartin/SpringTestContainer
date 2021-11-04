package com.example.demo;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.MySQLContainerProvider;

public abstract  class MyMysqlContainer {
    private static MySQLContainer mySQLContainer;

    static {
        mySQLContainer = (MySQLContainer) new MySQLContainerProvider().newInstance()
                .withDatabaseName("test")
                .withUsername("duke")
                .withPassword("s3cret")
                .withReuse(true);

        mySQLContainer.start();
    }

    @DynamicPropertySource
    static void datasourceConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
    }
}
