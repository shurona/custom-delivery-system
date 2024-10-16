package com.webest.delivery.infrastructure.configuration;

import static com.webest.web.common.CommonStaticVariable.SWAGGER_TITLE;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Components components = new Components();

        return new OpenAPI()
            .addServersItem(new Server().url("/"))
            .info(info())
            .components(components);

    }

    private Info info() {
        return new Info()
            .title(SWAGGER_TITLE)
            .version("0.0.1");
    }
}
