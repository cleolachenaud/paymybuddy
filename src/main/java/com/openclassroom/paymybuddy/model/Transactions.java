package com.openclassroom.paymybuddy.model;


import javax.validation.constraints.NotNull;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity

@Table(name = "transactions")
public class Transactions {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_transaction")
	private int transactionId;
	
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
	
	@Column (name = "description")
	private String description;
	
	@NotNull
	@Column (name = "montant")
	private double montant;
	
}
