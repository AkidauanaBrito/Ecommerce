package eCommerce;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import eCommerce.bd.GerenciadorBD;

public class Categoria {

	@Expose
	private String nome; // considerar que é unico
	
	@Expose
	private List<Produto> produtos;

	public Categoria(String nome) {
		this.nome = nome;
		this.produtos = new ArrayList<>();
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	public List<Produto> getProdutos() {
		return this.produtos;
	}

	// Mï¿½todo para adicionar produto
	public boolean adicionaProduto(Produto prod) {
		return this.produtos.add(prod);
	}

	// Mï¿½todo para listar Produtos
	public void listaProdutos() {
		for (int i = 0; i < this.produtos.size(); i++) {
			Produto prod = this.produtos.get(i);
			System.out.println(i + 1 + ". " + prod.getNome());
		}
	}

	// Mï¿½todo para remover produto
	public boolean removeProduto(Produto produto) {
		for (Produto prod : this.produtos) {
			if (prod.getNome().equals(produto.getNome())) {
				return this.produtos.remove(prod);
			}
		}
		return false;
	}
	
	public boolean listaInfoProdutos() {
		System.out.println("\n=================================== Produtos ========================================\n");
		if (this.produtos.size() == 0) {
			System.out.println("A categoria " + this.nome + " nï¿½o possui itens cadastrados.");
			System.out.println("\n=======================================================================================\n");
			return false;
		} else {
			for (int j = 0; j < this.produtos.size(); j++) {
				Produto prod = this.produtos.get(j);
				System.out.println(j + 1 + ". " + prod.getNome() + "  |  " + "R$ "+prod.getPreco() + "  |  " + prod.getQuantDisponivel() + " unidade(s) disponï¿½vel(eis)");
			}
			System.out.println("\n=======================================================================================\n");
		}
		
		return true;
	}
	
	public List<Produto> listaInfoProdutosPesquisado(String nomeProdutoAPequisar) {
		List<Produto> produtosEncontrados = new ArrayList<Produto>();
		
		for (int j = 0; j < this.produtos.size(); j++) {
			Produto prod = this.produtos.get(j);
			if (prod.getNome().equals(nomeProdutoAPequisar)) {
				produtosEncontrados.add(prod);
			}
		}
		
		System.out.println("\n=================================== Produtos Pesquisados ========================================\n");
		if (produtosEncontrados.size() == 0) {
			System.out.println("A categoria " + this.nome + " nï¿½o possui itens cadastrados.");
			System.out.println("\n=======================================================================================\n");
		} else {
			for (int j = 0; j < produtosEncontrados.size(); j++) {
				Produto prod = produtosEncontrados.get(j);
				System.out.println(j + 1 + ". " + prod.getNome() + "  |  " + "R$ "+prod.getPreco() + "  |  " + prod.getQuantDisponivel() + " unidade(s) disponï¿½vel(eis)");
			}
			System.out.println("\n=======================================================================================\n");
		}
		
		return produtosEncontrados;
	}
}
