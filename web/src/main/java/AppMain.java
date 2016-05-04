import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by pan on 16/2/25.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "paner.controller,paner.service,paner.das.mapper")
@ImportResource("classpath:mybatis-context.xml")
public class AppMain {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppMain.class, args);
    }
}
