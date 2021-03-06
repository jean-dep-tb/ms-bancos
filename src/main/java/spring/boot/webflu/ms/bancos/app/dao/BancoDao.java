package spring.boot.webflu.ms.bancos.app.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.bancos.app.documents.Banco;


public interface BancoDao extends ReactiveMongoRepository<Banco, String> {

//	@Query("{ 'ruc' : ?0}")
//	Mono<Banco> viewRucBanco(String dni);
	
	Mono<Banco> findByRuc(String ruc);
	
	//public Mono<Banco> findByRazon_social(String razon_social);
}
