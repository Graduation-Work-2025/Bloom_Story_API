package bloom_story;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BloomStoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloomStoryApplication.class, args);
	}

}
