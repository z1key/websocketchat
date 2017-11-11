package config;

import domain.UserMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by UserMessage on 29.10.2017.
 */
@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
@ComponentScan(basePackages = {"controller", "config", "component"})
public class Application {

    public static Set<UserMessage> users = new HashSet<>();
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
