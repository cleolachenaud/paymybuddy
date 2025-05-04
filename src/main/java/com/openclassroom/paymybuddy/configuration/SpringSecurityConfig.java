package com.openclassroom.paymybuddy.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    return http.authorizeHttpRequests(auth -> {
	        //auth.requestMatchers("/admin").hasRole("ADMIN"); // gère le login
	        auth.requestMatchers("/user").hasRole("USER");
	        auth.anyRequest().authenticated();
	    }).formLogin(Customizer.withDefaults())
	        .logout(logout -> logout // ici on gère la déconnexion
	            .logoutUrl("/logout") // URL de déconnexion
	            .logoutSuccessUrl("/login?logout") // URL de redirection après déconnexion
	            .invalidateHttpSession(true) // invalider la session HTTP
	            .deleteCookies("JSESSIONID")) // supprimer les cookies de session
	        .build();
	}
  

	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
	    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	    authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
	    return authenticationManagerBuilder.build();
	}

}
