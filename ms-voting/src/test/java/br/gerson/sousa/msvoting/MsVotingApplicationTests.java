package br.gerson.sousa.msvoting;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@OpenAPIDefinition(info = @Info(title = "MS-Voting", version = "1.2", description = "Api that stores proposal and voting information"))
class MsVotingApplicationTests {

	@Test
	void contextLoads() {
	}

}
