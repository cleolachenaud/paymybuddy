package com.openclassroom.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassroom.paymybuddy.dto.InscriptionDTO;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.service.UsersService;

@RestController
public class UsersController {
	
	private static final Logger logger = LogManager.getLogger("UsersController");
	
	@Autowired
	private UsersService userService;

	@PostMapping("/inscription")
	public String inscription(@ModelAttribute InscriptionDTO inscriptionDTO, RedirectAttributes redirectAttributes) {
		// RedirectAttribute permet le stockage temporaire des infos, notamment pour préciser à l'utilisateur si tout s'est bien passé, ou non
	    try {
	        userService.inscriptionUser(inscriptionDTO);
	        redirectAttributes.addFlashAttribute("successMessage", "Inscription réussie !");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'inscription : " + e.getMessage());
	        logger.error("Erreur lors de l'inscription");
	        return "redirect:/inscription";  // Redirige vers la page d'inscription en cas d'erreur
	    }
	    logger.info("Inscription réussie !");
	    return "redirect:/login";  // Redirige vers la page de connexion après l'inscription
	}


	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody Users updatedUser) {
	    if (id <= 0 || updatedUser == null) {
	        logger.error("updateUser. ID ou utilisateur invalide");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : ID ou utilisateur non valide");
	    }

	    Users user;
	    try {
	        user = userService.updateUser(updatedUser, id);
	    } catch (RuntimeException e) {
	        logger.error("updateUser. mise a jour non effectuée pour l'utilisateur ID: " + id);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erreur : " + e.getMessage());
	    }

	    logger.info("updateUser. mise a jour effectuée pour l'utilisateur ID: " + id);
	    return ResponseEntity.ok(user);
	}
}
