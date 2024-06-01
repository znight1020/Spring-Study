package hello.springmvc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        Info info = new Info()
                .title("테스트 API 명세서")
                .description("")
                .version("1.0.0")
                .contact(new io.swagger.v3.oas.models.info.Contact()
                        .name("LeeHyeonSoo")
                        .email("znight1020@naver.com")
                        .url("http://edu.ssafy.com"));

        return new OpenAPI().components(new Components()).info(info);
    }


    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder().group("ssafy-admin").pathsToMatch("/**").build();
    }
}
