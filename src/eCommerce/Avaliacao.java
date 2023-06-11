package eCommerce;

import com.google.gson.annotations.Expose;

public class Avaliacao {

	@Expose
	private float nota;

	@Expose
	private String feedback;

	@Expose
	private String data;

	@Expose
	private Cliente avaliador;

}
