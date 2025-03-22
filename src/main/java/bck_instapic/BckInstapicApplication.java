package bck_instapic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value="classpath:/${properties.service.path}/db.properties")
@PropertySource(value="classpath:/${properties.service.path}/jwt.properties")
@PropertySource(value="classpath:/${properties.service.path}/aws.properties")
@PropertySource(value="classpath:/${properties.service.path}/system.properties")
@PropertySource(value="classpath:/${properties.service.path}/stripe.properties")
@PropertySource(value="classpath:/${properties.service.path}/trainings.bucket.properties")
public class BckInstapicApplication {
	public static void main(String[] args) {
		SpringApplication.run(BckInstapicApplication.class, args);
	}
}
