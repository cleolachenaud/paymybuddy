package com.openclassroom.paymybuddy.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassroom.paymybuddy.dto.TransactionsDTO;
import com.openclassroom.paymybuddy.model.Transactions;
import com.openclassroom.paymybuddy.model.UsersLink;
import com.openclassroom.paymybuddy.repository.ITransactionsRepository;
import com.openclassroom.paymybuddy.service.TransactionsService;
import com.openclassroom.paymybuddy.service.UsersLinkService;
import com.openclassroom.paymybuddy.service.UsersService;

@Controller
@RequestMapping("/transactions")
public class TransactionsController {
	
	private static final Logger logger = LogManager.getLogger("TransactionsController");
	
	@Autowired
    TransactionsService transactionService;
	
	@Autowired
	ITransactionsRepository transactionRepository;

	@Autowired
	UsersLinkService userLinkService;
	@Autowired
	UsersService usersService;
    
    @GetMapping("") // <== seconde partie de l'URL
	public String getUserLink(Model model) {
		logger.info("@GetMapping getUserLink");
		
		List<UsersLink> listUsersLink = userLinkService.getUsersLink(usersService.getAuthenticatedUser().getUserId());
		logger.info("liste des relations"+ listUsersLink.toString());
		model.addAttribute("listUsersLink",listUsersLink);
		
		Iterable<Transactions> listTransactions = transactionService.getTransactionsByUser(usersService.getAuthenticatedUser().getUserId());
		model.addAttribute("transactions", listTransactions);
		
		model.addAttribute("reponse", new TransactionsDTO());
      
		return "transactions"; // <== fichier .html à charger
	}
    

    @PostMapping(value="")
    public String transferMoney(@ModelAttribute("reponse") TransactionsDTO reponse, Model model,  RedirectAttributes redirectAttributes) {
        try {
            transactionService.transferMoney(usersService.getAuthenticatedUser().getUserId(), reponse.getUserID(), reponse.getMontant(), reponse.getDescription());
        } catch (RuntimeException e) {
        	logger.error("transferMoney. Transfert d'argent n'a pas abouti");
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur : " + e.getMessage());
            return "redirect:/transactions"; // Redirection en cas d'erreur
        }
        logger.info("transferMoney. Transfert d'argent réussi");
        redirectAttributes.addFlashAttribute("successMessage", "Transaction réussie !"); 
        return "redirect:/transactions"; // Redirection après succès  
    }
}
