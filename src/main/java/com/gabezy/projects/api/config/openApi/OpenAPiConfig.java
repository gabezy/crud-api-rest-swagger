package com.gabezy.projects.api.config.openApi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(info());
    }

    private Info info() {
       // config the title, description, version and other elements of the API
        return new Info().title("CRUD API Rest")
                .description("Simple CRUD API")
                .version("1.0")
                .termsOfService("Terms of Service")
                .summary("This Summary");

    }
}
