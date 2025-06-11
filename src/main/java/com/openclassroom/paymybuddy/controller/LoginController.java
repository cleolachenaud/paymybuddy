package com.openclassroom.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassroom.paymybuddy.configuration.CustomUserDetailsService;
import com.openclassroom.paymybuddy.dto.InscriptionDTO;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.service.UsersService;



@Controller
public class LoginController {
	private static final Logger logger = LogManager.getLogger("LoginController");
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private UsersService userService;
	
	
    @GetMapping("/inscription")
    public String inscription(Model model) {
    	logger.info("GetMapping /connexion");
        model.addAttribute("inscriptionDTO", new InscriptionDTO()); // Ajoute un nouvel utilisateur au modèle
        return "inscription"; // Renvoie la page HTML login.html
    }
	
	@PostMapping(value="/inscription")
	public String inscription(@ModelAttribute InscriptionDTO inscriptionDTO, RedirectAttributes redirectAttributes) {
		logger.info("@/Inscription : " + inscriptionDTO.toString());
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
	    return "redirect:/custom-login";  // Redirige vers la page de connexion après l'inscription
	}

    @GetMapping("")
    public String redirectCustomLogin(Model model) {
    	logger.info("GetMapping /connexion");
        model.addAttribute("user", new Users()); // Ajoute un nouvel utilisateur au modèle
        return "custom-login"; // Renvoie la page HTML login.html
    }
    
    @GetMapping("/custom-login")
    public String showLoginForm(Model model) {
    	logger.info("GetMapping /connexion");
        model.addAttribute("user", new Users()); // Ajoute un nouvel utilisateur au modèle
        return "custom-login"; // Renvoie la page HTML login.html
    }
    
    @PostMapping(value="/custom-login")
    public String connexion(@ModelAttribute Users user, Model model, RedirectAttributes redirectAttributes) {
        logger.info("@RequestMapping /login ");
        logger.info("@RequestMapping /login " + user.toString());
        // Utilisation du service pour vérifier les identifiants
        UserDetails userDetail = customUserDetailsService.loadUserByUsername(user.getEmail());
        if (userDetail != null) {
            return "/transactions"; // Redirige vers la page d'accueil
        } else {
        	redirectAttributes.addFlashAttribute("errorMessage", "Erreur, l'email ou le mot de passe est inconnu ");
            return "redirect:/custom-login"; // Renvoie la page login
        }
    }
    
    @GetMapping("/logout")
    public String logout(Model model) {
    	logger.info("GetMapping /connexion");
        model.addAttribute("user", new Users()); // Ajoute un nouvel utilisateur au modèle
        return "logout"; // Renvoie la page HTML login.html
    }
    
}
