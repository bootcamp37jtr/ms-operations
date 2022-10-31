package com.nttdata.bootcamp.msoperations.infraestructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msoperations.model.Operation;

@Repository
public interface OperationRepository extends ReactiveMongoRepository<Operation, String> {

}
