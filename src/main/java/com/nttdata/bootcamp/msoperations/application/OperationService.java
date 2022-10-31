package com.nttdata.bootcamp.msoperations.application;

import com.nttdata.bootcamp.msoperations.model.Operation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OperationService {
	
	Mono<Operation> insertOperation(Mono<Operation> operation);
	Flux<Operation> findByIdproduct(String idproduct) ;
	
}
