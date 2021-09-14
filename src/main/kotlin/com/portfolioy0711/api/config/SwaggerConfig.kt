package com.portfolioy0711.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun productApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
                .apiInfo(this.metaInfo())
    }

    private fun metaInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Triple Club Mileage Service")
                .description("Restful API Reference")
                .version("1.0")
                .build()
    }
}


