package ve.com.gem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@EnableHypermediaSupport(type = { HypermediaType.HAL })
public class GemBackendApplication {

	/*
	 * Main app.
	 */
	public static void main(String[] args) {
		SpringApplication.run(GemBackendApplication.class, args);
	
	}
}
