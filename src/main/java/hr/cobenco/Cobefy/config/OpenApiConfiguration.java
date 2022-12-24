package hr.cobenco.Cobefy.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfiguration {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Song Management")
                                .description("Upload & download songs")
                                .version("1.0")
                                .license(
                                        new License()
                                                .name("Apache 2.0")
                                                .url("http://www.apache.org/licenses/LICENSE-2.0")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Song Management")
                .pathsToMatch("/api/song/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicApiSongFiles() {
        return GroupedOpenApi.builder()
                .group("Song Files Management")
                .pathsToMatch("/api/song-file/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicApiImageFiles() {
        return GroupedOpenApi.builder()
                .group("Image Files Management")
                .pathsToMatch("/api/image-file/**")
                .build();
    }

    @Bean
    public GroupedOpenApi UsersApi() {
        return GroupedOpenApi.builder()
                .group("Users")
                .pathsToMatch("/api/users/**")

                .build();
    }

    @Bean
    public GroupedOpenApi AuthApi() {
        return GroupedOpenApi.builder()
                .group("Authentication")
                .pathsToMatch("/api/token/**")

                .build();
    }


}
