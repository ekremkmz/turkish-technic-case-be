package com.example.demo.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.example.demo.repository", "com.example.demo.repository.impl"})
@EntityScan(basePackages = {"com.example.demo.repository.dao"})
@Configuration
public class ApplicationConfiguration {
}
