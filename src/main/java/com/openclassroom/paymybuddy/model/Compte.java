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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "compte")
public class Compte {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_compte")
	private int compteId;
	
	//TODO a voir si on fait en sorte que le compte supprime le user ou non 
	@OneToOne(
	cascade = CascadeType.ALL, //toutes les actions sur l'entité Users seront propagées sur l'entité Compte
	orphanRemoval = true, // pas de Compte orphelin de son User
	fetch = FetchType.EAGER) //a la récupération de Users le compte assoicié est récupéré
	@JoinColumn(name = "id_user_compte")
	private Users userCompteId;
	
	@NotNull
	@Column(name = "solde_compte")
	private double soldeCompte;
	
}
