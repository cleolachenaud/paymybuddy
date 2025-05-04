package com.openclassroom.paymybuddy.model;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private int userId;
	
	@NotNull
	@Column(name = "username")
	private String username;
	
	@Email
	@NotNull
	@Column(name = "email")
	private String email;
	
	@NotNull
	@Column(name = "mdp")
	private String mdp;
	
	
	@Column (name = "user_role")
	private String role;




}
