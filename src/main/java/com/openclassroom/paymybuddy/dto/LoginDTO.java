package com.openclassroom.paymybuddy.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginDTO {
    @Email
	@NotNull
	private String email;

	@NotNull
	private String mdp;
		
}
