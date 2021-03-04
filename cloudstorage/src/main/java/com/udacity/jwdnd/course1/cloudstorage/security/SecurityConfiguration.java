package com.udacity.jwdnd.course1.cloudstorage.security;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private AuthenticationService authenticationService;
    private EncryptionService encryptionService;

    public SecurityConfiguration(AuthenticationService authenticationService, EncryptionService encryptionService){
        this.authenticationService = authenticationService;
        this.encryptionService = encryptionService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/signup", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/home", true)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }
}
