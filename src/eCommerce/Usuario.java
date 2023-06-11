package eCommerce;

import com.google.gson.annotations.Expose;

public class Usuario {

	@Expose
	private String login;

	@Expose
	private String senha;

	@Expose
	private String nome;
	
	private String tipo;

	public Usuario(String login, String senha, String nome, String tipo) {
		this.setLogin(login);
		this.setSenha(senha);
		this.setNome(nome);
		this.tipo = tipo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean equals(Usuario outroUsuario) {
		return this.login.equalsIgnoreCase(outroUsuario.getLogin());
	}

}