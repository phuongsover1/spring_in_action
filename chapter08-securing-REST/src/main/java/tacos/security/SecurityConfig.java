package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .requestMatchers(HttpMethod.POST, "/data-api/ingredients")
                .hasAuthority("SCOPE_writeIngredients")
                .requestMatchers(HttpMethod.PUT, "/data-api/ingredients")
                .hasAuthority("SCOPE_writeIngredients")
                .requestMatchers(HttpMethod.PATCH, "/data-api/ingredients")
                .hasAuthority("SCOPE_writeIngredients")
                .requestMatchers(HttpMethod.DELETE, "/data-api/ingredients")
                .hasAuthority("SCOPE_deleteIngredients")
                .requestMatchers(HttpMethod.GET, "/data-api/tacoOrders")
                .permitAll()
                .and()
                .oauth2ResourceServer(oauth2 -> oauth2.jwt())
                .build();
    }

}
