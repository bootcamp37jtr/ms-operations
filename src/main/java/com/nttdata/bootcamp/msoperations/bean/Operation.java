package com.nttdata.bootcamp.msoperations.bean;


import org.springframework.data.annotation.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
	
	private String id;
	private String idproduct;
	private String typeAction;  // tipo de operacion ABONO, RETIRO
	private String typeOperation;  // tipo de operacion DEPOSITO, TRANSFERENCIA, RECAUDO, PAGO
	private Double amount;
	private String indDel; // indicador registro eliminado
	private String idproductOriDest;

}
