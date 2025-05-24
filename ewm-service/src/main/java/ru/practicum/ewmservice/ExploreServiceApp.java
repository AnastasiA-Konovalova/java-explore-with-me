package ru.practicum.ewmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "ru.practicum.ewmservice")
@SpringBootApplication(scanBasePackages = {
        "ru.practicum.ewmservice",
        "ru.practicum.stats" // добавить этот пакет!
})
public class ExploreServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(ExploreServiceApp.class, args);
    }
}