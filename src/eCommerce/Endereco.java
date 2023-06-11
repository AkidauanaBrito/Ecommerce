package eCommerce;

import com.google.gson.annotations.Expose;

public class Endereco {

	@Expose
	private String rua;
	
	@Expose
	private int numero;
	
	@Expose
	private String complemento;
	
	@Expose
	private String cep;
	
	@Expose
	private String bairro;
	
	@Expose
	private String cidade;
	
	@Expose
	private String estado;
	
}
