package eCommerce;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

public class RelatorioFinanceiro {

	public static void exibirTotalVendasNoPeriodo(Ecommerce comercio, String loginVendedor, Date inicio, Date fim) {
		float valorTotal = 0;

		if (inicio == null || fim == null) { // uma das datas � inv�lida, considerar todos os pedidos
			inicio = new Date(0L); // menor data possivel
			fim = new Date(); // maior data possivel: hoje
		}

		List<Usuario> usuarios = comercio.getUsuarios();

		for (int i = 0; i < usuarios.size(); i++) {
			Usuario usuario = usuarios.get(i);
			if (usuario instanceof Cliente) {
				Cliente cliente = (Cliente) usuario;

				List<Integer> pedidosIdx = cliente.getHistoricoPedidosIdx();
				List<Pedido> pedidos = cliente.getPedidos();

				for (Integer pedidoIdx : pedidosIdx) {
					Pedido pedido = pedidos.get(pedidoIdx);
					Date data = Utils.getDateDeString(pedido.getData());

					if (data.compareTo(inicio) >= 0 && data.compareTo(fim) <= 0) {
						List<Produto> produtos = pedido.getProdutos();

						for (int j = 0; j < produtos.size(); j++) {
							Produto produto = produtos.get(j);
							if (produto.getVendedor().equals(loginVendedor)) {
								valorTotal += produto.getPreco() * produto.getQuantDisponivel();
							}
						}
					}
				}
			}
		}

		System.out.println("Valor total de vendas no período: R$ " + valorTotal + "\n");
	}

	public static void exibirVendasPorCategoriaNoPeriodo(Ecommerce comercio, String loginVendedor, Date inicio,
			Date fim) {
		float valorTotal = 0;
		Map<String, Float> vendasPorCategoria = new TreeMap<>();

		if (inicio == null || fim == null) { // uma das datas é inválida, considerar todos os pedidos
			inicio = new Date(0L); // menor data possível
			fim = new Date(); // maior data possível: hoje
		}

		List<Usuario> usuarios = comercio.getUsuarios();

		for (int i = 0; i < usuarios.size(); i++) {
			Usuario usuario = usuarios.get(i);
			if (usuario instanceof Cliente) {
				Cliente cliente = (Cliente) usuario;

				List<Integer> pedidosIdx = cliente.getHistoricoPedidosIdx();
				List<Pedido> pedidos = cliente.getPedidos();

				for (Integer pedidoIdx : pedidosIdx) {
					Pedido pedido = pedidos.get(pedidoIdx);
					Date data = Utils.getDateDeString(pedido.getData());

					if (data.compareTo(inicio) >= 0 && data.compareTo(fim) <= 0) {
						List<Produto> produtos = pedido.getProdutos();

						for (int j = 0; j < produtos.size(); j++) {
							Produto produto = produtos.get(j);
							if (produto.getVendedor().equals(loginVendedor)) {
								String categoria = produto.getCategoria();

								float valorProd = produto.getPreco() * produto.getQuantDisponivel();

								if (vendasPorCategoria.containsKey(categoria)) {
									float valorAtual = vendasPorCategoria.get(categoria);
									vendasPorCategoria.put(categoria, valorAtual + valorProd);
								} else {
									vendasPorCategoria.put(categoria, valorProd);
								}

								valorTotal += valorProd;
							}
						}
					}
				}
			}
		}

		System.out.println("=============== Total de vendas no período por Categoria ===============\n");

		AtomicReference<Integer> pos = new AtomicReference<>();
		pos.set(1);

		AtomicReference<Float> valorTotalRef = new AtomicReference<>();
		valorTotalRef.set(valorTotal);

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);

		vendasPorCategoria.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.forEach(entry -> {
					System.out.println(pos + ". " + entry.getKey() + " - R$ " + entry.getValue() + " - "
							+ df.format((entry.getValue() / valorTotalRef.get()) * 100) + "%");
					pos.set(pos.get() + 1);
				});

		System.out.println("\n========================================================================\n");
	}

	public static void exibirTotalVendasPorProduto(Ecommerce comercio, String loginVendedor, Date inicio, Date fim) {
		float valorTotal = 0;
		Map<String, Float> vendasPorProduto = new TreeMap<>();

		if (inicio == null || fim == null) { // uma das datas é inválida, considerar todos os pedidos
			inicio = new Date(0L); // menor data possível
			fim = new Date(); // maior data possível: hoje
		}

		List<Usuario> usuarios = comercio.getUsuarios();

		for (int i = 0; i < usuarios.size(); i++) {
			Usuario usuario = usuarios.get(i);
			if (usuario instanceof Cliente) {
				Cliente cliente = (Cliente) usuario;

				List<Integer> pedidosIdx = cliente.getHistoricoPedidosIdx();
				List<Pedido> pedidos = cliente.getPedidos();

				for (Integer pedidoIdx : pedidosIdx) {
					Pedido pedido = pedidos.get(pedidoIdx);
					Date data = Utils.getDateDeString(pedido.getData());
					
					if (data.compareTo(inicio) >= 0 && data.compareTo(fim) <= 0) {
						List<Produto> produtos = pedido.getProdutos();

						for (int j = 0; j < produtos.size(); j++) {
							Produto produto = produtos.get(j);
							if (produto.getVendedor().equals(loginVendedor)) {
								String nome = produto.getNome();

								float valorProd = produto.getPreco() * produto.getQuantDisponivel();

								if (vendasPorProduto.containsKey(nome)) {
									float valorAtual = vendasPorProduto.get(nome);
									vendasPorProduto.put(nome, valorAtual + valorProd);
								} else {
									vendasPorProduto.put(nome, valorProd);
								}

								valorTotal += valorProd;
							}
						}
					}
					
				}
			}
		}

		System.out.println("=============== Total de vendas no período por Categoria ===============\n");

		AtomicReference<Integer> pos = new AtomicReference<>();
		pos.set(1);

		AtomicReference<Float> valorTotalRef = new AtomicReference<>();
		valorTotalRef.set(valorTotal);

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);

		vendasPorProduto.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.forEach(entry -> {
					System.out.println(pos + ". " + entry.getKey() + " - R$ " + entry.getValue() + " - "
							+ df.format((entry.getValue() / valorTotalRef.get()) * 100) + "%");
					pos.set(pos.get() + 1);
				});

		System.out.println("\n========================================================================\n");
	}

}
