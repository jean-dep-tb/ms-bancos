package spring.boot.webflu.ms.bancos.app.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.bancos.app.dao.BancoDao;
import spring.boot.webflu.ms.bancos.app.documents.Banco;
import spring.boot.webflu.ms.bancos.app.service.BancoService;

@Service
public class BancoServiceImpl implements BancoService {
	
	@Autowired
	public BancoDao servicioBanco; //servicioBanco
	
	@Override
	public Flux<Banco> findAllBanco()
	{
	return servicioBanco.findAll();
	
	}
	@Override
	public Mono<Banco> findByIdBanco(String id)
	{
	return servicioBanco.findById(id);
	
	}
	
	@Override
	public Mono<Banco> verBancoRuc(String ruc)
	{
	return servicioBanco.findByRuc(ruc);
	
	}
	
	@Override
	public Mono<Banco> saveBanco(Banco bank)
	{
	return servicioBanco.save(bank);
	}
	
	@Override
	public Mono<Void> deleteBanco(Banco bank) {
		return servicioBanco.delete(bank);
	}
	
	@Override
	public Mono<Banco> findByRuc(String ruc) {
		
		return servicioBanco.findByRuc(ruc);
	}
	
	
	
}
