package com.openclassroom.paymybuddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transactions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_transaction")
	private int transactionId;
	
	@Column(name = "id_user_sender")
	private int userSenderId;
	
	@Column (name = "id_user_reciever")
	private int userRecieverId;
	
	@Column (name = "description")
	private String description;
	
	@Column (name = "montant")
	private int montant;
}
