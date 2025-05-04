package com.openclassroom.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassroom.paymybuddy.dto.InscriptionDTO;
import com.openclassroom.paymybuddy.dto.LoginDTO;
import com.openclassroom.paymybuddy.model.Transactions;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.repository.IUsersRepository;

@Service
public class UsersService {
	
	@Autowired
	IUsersRepository usersRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Iterable<Users> getUsers(){
		return usersRepository.findAll();
	}

	public Users findByEmail (String email) {
		return usersRepository.findByEmail(email);
	}
	public Users findById (int id) {
		return usersRepository.findById(id);
	}
		
	public void deleteUser (Users user) {
		usersRepository.delete(user);
	}

	public void inscriptionUser(InscriptionDTO inscriptionDTO) {
		 // je vérifie si l'email existe déjà
        if (usersRepository.existsByEmail(inscriptionDTO.getEmail())) {
            throw new IllegalArgumentException("cette adresse mail est déjà connue de nos services, merci de vous connecter");
        }
        // je cripte le mot de passe
        String bCryptPasswordEncoder = passwordEncoder.encode(inscriptionDTO.getMdp());

        // je créer et enregistre le nouvel utilisateur
        Users user = new Users();
        user.setEmail(inscriptionDTO.getEmail());
        user.setMdp(bCryptPasswordEncoder);
        user.setUsername(inscriptionDTO.getUserName());
        user.setRole("USER"); // ici la valeur USER est passée en dur, car nous n'avons qu'un seul type de connexion, mais il serait très facile d'ajouter
        // différents profils, comme par exemple ADMIN, et pouvoir du coup le paramétrer simplement. 

        usersRepository.save(user); 
    }
	
}
