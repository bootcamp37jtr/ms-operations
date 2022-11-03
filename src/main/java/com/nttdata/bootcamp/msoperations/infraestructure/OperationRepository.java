package com.nttdata.bootcamp.msoperations.infraestructure;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msoperations.model.Operation;

import reactor.core.publisher.Flux;

@Repository
public interface OperationRepository extends ReactiveMongoRepository<Operation, String> {
	Flux<Operation> findByIdproduct(String idproduct, Pageable pageable);
	Flux<Operation> findByIdproductAndCreatedAtBetween(String idproduct,LocalDateTime startDate, LocalDateTime endDate);
}
