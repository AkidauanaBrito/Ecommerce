package eCommerce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

public class Ecommerce {

	private int usuarioLogadoIdx;

	@Expose
	private String nome;

	@Expose
	private List<Categoria> categorias;

	@Expose
	private List<Usuario> usuarios;

	private String separador = System.getProperty("line.separador");

	// M�todo para criar o ecommerce
	public Ecommerce(String nome) {
		this.nome = nome;
		this.categorias = new LinkedList<Categoria>();
		this.usuarios = new ArrayList<>();
	}

	public void setUsuarioLogado(int index) {
		this.usuarioLogadoIdx = index;
	}

	// M�todo para buscar o usu�rio pelo Login.
	public Usuario usuarioALogarExiste(String login, String senha) {
		for (int i = 0; i < this.usuarios.size(); i++) {
			Usuario usuarioDaVez = this.usuarios.get(i);
			if (usuarioDaVez != null && usuarioDaVez.getLogin().equals(login)
					&& usuarioDaVez.getSenha().equals(senha)) {
				this.usuarioLogadoIdx = i;
				return usuarioDaVez;
			}
		}
		return null;
	}

	// M�todo booleano para validar o campo Login.
	public boolean loginEhValido(String login) {
		return login.length() > 2;
	}

	// M�todo booleano para validar a senha.
	public boolean senhaEhValida(String senha) {
		return senha != null && senha.trim() != "";
	}

	// M�todo para adicionar Usu�rio.
	public boolean adicionaUsuario(Usuario usuario) {
		return this.usuarios.add(usuario);
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	// Método para finalizar o pedido
	public void finalizarPedido(String loginCliente) {
		((Cliente) this.usuarios.get(this.usuarioLogadoIdx)).finalizarPedido();
	}

	// M�todo para adicionar categoria.
	public boolean adicionaCategoria(Categoria cat) {
		return this.categorias.add(cat);
	}

	// M�todo para remover categoria.
	public boolean removeCategoria(String nome) {
		for (Categoria cat : this.categorias) {
			if (cat.getNome().equals(nome)) {
				return this.categorias.remove(cat);
			}
		}
		return false;
	}

	// M�todo para Listar categorias:
	public void listarCategorias() {
		for (int i = 0; i < this.categorias.size(); i++) {
			Categoria cat = this.categorias.get(i);
			System.out.println((i + 1) + ". " + cat.getNome());
		}
	}

	// M�todo para criar produto na categoria
	public boolean adicionaProduto(Produto prod, int categoriaSelecionada) {
		String nomeCategoria = this.categorias.get(categoriaSelecionada).getNome();
		prod.setCategoria(nomeCategoria);
		((Vendedor) this.usuarios.get(this.usuarioLogadoIdx)).adicionaProduto(prod);
		return this.categorias.get(categoriaSelecionada).adicionaProduto(prod);
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void listaProdutos() {
		for (int i = 0; i < this.categorias.size(); i++) {
			Categoria cat = this.categorias.get(i);
			System.out.println((i + 1) + ". " + cat.getNome() + ":");
			List<Produto> produtos = cat.getProdutos();
			for (int j = 0; j < produtos.size(); j++) {
				Produto prod = produtos.get(j);
				System.out.println("    " + prod.getNome() + "  |  " + "R$ " + prod.getPreco() + "  |  "
						+ prod.getQuantDisponivel() + " unidade(s) dispon�vel(eis)");
			}
		}
		System.out.println("\n");
	}

	public boolean removeProduto(String nomeProduto) {
		for (int i = 0; i < this.categorias.size(); i++) {
			Categoria cat = this.categorias.get(i);
			List<Produto> produtos = cat.getProdutos();
			for (int j = 0; j < produtos.size(); j++) {
				Produto prod = produtos.get(j);
				if (prod.getNome().equals(nomeProduto)) {
					if (removerProdutoVendedor(prod.getNome())) {
						return this.categorias.get(i).removeProduto(prod);
					}
				}
			}
		}
		return false;
	}

	private boolean removerProdutoVendedor(String nomeProduto) {
		return ((Vendedor) this.usuarios.get(this.usuarioLogadoIdx)).removeProduto(nomeProduto);
	}

	public Categoria getCategoria(int index) {
		return this.categorias.get(index);
	}

	public List<Categoria> getCategorias() {
		return this.categorias;
	}

	public boolean listaInfoProdutos(int categoriaIdx) {
		return this.categorias.get(categoriaIdx).listaInfoProdutos();
	}

	public Produto getProdutoDaCategoria(int categoriaIdx, int produtoIdx) {
		return this.categorias.get(categoriaIdx).getProdutos().get(produtoIdx);
	}

	public void addProdutoCarrinho(Produto produtoEscolhido, int categoriaIdx, int produtoIdx, int qtdDisponivel,
			int qtdPossivel) {
		((Cliente) this.usuarios.get(usuarioLogadoIdx)).adicionaProduto(new Produto(produtoEscolhido.getNome(),
				produtoEscolhido.getPreco(), qtdPossivel, produtoEscolhido.getVendedor(), produtoEscolhido.getCategoria()));
		this.categorias.get(categoriaIdx).getProdutos().get(produtoIdx).setQuantDisponivel(qtdDisponivel - qtdPossivel);

		System.out.println("\nItem adicionado ao carrinho.\n");
	}

	public void listarProdutosPesquisado(String nomeProdutoAPesquisar) {
		for (int i = 0; i < this.categorias.size(); i++) {
			Categoria cat = this.categorias.get(i);
			List<Produto> produtos = cat.getProdutos();
			for (int j = 0; j < produtos.size(); j++) {
				Produto prod = produtos.get(j);
				if (prod.getNome().equals(nomeProdutoAPesquisar)) {
					System.out.println("    " + prod.getNome() + "  |  " + "R$ " + prod.getPreco() + "  |  "
							+ prod.getQuantDisponivel() + " unidade(s) disponível(eis)");
				}

			}
		}
		System.out.println("\n");
	}
	
	public Map<Integer, Map<Integer, Map<Integer, Produto>>> getVendasVendedor(String loginVendedor) {
		Map<Integer, Map<Integer, Map<Integer, Produto>>> prodsPorPedidoPorUsuario = new HashMap<>();
		int posVenda = 1;

		for (int i = 0; i < usuarios.size(); i++) {
			Usuario usuario = usuarios.get(i);
			if (usuario instanceof Cliente) {
				Cliente cliente = (Cliente) usuario;
				System.out.println(
						String.format("%d. =============== Cliente: %s ===============", i + 1, cliente.getNome()));

				// Adiciona usuario e instancia o map de pedidos
				prodsPorPedidoPorUsuario.put(i, new HashMap<>());

				List<Integer> pedidosIdx = cliente.getHistoricoPedidosIdx();
				List<Pedido> pedidos = cliente.getPedidos();

				for (Integer pedidoIdx : pedidosIdx) {
					// Adiciona o index do pedido no cliente atual e instancia o map de produtos
					prodsPorPedidoPorUsuario.get(i).put(pedidoIdx, new HashMap<Integer, Produto>());

					Pedido pedido = pedidos.get(pedidoIdx);
					List<Produto> produtos = pedido.getProdutos();

					System.out.println("--" + "Pedido #" + (pedidoIdx + 1) + " - Data: " + pedido.getData());

					for (int j = 0; j < produtos.size(); j++) {
						Produto produto = produtos.get(j);
						if (produto.getVendedor().equals(loginVendedor)) {
							prodsPorPedidoPorUsuario.get(i).get(pedidoIdx).put(j, produto);
							System.out.println("    " + (j + 1) + ". " + produto.getNome() + "  |  " + "R$ "
									+ produto.getPreco() + "  |  Quantidade: " + produto.getQuantDisponivel());
						}
					}
					System.out.println("=============================================");
				}
			}
		}
		return prodsPorPedidoPorUsuario;
	}

	public void atualizarStatusPedidoCliente(int usuarioIdx, int pedidoIdx, int produtoIdx, StatusProduto status) {
		((Cliente) this.usuarios.get(usuarioIdx)).getPedidos().get(pedidoIdx).getProdutos().get(produtoIdx)
				.setStatusProduto(status);
	}
}
