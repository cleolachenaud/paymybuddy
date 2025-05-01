package com.openclassroom.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.paymybuddy.model.Compte;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.repository.CompteRepository;

@Service
public class CompteService {
	
	@Autowired
	CompteRepository compteRepository;
	
	public Iterable<Compte> getComptes(){
		return compteRepository.findAll();
	}
	
	public Optional<Compte> getCompteById(int id) {
		return compteRepository.findById(id);
	}    

	public Compte saveCompte (Compte compte) {
		return compteRepository.save(compte);
	}
	
	public void deleteCompte (Compte compte) {
		compteRepository.delete(compte);
	}
}
