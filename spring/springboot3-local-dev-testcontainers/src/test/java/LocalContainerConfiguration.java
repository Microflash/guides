import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class LocalContainerConfiguration {

	@Bean
	public PostgreSQLContainer<?> postgreSQLContainer(DynamicPropertyRegistry props) {
		var container = new PostgreSQLContainer<>("postgres:16-alpine");
		props.add("spring.datasource.url", container::getJdbcUrl);
		props.add("spring.datasource.username", container::getUsername);
		props.add("spring.datasource.password", container::getPassword);
		return container;
	}
}
