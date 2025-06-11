package com.openclassroom.paymybuddy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.openclassroom.paymybuddy.dto.InscriptionDTO;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.repository.IUsersRepository;
import com.openclassroom.paymybuddy.service.UsersService;
import com.openclassroom.paymybuddy.service.ValidationMdp;


public class UsersServiceTest {
	@Mock
	private IUsersRepository usersRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private ValidationMdp validationMdp;

	@InjectMocks
	private UsersService userService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	    
	@Test
	public void testInscriptionUserEmailDejaConnu() {
	    InscriptionDTO inscriptionDTO = new InscriptionDTO();
	    inscriptionDTO.setEmail("appa@email.com");
	    when(usersRepository.existsByEmail(inscriptionDTO.getEmail())).thenReturn(true);

	    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	        userService.inscriptionUser(inscriptionDTO);
	    });

	    assertEquals("cette adresse mail est déjà connue de nos services, merci de vous connecter", exception.getMessage());
	}
	
	@Test
	public void testInscriptionUserMotDePasseTropSimple() {
	    InscriptionDTO inscriptionDTO = new InscriptionDTO();
	    inscriptionDTO.setEmail("appa@email.com");
	    inscriptionDTO.setMdp(" "); // Mot de passe 
	    when(usersRepository.existsByEmail(inscriptionDTO.getEmail())).thenReturn(false);
	    when(validationMdp.isValidationMdp(inscriptionDTO.getMdp())).thenReturn(false);

	    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	        userService.inscriptionUser(inscriptionDTO);
	    });

	    assertEquals("le mot de passe est trop simple ! Veuillez en choisir un plus complexe !", exception.getMessage());
	}
	
	@Test
	public void testInscriptionUserOk() {
	    InscriptionDTO inscriptionDTO = new InscriptionDTO();
	    inscriptionDTO.setEmail("appa@email.com");
	    inscriptionDTO.setMdp("123RizPaddy");
	    inscriptionDTO.setUsername("Appa");
	    
	    when(usersRepository.existsByEmail(inscriptionDTO.getEmail())).thenReturn(false);
	    when(validationMdp.isValidationMdp(inscriptionDTO.getMdp())).thenReturn(true);
	    when(passwordEncoder.encode(inscriptionDTO.getMdp())).thenReturn("encodedPassword");

	    userService.inscriptionUser(inscriptionDTO);

	    verify(usersRepository).save(any(Users.class));  
	}
	
	@Test
	public void testUpdateUserOK() {
		Users user = new Users();
		user.setEmail("appa@email.com");
		user.setMdp("123RizPaddy");
		user.setUsername("Appa");
		user.setUserId(1);
		
		Users updateUser = createUser("appaLeFruit@email.com","", "Appa", 1 );
		when(usersRepository.findById(1)).thenReturn(Optional.of(user));
		
	    userService.updateUser(updateUser, 1);
		ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
		verify(usersRepository).save(captor.capture());
		Users savedUser = captor.getValue();
		assertEquals(updateUser.getEmail(), savedUser.getEmail());
		assertEquals(updateUser.getUsername(), savedUser.getUsername());
		
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
