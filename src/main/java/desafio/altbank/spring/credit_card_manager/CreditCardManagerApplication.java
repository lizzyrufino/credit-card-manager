package desafio.altbank.spring.credit_card_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class CreditCardManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditCardManagerApplication.class, args);
	}

}
