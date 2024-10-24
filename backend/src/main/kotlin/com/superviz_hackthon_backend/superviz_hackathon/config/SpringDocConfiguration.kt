package com.superviz_hackthon_backend.superviz_hackathon.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme

@org.springframework.context.annotation.Configuration
class SpringDocConfiguration {

    @org.springframework.context.annotation.Bean fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .components(Components()
                .addSecuritySchemes("bearer-key",
                    SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
            .info(Info()
                .title("CollabNotes")
                )
    }
}