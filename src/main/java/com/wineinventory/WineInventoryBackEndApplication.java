package com.wineinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * WineInventory Back End Application - WineInventory Platform
 *
 * @summary
 * This is the main class for the WineInventory Platform application.
 * It is responsible for bootstrapping the application and configuring the necessary components.
 * It enables JPA auditing and starts the Spring Boot application.
 *
 * @since 1.0
 */
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class WineInventoryBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(WineInventoryBackEndApplication.class, args);
    }

}