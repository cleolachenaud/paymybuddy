package com.openclassroom.paymybuddy.service;

import org.springframework.stereotype.Service;

@Service
public class ValidationMdp {
	
	public boolean isValidationMdp (String mdp) {
		return !mdp.isEmpty(); // non vide
		// autres règles de validation à implémenter au besoin
	}

}
