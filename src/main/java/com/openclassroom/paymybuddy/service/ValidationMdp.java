package com.openclassroom.paymybuddy.service;

import org.springframework.stereotype.Service;

@Service
public class ValidationMdp {
	

	public boolean isValidationMdp (String mdp) {
		return !mdp.isEmpty() // non vide
				;
	// dans le cadre du POC aucune règle de validation n'est implémentée, je vérifie simplement qu'il n'est pas vide.
	}

}
