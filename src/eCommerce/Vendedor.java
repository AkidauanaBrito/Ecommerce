package eCommerce;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Vendedor extends Usuario {

	@Expose
	private List<Produto> produtos; // Um vendedor tem uma lista de produtos.

	public Vendedor(String login, String senha, String nome) {
		super(login, senha, nome, "vendedor");
		this.produtos = new ArrayList<Produto>();
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public boolean adicionaProduto(Produto novoProduto) {
		return this.produtos.add(novoProduto);

	}

	public boolean removeProduto(String nomeProduto) {
		for (Produto prod : this.produtos) {
			if (prod.getNome().equals(nomeProduto)) {
				this.produtos.remove(prod);
				return true;
			}
		}
		return false;
	}
}
