package com.openclassroom.paymybuddy.controller;

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
	
	@Autowired
    private UsersLinkService usersLinkService;

    @PutMapping("/addUsersLink")
    public ResponseEntity<String> addUsersLink(@RequestParam String emailAdd,
                                               @RequestParam int userSenderId) {
        try {
        	usersLinkService.addUsersLink(emailAdd, userSenderId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
        }
        return ResponseEntity.ok("la relation est ajoutée !");
    }

}
