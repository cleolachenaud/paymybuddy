package com.openclassroom.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.paymybuddy.service.UsersLinkService;

@RestController
@RequestMapping("/usersLink")
public class UsersLinkController {
	
	private static final Logger logger = LogManager.getLogger("UsersLinkController");
	
	@Autowired
    private UsersLinkService usersLinkService;

    @PutMapping("/addUsersLink")
    public ResponseEntity<String> addUsersLink(@RequestParam String emailAdd,
                                               @RequestParam int userSenderId) {
        try {
        	usersLinkService.addUsersLink(emailAdd, userSenderId);
        } catch (RuntimeException e) {
        	logger.error("la relation n'a pas été ajoutée");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
        }
        logger.info("la relation est ajoutée");
        return ResponseEntity.ok("la relation est ajoutée !");
    }

}
