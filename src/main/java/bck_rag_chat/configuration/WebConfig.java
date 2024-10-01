package bck_rag_chat.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/chat")
                .allowedOrigins("http://localhost:3000") // React app origin
                .allowedMethods("POST")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
