package com.openclassroom.paymybuddy.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfilDTO {
	
	private String username;
	
	private String email;
	
	private String mdp;

}
