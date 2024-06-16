package com.trivago.casestudy.configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.trivago.casestudy.repository")
public class PersistenceConfig {
}
