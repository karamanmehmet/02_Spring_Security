package com.cybertek.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	
	
	
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	

        auth
        .inMemoryAuthentication()
        .withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN")
        .and()
        .withUser("mehmet").password(passwordEncoder().encode("mehmet123")).roles("USER")
        .and()
        .withUser("manager").password(passwordEncoder().encode("manager123")).roles("MANAGER");
         
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("index.html").permitAll()
                .antMatchers("/profile/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/management/index").hasAnyRole("ADMIN","MANAGER")
                .and()
                .httpBasic();
    }
	

    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
}
