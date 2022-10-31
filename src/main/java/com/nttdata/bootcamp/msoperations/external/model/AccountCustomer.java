package com.nttdata.bootcamp.msoperations.external.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCustomer {
	
	private String id;
	private String productId;
	private String numberAccount; // numero de cuenta
	private String numberInterbankAccount; // numero de cuenta interbancaria
	private Double availableBalance; // saldo disponible
	private Double contableBalance; // saldo contable
	private String customerId;
	private String indDel; // indicador registro eliminado
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	
	private Integer maxQuantOperComisiion;
	private Double comissionvalue;
	 
}
