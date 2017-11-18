package config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by UserMessage on 29.10.2017.
 */
@SpringBootApplication
@EnableScheduling
//@EnableAsync(proxyTargetClass = true)
//@EnableCaching(proxyTargetClass = true)
@ComponentScan(basePackages = {"controller", "config", "component"})
public class Application {

    public static final Set<String> users = new HashSet<>();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
