package com.openclassroom.paymybuddy.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassroom.paymybuddy.dto.InscriptionDTO;
import com.openclassroom.paymybuddy.dto.ProfilDTO;
import com.openclassroom.paymybuddy.dto.TransactionsDTO;
import com.openclassroom.paymybuddy.dto.AjouterRelationDTO;
import com.openclassroom.paymybuddy.model.Transactions;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.model.UsersLink;
import com.openclassroom.paymybuddy.service.UsersService;

@Controller
@RequestMapping("/profil")
public class UsersController {
	
	private static final Logger logger = LogManager.getLogger("UsersController");
	
	@Autowired
	private UsersService usersService;
	
	@GetMapping("") // <== seconde partie de l'URL
	public String getUser(Model model) {
		logger.info("@GetMapping getUser");
		model.addAttribute("reponse", new ProfilDTO());
      
		return "profil"; // <== fichier .html à charger
	}

	@PostMapping(value="")
	public String updateUser(@ModelAttribute("reponse") ProfilDTO reponse, Model model, RedirectAttributes redirectAttributes) {
	    
		int id = usersService.getAuthenticatedUser().getUserId();
		if (id < 0 || reponse == null) {
	        logger.error("updateUser. ID ou utilisateur invalide");
	        redirectAttributes.addFlashAttribute("errorMessage", "ID ou utilisateur non valide");
	        return "redirect:/profil";
	    }

	    Users user = new Users();
	    user.setEmail(reponse.getEmail());
	    user.setMdp(reponse.getMdp());
	    user.setUsername(reponse.getUsername());
        logger.info("Id = " + id);
        logger.info("User = " + user.toString());
	    
	    try {
	        usersService.updateUser(user, id);
	    } catch (RuntimeException e) {
	        logger.error("updateUser. mise a jour non effectuée pour l'utilisateur ID: " + id);
	        redirectAttributes.addFlashAttribute("errorMessage", "mise à jour non effectuée");
	        return "redirect:/profil";
	    }

	    logger.info("updateUser. mise a jour effectuée pour l'utilisateur ID: " + id);
	    redirectAttributes.addFlashAttribute("successMessage", "mise à jour effectuée");
        return "redirect:/profil";
	}
}
