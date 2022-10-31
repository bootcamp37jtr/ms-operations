package com.nttdata.bootcamp.msoperations.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
	
	@Id
	private String id;
	private String idproduct;
	private String typeAction;  // tipo de operacion ABONO, RETIRO
	private String typeOperation;  // tipo de operacion DEPOSITO, TRANSFERENCIA, RECAUDO, PAGO
	private Double amount;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	private String indDel; // indicador registro eliminado
	private String idproductOriDest;
	@Transient
	private String numberCard;
	
}
