package com.openclassroom.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassroom.paymybuddy.dto.TransactionsDTO;
import com.openclassroom.paymybuddy.dto.AjouterRelationDTO;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.service.UsersLinkService;
import com.openclassroom.paymybuddy.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ajouterRelation")
public class UsersLinkController {
	
	private static final Logger logger = LogManager.getLogger("UsersLinkController");
	
	@Autowired
    private UsersLinkService usersLinkService;
	@Autowired
	UsersService usersService;

    @PostMapping("")
    public String addUsersLink(@ModelAttribute("reponse") AjouterRelationDTO reponse, Model model, RedirectAttributes redirectAttributes) {
    	int userSenderId = usersService.getAuthenticatedUser().getUserId(); // Récupération de l'ID d'utilisateur depuis la session
        try {
        	usersLinkService.addUsersLink(reponse.getEmail(), userSenderId);
        } catch (RuntimeException e) {
        	logger.error("la relation n'a pas été ajoutée");
        	redirectAttributes.addFlashAttribute("errorMessage", "Erreur : " + e.getMessage());
            return "redirect:/ajouterRelation"; // Redirection en cas d'erreur
        }
        logger.info("la relation est ajoutée");
        redirectAttributes.addFlashAttribute("successMessage", "Ajout de la relation réussie !"); 
        return "redirect:/ajouterRelation"; // Redirection après succès 
    }
    
    @GetMapping("") // <== seconde partie de l'URL
	public String getUserLink(Model model) {
		logger.info("@GetMapping getUserLink");
		model.addAttribute("reponse", new AjouterRelationDTO());
		return "ajouterRelation"; // <== fichier .html à charger
	}


}
