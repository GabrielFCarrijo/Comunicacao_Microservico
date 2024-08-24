package br.com.microservico.product_api.config.exception.interceptor;

import br.com.microservico.product_api.modules.jwt.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }

    @Bean
    public AuthInterceptor authInterceptor() {
     return new AuthInterceptor(jwtService());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor());
    }
}
