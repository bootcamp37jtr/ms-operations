package com.nttdata.bootcamp.msoperations.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.bootcamp.msoperations.exception.ServiceException;
import com.nttdata.bootcamp.msoperations.external.model.AccountCustomer;
import com.nttdata.bootcamp.msoperations.infraestructure.OperationRepository;
import com.nttdata.bootcamp.msoperations.model.Operation;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OperationServiceImpl implements OperationService {

	@Autowired
	OperationRepository operationRepository;

	@Autowired
	WebClient.Builder webClientBuilder;

	@Override
	public Mono<Operation> insertOperation(Mono<Operation> operation) {
		return operation.flatMap(ope -> {
			if (!"TRANS".equals(ope.getTypeOperation())) {
				this.count(ope.getIdproduct()).subscribe(cantidad -> {
					log.info("operaciones registradas: " + cantidad);
					// se valida la cantidad de operaciones que se tiene para el calculo de la comision
					getCustomerAccount(operation).subscribe(ac -> {
						if (ac.getMaxQuantOperComisiion() < cantidad.intValue()) {
							Operation opeComision = ope;
							log.debug("55", opeComision);
							opeComision.setTypeAction("R");
							opeComision.setTypeOperation("COM");
							opeComision.setIndDel("0");
							opeComision.setAmount(ac.getComissionvalue());
							operationRepository.save(opeComision).subscribe(opeC -> {
								log.info("registrooperacioncomision");
							});

						} else {
							log.info("NO HAY COMISION");
						}
					});
					
					
					
				});
			} else {
				Operation opeTransDestiny = ope;
				log.debug("operacion", opeTransDestiny);
				String cuentaOrigen = ope.getIdproductOriDest();
				String cuentaDestino = ope.getIdproduct();
				opeTransDestiny.setTypeAction("A");
				opeTransDestiny.setIndDel("0");
				opeTransDestiny.setIdproduct(cuentaOrigen);
				opeTransDestiny.setIdproductOriDest(cuentaDestino);

				operationRepository.save(opeTransDestiny).subscribe(opeC -> {
					log.info("registrooperacioncomision");
				});

			}

			return operationRepository.save(ope);
		});

		// operationRepository::save
		// TODO: ENVIAR MENSAJE KAFA PARA ACTUALIZAR EL SALDO DEL LA CUENTA,
		// SE PODRIA HACER CON UN LLAMADO AUN SERVICIO, UPDATE
	}
	
	
	
	private Mono<AccountCustomer> getCustomerAccount(Mono<Operation> operation){
		return operation.flatMap(ope->{
			if ("R".equals(ope.getTypeAction()) && 
					"RET".equals(ope.getTypeOperation())) {
				return getAccountAvaiable(ope.getNumberCard());
			} else {
				return getCustomerAccount(ope.getIdproduct());
			}
		});
	}
	

	private Mono<AccountCustomer> getCustomerAccount(String idaccount) {
		return webClientBuilder.build().get().uri("http://service-accountcustomer/accountcustomer/" + idaccount)
				.retrieve().bodyToMono(AccountCustomer.class).onErrorResume(ServiceException.class, ex -> {
					log.error(ex.getMessage());
					return Mono.empty();
				});
	}

	private Mono<AccountCustomer> getAccountAvaiable(String cardNumber) {
		return webClientBuilder.build().get()
				.uri("http://service-accountcustomer/creditcardaccount/cuentadisponible/" + cardNumber).retrieve()
				.bodyToMono(AccountCustomer.class).onErrorResume(ServiceException.class, ex -> {
					log.error(ex.getMessage());
					return Mono.empty();
				});
	}

	@Override
	public Flux<Operation> findByIdproduct(String idproduct) {

		Operation operationFilter = new Operation();
		operationFilter.setIdproduct(idproduct);
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("idproduct",
				ExampleMatcher.GenericPropertyMatcher.of(StringMatcher.EXACT));
		Example<Operation> personExample = Example.of(operationFilter, matcher);
		return operationRepository.findAll(personExample, Sort.by("createdAt"));
	}

	public Mono<Long> count(String idproduct) {
		Operation operationFilter = new Operation();
		operationFilter.setIdproduct(idproduct);
		log.info("operationFilter " + operationFilter.getIdproduct());
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("idproduct",
				ExampleMatcher.GenericPropertyMatcher.of(StringMatcher.EXACT));
		Example<Operation> personExample = Example.of(operationFilter, matcher);
		return operationRepository.count(personExample);
	}

}
