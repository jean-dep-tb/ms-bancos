package spring.boot.webflu.ms.bancos.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import reactor.core.publisher.Flux;
import spring.boot.webflu.ms.bancos.app.documents.Banco;
import spring.boot.webflu.ms.bancos.app.service.BancoService;

@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class SpringBootWebfluMsBancosApplication implements CommandLineRunner{
	
	@Autowired
	private BancoService bancoService;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluMsBancosApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluMsBancosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("bancos").subscribe();
		
		Flux.just(
				new Banco("bcp","20200000001", "BCP","CALLE BCP","99999991"),
				new Banco("bbva","20200000002", "BBVA","CALLE BBVA","99999992")
		).flatMap(banco -> bancoService.saveBanco(banco))
			.subscribe(banco -> log.info("insert"+ banco.getCodigo_banco() + banco.getRazon_social()));
		
		
	}

}
