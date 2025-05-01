package com.openclassroom.paymybuddy.model;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "users_link")
public class UsersLink {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user_link")
	private int userLinkId;
	
	@ManyToOne (
			cascade = {
					CascadeType.PERSIST, //CascadeType nécessaires pour l’insertion et la modification
					CascadeType.MERGE
					}, 
		fetch = FetchType.LAZY) //a la récupération de Users les transactions seront récupérées à la demande
	@JoinColumn(name = "id_user_sender", nullable = false) 
	private Users userSenderId;
		
	@ManyToOne (
			cascade = {
					CascadeType.PERSIST, //CascadeType nécessaires pour l’insertion et la modification
					CascadeType.MERGE
					},  
		fetch = FetchType.LAZY) 
	@JoinColumn(name = "id_user_reciever", nullable = false) 
	private Users userRecieverId;
	

}
