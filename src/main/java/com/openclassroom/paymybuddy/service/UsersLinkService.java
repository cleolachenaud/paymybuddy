package com.openclassroom.paymybuddy.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.model.UsersLink;
import com.openclassroom.paymybuddy.repository.IUsersLinkRepository;
import com.openclassroom.paymybuddy.repository.IUsersRepository;

@Service
public class UsersLinkService {

	@Autowired
	IUsersLinkRepository usersLinkRepository;

	@Autowired
	IUsersRepository usersRepository;

	private static final Logger logger = LogManager.getLogger("UsersLinkService");
	@Transactional
	public void addUsersLink(String emailAdd, int userSenderId) {
		logger.info("début de la méthode addUsersLink");
		// je vérifie que les utilisateurs existent sur l'application
		Users userSender = usersRepository.findById(userSenderId)
				.orElseThrow(() -> new RuntimeException("utilisateur inconnu"));
				logger.error("utilisateur inconnu");

		Users userReceiver = usersRepository.findByEmail(emailAdd);
		if (userReceiver == null) {
			logger.error("email inccorect ou inexistant en base");
			throw new RuntimeException("la personne que vous recherchez n'existe pas, ou l'email est inccorect");
		}

		// j'ajoute la relation entre les deux
		UsersLink relationship = new UsersLink(userSender, userReceiver);

		// j'enregistre la relation
		usersLinkRepository.save(relationship);
		logger.info("fin de la méthode addUsersLink");
	}

}
