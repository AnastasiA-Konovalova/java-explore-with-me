import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.practicum.client")
public class StatsClientApp {
    public static void main(String[] args) {
        SpringApplication.run(StatsClientApp.class, args);
    }
}
