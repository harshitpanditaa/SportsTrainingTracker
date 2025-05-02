package com.ncu.sportstrainingtracker.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI myAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sports Training Tracker API")
                        .version("v1")
                        .description("API for managing personal bests"));
    }
}
