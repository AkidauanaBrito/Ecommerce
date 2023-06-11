package eCommerce;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Pedido {

	@Expose
	private List<Produto> produtos;

	@Expose
	private StatusPedido statusPedido;

	@Expose
	private String data;

	public Pedido() {
		setProdutos(new ArrayList<Produto>());
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}

	public void setStatusProduto(StatusPedido statusPedido) {
		if (statusPedido == StatusPedido.PAGAMENTO_APROVADO) {
			data = Utils.getDataAgora();
		}
		this.statusPedido = statusPedido;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean adicionaProduto(Produto prod) {
		return this.produtos.add(prod);
	}

	// M�todo para listar Produtos
	public void listaProdutos() {
		for (int i = 0; i < this.produtos.size(); i++) {
			Produto prod = this.produtos.get(i);
			System.out.println(i + 1 + ". " + prod.getNome());
		}
	}

	// M�todo para remover produto
	public boolean removeProduto(Produto produto) {
		for (Produto prod : this.produtos) {
			if (prod.getNome().equals(produto.getNome())) {
				return this.produtos.remove(prod);
			}
		}
		return false;
	}

	// M�todo para mostrar o carrinho do cliente
	public void exibirResumo() {
		float valorTotal = 0f;

		if (this.produtos.size() == 0) {
			System.out.println("O Carrinho est� vazio. Por favor, adicione produtos");
		} else {
			for (int i = 0; i < this.produtos.size(); i++) {
				Produto prod = this.produtos.get(i);
				float valorTotalProduto = prod.getPreco() * prod.getQuantDisponivel();
				valorTotal += valorTotalProduto;
				System.out.println(prod.getNome() + "  |  " + "R$ " + prod.getPreco() + "  x  "
						+ prod.getQuantDisponivel() + " = R$ " + valorTotalProduto);
			}

			System.out.println("\nO valor total da compra �: " + valorTotal);
		}

		System.out.println("\n=====================================================\n");
	}
}
