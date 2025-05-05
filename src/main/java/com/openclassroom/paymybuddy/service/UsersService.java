package com.openclassroom.paymybuddy.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassroom.paymybuddy.dto.InscriptionDTO;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.repository.IUsersRepository;

@Service
public class UsersService {
	
	@Autowired
	IUsersRepository usersRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	ValidationMdp validationMdp;
	
	private static final Logger logger = LogManager.getLogger("UsersService");
	
	@Transactional
	public void inscriptionUser(InscriptionDTO inscriptionDTO) {
		logger.debug("entrée dans la methode inscriptionUser");
		 // je vérifie si l'email existe déjà
        if (usersRepository.existsByEmail(inscriptionDTO.getEmail())) {
        	logger.error("email déjà connu de la base de donnees");
            throw new IllegalArgumentException("cette adresse mail est déjà connue de nos services, merci de vous connecter");
        }
        // je cripte le mot de passe
        String bCryptPasswordEncoder = passwordEncoder.encode(inscriptionDTO.getMdp());
        
        // je vérifie que mon mot de passe est raccord avec les règles de sécurité mises en place
        if(!validationMdp.isValidationMdp(inscriptionDTO.getMdp())) {
        	logger.error("mot de passe trop simple");
    		throw new IllegalArgumentException("le mot de passe est trop simple ! Veuillez en choisir un plus complexe !");
    	}

        // je créer et enregistre le nouvel utilisateur
        Users user = new Users();
        user.setEmail(inscriptionDTO.getEmail());
        user.setMdp(bCryptPasswordEncoder);
        user.setUsername(inscriptionDTO.getUserName());
        user.setRole("USER"); // ici la valeur USER est passée en dur, car nous n'avons qu'un seul type de connexion, mais il serait très facile d'ajouter
        // différents profils, comme par exemple ADMIN, et pouvoir du coup le paramétrer simplement. 
        logger.debug("utilisateur inséré en base, fin de la methode inscriptionUser");
        usersRepository.save(user); 
    }
	
	@Transactional
	public Users updateUser(Users updateUser, int userId) {
		logger.debug("entrée dans la methode updateUser");
		Users userExistant = usersRepository.findById(userId);
		if(userExistant == null) {
			logger.error("utilisateur non trouvé");
			throw new IllegalArgumentException("Utilisateur non trouvé");
		}
	    if(!userExistant.getEmail().equals(updateUser.getEmail()) && !updateUser.getEmail().isEmpty()) {
	    	userExistant.setEmail(updateUser.getEmail());
	    }
	    if(!userExistant.getUsername().equals(updateUser.getUsername()) && !updateUser.getUsername().isEmpty()) {
	    	userExistant.setUsername(updateUser.getUsername());
	    	
	    }
	    if(updateUser.getMdp() != null && !updateUser.getMdp().isEmpty()) {
		    String bCryptPasswordEncoderUpdate = passwordEncoder.encode(updateUser.getMdp());
		    if(!bCryptPasswordEncoderUpdate.equals(userExistant.getMdp())){
		    	if(!validationMdp.isValidationMdp(updateUser.getMdp())) {
		    		logger.error("motdepasse trop simple");
		    		throw new IllegalArgumentException("le mot de passe est trop simple ! Veuillez en choisir un plus complexe !");
		    	}
		    	userExistant.setMdp(bCryptPasswordEncoderUpdate);
		    }
	    }
	    logger.debug("utilisateur modifié en base, fin de la methode updateUser");
	    return usersRepository.save(userExistant);
	}	
}
