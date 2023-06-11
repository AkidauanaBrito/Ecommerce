package eCommerce;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Cliente extends Usuario {

	@Expose
	private List<Pedido> pedidos;

	private String carrinhoIdx = "-1"; // Indice do pedido cujo status é StatusPedido.PAGAMENTO_PENDENTE

	public Cliente(String login, String senha, String nome) {
		super(login, senha, nome, "cliente");
		pedidos = new ArrayList<Pedido>();
		carrinhoIdx = "-1";
	}

	public Pedido getCarrinho() {
		if (pedidos.size() > 0) {
			int index = Integer.valueOf(carrinhoIdx == null ? "-1" : carrinhoIdx);
			if (index > -1) {
				return pedidos.get(index);
			}

			for (int i = 0; i < pedidos.size(); i++) {
				Pedido pedido = pedidos.get(i);
				if (pedido.getStatusPedido() == StatusPedido.PAGAMENTO_PENDENTE) {
					carrinhoIdx = String.valueOf(i);
					return pedido;
				}
			}
		}

		Pedido pedido = new Pedido();
		this.pedidos.add(pedido);
		carrinhoIdx = String.valueOf(this.pedidos.size() - 1);
		return pedido;
	}

	public boolean adicionaProduto(Produto prod) {
		return getCarrinho().adicionaProduto(prod);
	}

	// M�todo para listar Produtos
	public void listarProdutosCarrinho() {
		getCarrinho().listaProdutos();
	}

	// M�todo para remover produto
	public boolean removerProdutoCarrinho(Produto produto) {
		return getCarrinho().removeProduto(produto);
	}

	// M�todo para mostrar o carrinho do cliente
	public void exibirDetalhesCarrinho() {
		System.out.println("\n====================== Carrinho ======================\n");
		getCarrinho().exibirResumo();
	}

	// Método para finalizar pedido
	public void finalizarPedido() {
		getCarrinho().setStatusProduto(StatusPedido.PAGAMENTO_APROVADO);
		carrinhoIdx = null;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public List<Integer> getHistoricoPedidosIdx() {
		List<Integer> pedidosFiltrados = new ArrayList<>();
		for (int i = 0; i < pedidos.size(); i++) {
			Pedido pedido = pedidos.get(i);

			StatusPedido status = pedido.getStatusPedido();
			if (status != null && status != StatusPedido.PAGAMENTO_PENDENTE) {
				pedidosFiltrados.add(i);
			}
		}
		return pedidosFiltrados;
	}

	// Método para exibir os detalhes do histórico de pedidos
	public void exibirHistoricoPedidos() {
		for (int i = 0; i < pedidos.size(); i++) {
			Pedido pedido = pedidos.get(i);
			if (pedido.getStatusPedido() != StatusPedido.PAGAMENTO_PENDENTE) {
				System.out
						.println(String.format("\n====================== Pedido #%d ======================\n", i + 1));
				pedido.exibirResumo();
			}
		}
	}

	public List<Produto> getHistoricoPedidos(String loginVendedor) {
		List<Produto> produtosVendedor = new ArrayList<>();

		for (int i = 0; i < pedidos.size(); i++) {
			Pedido pedido = pedidos.get(i);
			if (pedido.getStatusPedido() != StatusPedido.PAGAMENTO_PENDENTE) {
				List<Produto> produtos = pedido.getProdutos();
				for (Produto produto : produtos) {
					if (produto.getVendedor().equals(loginVendedor)) {
						produtosVendedor.add(produto);
					}

				}
			}
		}
		return produtosVendedor;
	}
}
