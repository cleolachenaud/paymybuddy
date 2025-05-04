package com.openclassroom.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.paymybuddy.dto.InscriptionDTO;
import com.openclassroom.paymybuddy.service.UsersService;

@RestController
public class InscriptionController {
	
	@Autowired
	private UsersService userService;

	@PostMapping("/inscription")
	public String inscription(@ModelAttribute InscriptionDTO inscriptionDTO) {
	        userService.inscriptionUser(inscriptionDTO);
	        return "redirect:/login";  // redirige vers la page de connexion après l'inscription
	    }

}
