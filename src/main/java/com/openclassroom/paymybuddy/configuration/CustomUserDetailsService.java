package com.openclassroom.paymybuddy.configuration;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.repository.IUsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private static final Logger logger = LogManager.getLogger("CustomUserDetailsService");

	@Autowired
    private IUsersRepository usersRepository;
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.debug("entrée dans la méthode loadUserByUsername");
        Users user = usersRepository.findByEmail(email);
        if(user == null) {
        	  logger.debug("Utilisateur non trouvé : " + email);
              throw new UsernameNotFoundException("Utilisateur non trouvé : " + email);
        }
        logger.debug("sortie de la méthode loadUserByUsername");
        return new User(user.getEmail(), user.getMdp(), getGrantedAuthorities(user.getRole()));        
    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
    	logger.debug("entrée dans la méthode getGrantedAuthorities");
    	role = "user";
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        logger.debug("sortie de la méthode getGrantedAuthorities");
        return authorities;
    }	    
	
}
