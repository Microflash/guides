import com.example.localdev.Launcher;
import org.springframework.boot.SpringApplication;

public class LocalLauncher {

	public static void main(String... args) {
		SpringApplication
				.from(Launcher::main)
				.with(LocalContainerConfiguration.class)
				.run(args);
	}
}
