package bck_rag_chat.configuration;


import bck_rag_chat.user.userDetail.CustomUserDetailsService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JwtBuilder getJwtBuilder() {
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes());
    }

    @Bean
    public JwtParser getJwtParser() {
        return Jwts.parser().setSigningKey(SECRET_KEY);
    }
}
