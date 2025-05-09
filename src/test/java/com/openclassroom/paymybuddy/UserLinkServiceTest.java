package com.openclassroom.paymybuddy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.model.UsersLink;
import com.openclassroom.paymybuddy.repository.IUsersLinkRepository;
import com.openclassroom.paymybuddy.repository.IUsersRepository;
import com.openclassroom.paymybuddy.service.UsersLinkService;
import com.openclassroom.paymybuddy.service.UsersService;
import com.openclassroom.paymybuddy.service.ValidationMdp;


public class UserLinkServiceTest {
	
	@Mock
	private IUsersRepository usersRepository;

	@Mock
	private IUsersLinkRepository usersLinkRepository;

	@InjectMocks
	private UsersLinkService usersLinkService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	@Test
	public void testAddUserLinkOK() {
		String email = "moja@email.com";

		Users userSender = createUser("appa@email.com","123RizPaddy", "Appa", 1 );
		Users userReciever = createUser("moja@email.com","123Paddavis", "Moja", 2 );
		when(usersRepository.findById(1)).thenReturn(Optional.of(userSender));
		when(usersRepository.findByEmail(email)).thenReturn(userReciever);
		
		usersLinkService.addUsersLink(email, 1);
		ArgumentCaptor<UsersLink> captor = ArgumentCaptor.forClass(UsersLink.class);
		verify(usersLinkRepository).save(captor.capture());
		UsersLink savedRelation = captor.getValue();
		assertEquals(userReciever, savedRelation.getUserRecieverId());
		assertEquals(userSender, savedRelation.getUserSenderId());	
	}
	@Test
	public void testAddUsersLinkUtilisateurNonTrouve() {
		when(usersRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(RuntimeException.class, () -> {
            usersLinkService.addUsersLink("appa@email.com", 1);
        });
        assertEquals("utilisateur inconnu", exception.getMessage());
	}
	
	@Test
	public void testAddUsersLinkEmailInexistante() {
		Users userSender = createUser("appa@email.com","123RizPaddy", "Appa", 1 );
		when(usersRepository.findById(1)).thenReturn(Optional.of(userSender));
		when(usersRepository.findByEmail("moja@email.com")).thenReturn(null);
		Exception exception = assertThrows(RuntimeException.class, () -> {
			usersLinkService.addUsersLink("moja@email.com", 1);
        });
        assertEquals("la personne que vous recherchez n'existe pas, ou l'email est inccorect", exception.getMessage());
	}
	private Users createUser(String email, String mdp, String username, int id) {
		Users user = new Users();
		user.setEmail(email);
		user.setMdp(mdp);
		user.setUsername(username);
		user.setUserId(id);
		return user;
	}

}
