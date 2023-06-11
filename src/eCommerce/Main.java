//ECOMMERCE AKIDAUANA E NATHALIA
package eCommerce;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import eCommerce.bd.GerenciadorBD;

public class Main {

	private static Ecommerce comercio;

	private static Usuario usuarioLogado;

	public static void main(String[] args) {

		comercio = GerenciadorBD.recuperarDados();
		if (comercio == null) {
			comercio = new Ecommerce("AkiNat's Place");

			comercio.adicionaUsuario(new Administrador("nath", "123", "Nathalia"));
			comercio.adicionaUsuario(new Vendedor("aki", "123", "Akidauana"));
			comercio.adicionaUsuario(new Cliente("jem", "123", "Jemerson"));
			adicionarCategoria(new Categoria("Feminino"));
			adicionarCategoria(new Categoria("Masculino"));
			adicionarCategoria(new Categoria("Infantil"));
			adicionarCategoria(new Categoria("Cal�ados"));
			adicionarCategoria(new Categoria("Acess�rios"));
		}
		System.out.println("================ AkiNat's Place ================\n");

		System.out.println("Ol�, seja bem vindo ao sistema!" + " Escolha uma op��o abaixo:\n" + "1. Login\n"
				+ "2. Criar conta\n" + "3. Sair\n");

		Scanner entrada = new Scanner(System.in);
		int selecaoMenuPrincipal = Integer.parseInt(entrada.nextLine());

		while (selecaoMenuPrincipal != 3) {

			if (selecaoMenuPrincipal == 1) {

				if (usuarioLogado == null) {
					System.out.println("Digite seu login: ");
					String login = entrada.nextLine();

					System.out.println("Digite sua senha: ");
					String senha = entrada.nextLine();

					usuarioLogado = comercio.usuarioALogarExiste(login, senha);
					if (usuarioLogado != null) {
						exibirOpcoesDoUsuario();
					} else {
						System.out.println("Usu�rio n�o existe. Tente novamente!");
					}
				} else {
					exibirOpcoesDoUsuario();
				}
			} else if (selecaoMenuPrincipal == 2) {

				System.out
						.print("1. Vendedor\n2. Cliente\n3. Administrador\n4. Voltar\nEscolha uma op��oo (1 a 4): \n");
				selecaoMenuPrincipal = Integer.parseInt(entrada.nextLine());

				if (selecaoMenuPrincipal == 1 || selecaoMenuPrincipal == 2 || selecaoMenuPrincipal == 3) {
					System.out.print("Digite o login: ");
					String login = entrada.nextLine();

					if (comercio.loginEhValido(login)) {
						System.out.print("Digite a senha: ");
						String senha = entrada.nextLine();

						if (comercio.senhaEhValida(senha)) {
							System.out.print("Digite o nome: ");
							String nome = entrada.nextLine();

							if (selecaoMenuPrincipal == 1) {
								Vendedor vendedor = new Vendedor(login, senha, nome);
								comercio.adicionaUsuario(vendedor);
								System.out.println("Vendedor cadastrado com sucesso!");

							} else if (selecaoMenuPrincipal == 2) {
								Cliente cliente = new Cliente(login, senha, nome);
								comercio.adicionaUsuario(cliente);
								System.out.println("Cliente cadastrado com sucesso!");

							} else {
								Administrador administrador = new Administrador(login, senha, nome);
								comercio.adicionaUsuario(administrador);
								System.out.println("Administrador cadastrado com sucesso!");
							}
							GerenciadorBD.salvarDados(comercio);
						} else {
							System.out.println("Senha n�o pode ser vazia.");
						}

					} else {
						System.out.println("Login deve ter pelo menos 3 caracteres." + " Tente novamente.");

					}

				}

			} else {
				System.out.println("Op��o Inv�lida");
			}

			if (usuarioLogado == null) {
				System.out.print("1. Login\n2. Cadastro\n3. Sair\nEscolha uma op��o (1 a 3): \n");
				selecaoMenuPrincipal = Integer.parseInt(entrada.nextLine());
			}

		}
		usuarioLogado = null;
		entrada.close();
	}

	private static void adicionarCategoria(Categoria categoria) {
		if (comercio.adicionaCategoria(categoria)) {
			GerenciadorBD.salvarDados(comercio);
			System.out.println("Adicionado com sucesso!");
		} else {
			System.out.println("N�o foi poss�vel adicionar a categoria");
		}
	}

	private static void exibirOpcaoEscolherProdutosCliente() {
		System.out.println("======== Categorias Do Ecommerce ========\n");

		comercio.listarCategorias();

		System.out.println("\nEscolha a categoria que deseja ver os produtos");
		Scanner escolherProdutoEmCategoria = new Scanner(System.in);
		int indiceDaCategoria = Integer.parseInt(escolherProdutoEmCategoria.nextLine());

		boolean temProdutos = comercio.listaInfoProdutos(indiceDaCategoria - 1); // metodo p listar informa��es dos
																					// produtos
		if (temProdutos) {// se a lista tem produtos
			System.out.println("\nEscolha o produto que deseja adicionar ao carrinho.\n");
			try {
				int indiceDoProduto = Integer.parseInt(escolherProdutoEmCategoria.nextLine());

				System.out.println("\nEscolha a quantidade: \n");
				int qtdProdutoEscolhido = Integer.parseInt(escolherProdutoEmCategoria.nextLine());
				Produto produtoEscolhido = comercio.getProdutoDaCategoria(indiceDaCategoria - 1, indiceDoProduto - 1);
				int qtdDisponivel = produtoEscolhido.getQuantDisponivel();
				int qtdPossivel = qtdProdutoEscolhido;

				if (qtdDisponivel == 0) {
					System.out.println("Produto indisponível.");
				} else {

					if (qtdProdutoEscolhido > qtdDisponivel) {
						System.out.println(String.format(
								"A quantidade desejada é maior que o estoque disponível (%d). Adicionando (%d) ao carrinho",
								qtdDisponivel, qtdDisponivel));
						qtdPossivel = qtdDisponivel;
					}

					comercio.addProdutoCarrinho(produtoEscolhido, indiceDaCategoria - 1, indiceDoProduto - 1,
							qtdDisponivel, qtdPossivel);
				}

			} catch (NumberFormatException e) {
				System.out.println("Opção inválida, o campo aceita apenas números inteiros.");
			} catch (Exception e) {
				System.out.println("Erro desconhecido ao tentar selecionar produto: " + e.getLocalizedMessage());
				e.printStackTrace();
			}

		}
	}

	private static void exibirMenuBuscaDeProduto() {
		System.out.println("======== Categorias Do Ecommerce ========\n");

		comercio.listarCategorias();

		System.out.println("\nEscolha a categoria que deseja buscar por um produto");
		Scanner scEcolherProdutoEmCategoria = new Scanner(System.in);
		int indiceDaCategoria = Integer.parseInt(scEcolherProdutoEmCategoria.nextLine());

		Categoria categoria = comercio.getCategoria(indiceDaCategoria - 1);

		System.out.println("\nDigite o nome do produto a ser pesquisado: ");
		String nomeDoProdutoAPesquisar = scEcolherProdutoEmCategoria.nextLine();

		List<Produto> produtosPesquisados = categoria.listaInfoProdutosPesquisado(nomeDoProdutoAPesquisar);

		if (produtosPesquisados.size() != 0) {
			System.out.println("\nEscolha o produto que deseja adicionar ao carrinho.\n");
			int indiceDoProduto = Integer.parseInt(scEcolherProdutoEmCategoria.nextLine());

			System.out.println("\nEscolha a quantidade: \n");
			int qtdProduto = Integer.parseInt(scEcolherProdutoEmCategoria.nextLine());

			Produto produtoEscolhido = produtosPesquisados.get(indiceDoProduto - 1);

			((Cliente) usuarioLogado).adicionaProduto(new Produto(produtoEscolhido.getNome(),
					produtoEscolhido.getPreco(), qtdProduto, produtoEscolhido.getVendedor(), 
					produtoEscolhido.getCategoria()));

			System.out.println("\nItem adicionado ao carrinho.\n");

			produtoEscolhido.setQuantDisponivel(produtoEscolhido.getQuantDisponivel() - qtdProduto);
		}
	}

	private static void exibirOpcoesDoUsuario() {
		if (usuarioLogado instanceof Administrador) {
			Scanner receber = new Scanner(System.in);
			System.out.println("Ol� Adm, bem-vindo a Akinat's Place! Escolha uma op��o abaixo:\n"
					+ "1. Listar categorias\n2. Adicionar categoria\n3. Remover categoria\n4. Logout\n");
			int opcao = Integer.parseInt(receber.nextLine());

			if (opcao == 1) { // Vai listar as categorias dispon�veis.
				comercio.listarCategorias();

			} else if (opcao == 2) {
				// Pedir nome da nova categoria, criar objeto do tipo Categoria e adicionar;
				System.out.println("Por favor, insira o nome da nova categoria que deseja criar ");
				Scanner nomeCategoriaSc = new Scanner(System.in);
				String nomeCategoria = nomeCategoriaSc.nextLine();
				adicionarCategoria(new Categoria(nomeCategoria));

			} else if (opcao == 3) {
				// Pedir nome da categoria a remover e chamar o m�todo remover;
				System.out.println("Qual categoria que deseja remover? ");
				Scanner nomeCategoriaRemover = new Scanner(System.in);
				String categoriaRemovida = nomeCategoriaRemover.nextLine();
				comercio.removeCategoria(categoriaRemovida);
				GerenciadorBD.salvarDados(comercio);
				System.out.println("Categoria " + categoriaRemovida + " removida com sucesso.");

			} else if (opcao == 4) {
				// Sair do sistema (fazer logout);
				usuarioLogado = null;
				System.out.println("Usu�rio deslogado com sucesso.");

			} else {
				System.out.println("Op��o Inv�lida");
			}

		} else if (usuarioLogado instanceof Vendedor) {
			Scanner receberDoVendedor = new Scanner(System.in);
			System.out.println("Ol�, Vendedor, bem-vindo a Akinat's Place! Escolha uma op��o abaixo:\n"
					+ "1. Listar Produtos\n2. Criar Produto\n3. Remover Produto\n4. Acessar Pedidos\n5. Relatórios Financeiros\n6. Logout\n");
			int decisao = Integer.parseInt(receberDoVendedor.nextLine());

			if (decisao == 1) { // Vai listar todos os produtos j� cadastrados.
				comercio.listaProdutos();

			} else if (decisao == 2) { // O vendedor vai criar um novo produto (com nome, pre�o e quantidade) e
										// adicion�-lo em uma categoria
				Scanner receberCampo = new Scanner(System.in);
				System.out.println("Qual o produto deseja criar? ");
				String nomeProduto = receberCampo.nextLine();

				System.out.println("Qual o pre�o do produto? ");
				float precoProduto = Float.parseFloat(receberCampo.nextLine());

				System.out.println("Qual a quantidade de produtos dispon�veis? ");
				int quantidadeProduto = Integer.parseInt(receberCampo.nextLine());

				System.out.println("Escolha uma categoria para adicionar seu produto ");
				comercio.listarCategorias();
				int categoriaSelecionada = Integer.parseInt(receberCampo.nextLine());
				Produto produto = new Produto(nomeProduto, precoProduto, quantidadeProduto, usuarioLogado.getLogin(), null);

				// Adicionar o produto em alguma categoria
				comercio.adicionaProduto(produto, categoriaSelecionada - 1);
				GerenciadorBD.salvarDados(comercio);

				System.out.println(
						"Produto adicionado com sucesso na lista do(a) vendedor(a) " + usuarioLogado.getNome());
				// receberCampo.close();

			} else if (decisao == 3) {
				// Op��o de remover produto
				System.out.println("Deseja remover qual produto? ");
				Scanner receberNomeProdutoParaRemover = new Scanner(System.in);
				String nomeProduto = receberNomeProdutoParaRemover.nextLine();
				comercio.removeProduto(nomeProduto);
				GerenciadorBD.salvarDados(comercio);
				System.out
						.println("Produto removido com sucesso da lista do(a) vendedor(a) " + usuarioLogado.getNome());
				// receberNomeProdutoParaRemover.close();

			} else if (decisao == 4) {
				Map<Integer, Map<Integer, Map<Integer, Produto>>> vendas = comercio
						.getVendasVendedor(usuarioLogado.getLogin());

				System.out.println("Escolha o cliente: ");
				Scanner receberClienteAAtualizar = new Scanner(System.in);
				int clienteAAtualizar = receberClienteAAtualizar.nextInt();

				if (!vendas.containsKey(clienteAAtualizar - 1)) {
					System.out.println("Opção inválida\n");
				} else {
					Map<Integer, Map<Integer, Produto>> pedidos = vendas.get(clienteAAtualizar - 1);

					System.out.println("Escolha o pedido que deseja atualizar: ");
					Scanner receberPedidoAAtualizar = new Scanner(System.in);
					int pedidoAAtualizar = receberPedidoAAtualizar.nextInt();

					if (!pedidos.containsKey(pedidoAAtualizar - 1)) {
						System.out.println("Opção inválida\n");
					} else {
						Map<Integer, Produto> produtos = pedidos.get(pedidoAAtualizar - 1);

						System.out.println("Escolha o produto que deseja atualizar: ");
						Scanner receberProdutoAAtualizar = new Scanner(System.in);
						int produtoAAtualizar = receberProdutoAAtualizar.nextInt();

						if (!produtos.containsKey(produtoAAtualizar - 1)) {
							System.out.println("Opção inválida\n");
						} else {
							System.out
									.println("Escolha uma o novo status do produto:\n" + Utils.getMenuStatusProduto());
							Scanner receberNovoStatusProduto = new Scanner(System.in);
							int novoStatusProduto = receberNovoStatusProduto.nextInt();
							StatusProduto status = StatusProduto.getStatus(novoStatusProduto);

							// TODO: criar metodo no comercio que recebe os idx de usuario, pedido e produto
							// e atualiza diretamente
							comercio.atualizarStatusPedidoCliente(clienteAAtualizar - 1, pedidoAAtualizar - 1,
									produtoAAtualizar - 1, status);
							GerenciadorBD.salvarDados(comercio);
						}
					}
				}

			} else if (decisao == 5) {
				System.out.println("Selecione a data de INÍCIO (dd/MM/AAAA) "
						+ "do período do relatório (enter para ignorar): \n");
				
				Scanner receberDataInicioRelatorioDoVendedor = new Scanner(System.in);
				String dataInicioString = receberDataInicioRelatorioDoVendedor.nextLine();
				Date dataInicio = Utils.getDateDeString(dataInicioString);
				
				System.out.println("Selecione a data de FIM (dd/MM/AAAA) "
						+ "do período do relatório (enter para ignorar): \n");
				
				Scanner receberDataFimRelatorioDoVendedor = new Scanner(System.in);
				String dataFimString = receberDataFimRelatorioDoVendedor.nextLine();
				Date dataFim = Utils.getDateDeString(dataFimString);
				
				System.out.println("Escolha o tipo de relatório:\n"
						+ "1. Valor total no período\n2. Valor total por categoria\n3. Valor total por produto\n");
				Scanner receberOpcaoRelatorioDoVendedor = new Scanner(System.in);
				int opcao = Integer.parseInt(receberOpcaoRelatorioDoVendedor.nextLine());

				switch (opcao) {
				case 1:
					RelatorioFinanceiro.exibirTotalVendasNoPeriodo(comercio, usuarioLogado.getLogin(), dataInicio, dataFim);
					break;
				case 2:
					RelatorioFinanceiro.exibirVendasPorCategoriaNoPeriodo(comercio, usuarioLogado.getLogin(), dataInicio, dataFim);
					break;
				case 3:
					RelatorioFinanceiro.exibirTotalVendasPorProduto(comercio, usuarioLogado.getLogin(), dataInicio, dataFim);
					break;
				default:
					System.out.println("Opção inválida");
					break;
				}

			} else if (decisao == 6) {
				// Sair do sistema (fazer logout);
				usuarioLogado = null;
				System.out.println("Usu�rio deslogado com sucesso.");

			} else {
				System.out.println("Op��o Inv�lida");
			}

			// receberDoVendedor.close();
		} else if (usuarioLogado instanceof Cliente) {
			Scanner receberDoCliente = new Scanner(System.in);
			System.out.println("Ol� Cliente, bem-vindo a Akinat's Place! Escolha uma op��oo abaixo:\n"
					+ "1. Escolher Produtos para o Carrinho\n2. Buscar Produto\n3. Ver carrinho de compra\n4. Exibir histórico de pedidos\n5. Finalizar Compra\n6. Logout\n");
			int decisao = Integer.parseInt(receberDoCliente.nextLine());

			if (decisao == 1) { // Vai listar todos as categorias.
				exibirOpcaoEscolherProdutosCliente();
			} else if (decisao == 2) {
				exibirMenuBuscaDeProduto();
			} else if (decisao == 3) {
				((Cliente) usuarioLogado).exibirDetalhesCarrinho();
			} else if (decisao == 4) {
				((Cliente) usuarioLogado).exibirHistoricoPedidos();
			} else if (decisao == 5) {
				finalizarPedido();
			} else if (decisao == 6) {
				usuarioLogado = null;
				System.out.println("Usu�rio deslogado com sucesso.");

			}
		} else {
			System.out.println("Tipo de usuário inválido.");
			System.exit(0);
		}
	}

	public static void finalizarPedido() {
//		((Cliente) usuarioLogado).finalizarPedido();
		comercio.finalizarPedido(usuarioLogado.getLogin());
		System.out.println("Obrigado pela compra. Até a próxima!\n");
		GerenciadorBD.salvarDados(comercio);
	}

}