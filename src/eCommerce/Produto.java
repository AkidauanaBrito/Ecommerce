package eCommerce;

import com.google.gson.annotations.Expose;

public class Produto {

	@Expose
	private String nome;

	@Expose
	private float preco;

	@Expose
	private int quantDisponivel;
	
	@Expose
	private String vendedor; //login
	
	@Expose
	private String categoria; //nome
	
	@Expose
	private StatusProduto statusProduto;

	public Produto(String nome, float preco, int quantDisponivel, String vendedor, String categoria) {
		this.nome = nome;
		this.preco = preco;
		this.quantDisponivel = quantDisponivel;
		this.vendedor = vendedor;
		this.statusProduto = StatusProduto.PREPARANDO;
		this.categoria = categoria;
	}

	public String getNome() {
		return this.nome;
	}

	public float getPreco() {
		return this.preco;
	}

	public int getQuantDisponivel() {
		return this.quantDisponivel;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public void setQuantDisponivel(int quantDisponivel) {
		this.quantDisponivel = quantDisponivel;
	}
	
	public String getVendedor() {
		return vendedor;
	}
	
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public StatusProduto getStatusProduto() {
		return statusProduto;
	}

	public void setStatusProduto(StatusProduto statusProduto) {
		this.statusProduto = statusProduto;
	}
}
