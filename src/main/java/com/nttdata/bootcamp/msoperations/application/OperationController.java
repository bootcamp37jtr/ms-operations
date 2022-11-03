package com.nttdata.bootcamp.msoperations.application;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.msoperations.model.Operation;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/operations")
@RefreshScope
public class OperationController {
	

	@Value("${message.demo}")
	private String demoString;

	
	@Autowired
	OperationService operationService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Operation> crear(@RequestBody Operation operation) {
		log.info(demoString);
		log.info(operation.getNumberCard());
		operation.setCreatedAt(new Date());
		return operationService.insertOperation(Mono.just(operation));
	}
	
	/**
	 * 	Implementar un reporte con los últimos 10 movimientos de la tarjeta de débito y de crédito
	 * */
	@GetMapping
	public  Mono<ResponseEntity<Flux<Operation>>> retrieveOperations(
			@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
			@RequestParam(value = "productid") String id) {
		
		log.info(pageNo+"-"+pageSize+"-"+sortBy+"-"+id);
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(operationService.findByIdproduct(pageNo, pageSize, sortBy, id))) ;
	}
	

	
	/**
	 * 	Generar un reporte de todas las comisiones cobradas por producto en un periodo de tiempo
	 */
	@GetMapping("/comissions/{idproduct}")
	public Flux<Operation> retrieveOperationComision(@PathVariable String idproduct,
			@RequestParam String startDate, @RequestParam  String endDate) {
		log.info(idproduct);
		return operationService.retrieveComissions(idproduct, startDate, endDate);
	}
	
	
}
