package com.openclassroom.paymybuddy.dto;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class InscriptionDTO {
	@NotNull
	private String username;
	
    @Email
	@NotNull
	private String email;

	@NotNull
	private String mdp;
}
