package spring.boot.webflu.ms.bancos.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.bancos.app.documents.Banco;
import spring.boot.webflu.ms.bancos.app.service.BancoService;

@RequestMapping("/api/Bancos")
@RestController
public class BancoControllers {

	@Autowired
	private BancoService bancoService;

	// LISTA TODOS LAS CUENTAS DE BANCO
	@GetMapping
	public Mono<ResponseEntity<Flux<Banco>>> findAll() {
		return Mono
				.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(bancoService.findAllBanco())

				);
	}

	// LISTAR BANCO POR ID
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Banco>> viewId(@PathVariable String id) {
		return bancoService.findByIdBanco(id)
				.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	// LISTA BANCO POR RUC
	@GetMapping("/ruc/{ruc}")
	public Mono<ResponseEntity<Banco>> verBancoRuc(@PathVariable String ruc) {
		return bancoService.verBancoRuc(ruc)
				.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	// ACTUALIZA BANCO POR ID
	@PutMapping
	public Mono<Banco> updateClientePersonal(@RequestBody Banco banco) {
		System.out.println(banco.toString());
		return bancoService.saveBanco(banco);
	}

	// GUARDA BANCO
	@PostMapping
	public Mono<Banco> guardarCliente(@RequestBody Banco banco) {
		return bancoService.saveBanco(banco);

	}

	// ELIMINA BANCO POR ID
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteBanco(@PathVariable String id) {
		return bancoService.findByIdBanco(id).flatMap(s -> {
			return bancoService.deleteBanco(s).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
	}

}
