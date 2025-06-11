package com.openclassroom.paymybuddy.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity//(debug = true)
public class SpringSecurityConfig {

	private static final Logger logger = LogManager.getLogger("SpringSecurityConfig");

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)  throws Exception {

		  return http.authorizeHttpRequests(auth -> {
		  	logger.debug("login securityFilterChain"); 

            auth.requestMatchers("/css/**").permitAll();
		  
		  	auth.requestMatchers("/inscription").permitAll();
		  	auth.requestMatchers("/user").hasRole("USER");
		  	auth.anyRequest().authenticated();
		  	
		  	logger.debug("logout securityFilterChain");
		  })
		  //.formLogin(Customizer.withDefaults())
		  .formLogin(login -> login.loginPage("/custom-login")
			  .loginProcessingUrl("/custom-login")
	          .defaultSuccessUrl("/transactions", true)
	          .permitAll()
	      )
		  .logout(logout -> logout // ici on gère la déconnexion
		  	.logoutUrl("/logout") // URL de déconnexion
		  	.logoutSuccessUrl("/custom-login") // URL de redirection après déconnexion    .logoutSuccessUrl("/login?logout")
		  	.invalidateHttpSession(true) // invalider la session HTTP
		  	.deleteCookies("JSESSIONID") // supprimer les cookies de session
		  )
          .authenticationProvider(authProvider())
		  .build();
		 
	}


	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
		return authenticationManagerBuilder.build();
	}

	@Bean
	public AuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(customUserDetailsService.passwordEncoder());
	    return authProvider;
	}
	
}
