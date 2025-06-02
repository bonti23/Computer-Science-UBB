package bonti.start;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication(scanBasePackages = {"bonti"})
@ComponentScan("bonti")
@EnableJpaRepositories(basePackages = "bonti")
@EntityScan(basePackages = "bonti.model")
public class StartRestServices {
    public static void main(String[] args) {
        SpringApplication.run(StartRestServices.class, args);
    }
    @Bean(name="props")
    public Properties getProperties() {
        Properties props = new Properties();
        String configFilePath = "C:\\Intellij Projects\\basket_orm\\RestServices\\src\\main\\resources\\bd.config";
        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            System.out.println("Loading bd.config from " + configFilePath);
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Configuration file bd.config not found or cannot be loaded: " + e.getMessage());
        }
        return props;
    }
}
