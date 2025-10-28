package com.wineinventory.shared.infrastructure.spa.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class FrontendConfiguration {

    @Value("${base-url}")
    private String baseUrl;
}
