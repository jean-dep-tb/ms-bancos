package spring.boot.webflu.ms.bancos.app.documents;


import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

@Document(collection ="bancos")
public class Banco {
	
	@Id
	@NotEmpty
	private String id;
	@NotEmpty
	private String codigo_banco;
	@NotEmpty
	private String ruc;
	@NotEmpty
	private String razon_social;
	@NotEmpty
	private String direccion;
	@NotEmpty
	private String telefono;
	
	public Banco() {
		
	}

	public Banco(String codigo_banco,String ruc, String razon_social,
			String direccion, String telefono) {

		this.codigo_banco = codigo_banco;
		this.ruc = ruc;
		this.razon_social = razon_social;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	
	
}










