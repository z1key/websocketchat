package config;

import com.google.common.collect.Sets;
import domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Set;

@SpringBootApplication(exclude={org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.class})
@EnableScheduling
//@EnableAsync(proxyTargetClass = true)
//@EnableCaching(proxyTargetClass = true)
@ComponentScan(basePackages = {"controller", "config", "component"})
public class Application {

    public static final Set<User> users = Sets.newConcurrentHashSet();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
