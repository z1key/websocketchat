package config;

import component.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
            .antMatchers("/webjars/**", "/resources/**").permitAll()
            .anyRequest()
                .fullyAuthenticated()
//                .permitAll()
                .and()
            .formLogin()
                .loginPage("/login")
                .successForwardUrl("/")
            .failureUrl("/login?error")
                .permitAll()
                .and()
            .csrf().disable()
                .logout().logoutSuccessHandler(customLogoutSuccessHandler);
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
        auth.inMemoryAuthentication().withUser("user2").password("user").roles("USER");
    }
}
