package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
            .antMatchers("/webjars/**", "/resources/**").permitAll()
            .anyRequest()
                .fullyAuthenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .successForwardUrl("/")
            .failureUrl("/login?error")
                .permitAll()
                .and()
            .csrf().disable()
            .logout()
                .permitAll();
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
    }
}
