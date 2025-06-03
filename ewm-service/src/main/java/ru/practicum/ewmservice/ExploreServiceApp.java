package ru.practicum.ewmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "ru.practicum.ewmservice")
@SpringBootApplication(scanBasePackages = {
        "ru.practicum.ewmservice",
        "ru.practicum.stats"
})
public class ExploreServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(ExploreServiceApp.class, args);
    }
}