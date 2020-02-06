package spring.boot.webflu.ms.bancos.app;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.bancos.app.documents.Banco;
import spring.boot.webflu.ms.bancos.app.service.BancoService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootWebfluMsBancosApplicationTests {

	@Autowired
	private WebTestClient client;
	
	@Autowired
	private BancoService serviceBanco;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void listarBancos() {
		client.get().uri("/api/Bancos/")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk() 
		.expectHeader().contentType(MediaType.APPLICATION_JSON) //.hasSize(2);
		.expectBodyList(Banco.class).consumeWith(response -> {
			
			List<Banco> banco = response.getResponseBody();
			
			banco.forEach(p -> {
				System.out.println(p.getCodigo_banco());
			});
			
			Assertions.assertThat(banco.size() > 0).isTrue();
		});
	}
	
	@Test
	public void verBanco() {
		
		Banco banco = serviceBanco.findByRuc("20200000001").block();
		
		//Banco banc = serviceBanco.findByIdBanco("5e2b167da2cabf58a5688662").block();
			
		client.get().uri("/api/Bancos/{id}", Collections.singletonMap("id", banco.getId()))
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody(Banco.class)
		.consumeWith(response ->{
			Banco b = response.getResponseBody();
			Assertions.assertThat(b.getId()).isNotEmpty();//no esta vacio  
			Assertions.assertThat(b.getId().length()>0).isTrue();//tamaño mayor a 0
			Assertions.assertThat(b.getRuc()).isEqualTo("20200000001");// si es igual al ruc
		});
	}
	
//	.expectBody()
//	.jsonPath("$.id").isNotEmpty()
//	.jsonPath("$.ruc").isEqualTo("20200000001");
	
	@Test
	void crearBanco() {	
		
		//Creamos un Banco
		
		Banco banco = new Banco();
		banco.setCodigo_banco("B002");
		banco.setRuc("20200000002");
		banco.setRazon_social("BBVA");
		banco.setDireccion("CALLE BBVA");
		banco.setTelefono("99999992");

		client.post()
		.uri("api/Bancos")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.body(Mono.just(banco), Banco.class)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody(Banco.class)
		.consumeWith(response -> {
			Banco b = response.getResponseBody();
			System.out.println("[Banco REGISTRADO] " + banco);
			Assertions.assertThat(b.getCodigo_banco()).isNotEmpty().isEqualTo("B002");
			Assertions.assertThat(b.getRuc()).isNotEmpty().isEqualTo("20200000002");
			Assertions.assertThat(b.getRazon_social()).isNotEmpty().isEqualTo("BBVA");
			Assertions.assertThat(b.getDireccion()).isNotEmpty().isEqualTo("CALLE BBVA");
			Assertions.assertThat(b.getTelefono()).isNotEmpty().isEqualTo("99999992");
		});
	}
	
	@Test
	public void actualizarBanco() {
		//buscando por numero de RUC
		Banco banco= serviceBanco.findByRuc("20200000002").block();
		
		Banco bancoEditado = new Banco("B002", "20200000002", "BBVAx", "CALLE BBVA", "99999992");
		
		client.put()
		.uri("/api/Bancos") //,Collections.singletonMap("id", banco.getId())
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.body(Mono.just(bancoEditado), Banco.class)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody()
		.jsonPath("$.razon_social").isEqualTo("BBVAx")
		.jsonPath("$.ruc").isEqualTo("20200000002")
		;
		
	}

	@Test
	public void borrarBanco() {
		Banco banco= serviceBanco.findByRuc("20200000001").block();	
		client.delete()
		.uri("api/Bancos" + "/{id}", Collections.singletonMap("id", banco.getId()))
		.exchange()
		.expectStatus().isNoContent()
		.expectBody()
		.isEmpty();
		
		client.get()
		.uri("api/Bancos" + "/{id}", Collections.singletonMap("id", banco.getId()))
		.exchange()
		.expectStatus().isNotFound()
		.expectBody()
		.isEmpty();
		
	}
	
}
