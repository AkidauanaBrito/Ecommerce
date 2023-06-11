package eCommerce.bd;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eCommerce.Administrador;
import eCommerce.Categoria;
import eCommerce.Cliente;
import eCommerce.Ecommerce;
import eCommerce.Pedido;
import eCommerce.Produto;
import eCommerce.Usuario;
import eCommerce.Vendedor;

public class GerenciadorBD {

	public static final String FILE_NAME = "ecommerce.json";

	public static JSONObject criarJSONEcommerce(Ecommerce ecommerce) {
		JSONObject ecommerceObj = new JSONObject();
		ecommerceObj.put(Colunas.COL_ECOMMERCE_NOME, ecommerce.getNome());
		ecommerceObj.put(Colunas.COL_ECOMMERCE_CATEGORIAS, converterListEmJSONArray(ecommerce.getCategorias()));
		ecommerceObj.put(Colunas.COL_ECOMMERCE_USUARIOS, converterListEmJSONArray(ecommerce.getUsuarios()));

		return ecommerceObj;
	}

	public static JSONObject criarJSONCategoria(Categoria categoria) {
		JSONObject categoriaObj = new JSONObject();
		categoriaObj.put(Colunas.COL_CATEGORIA_NOME, categoria.getNome());
		categoriaObj.put(Colunas.COL_CATEGORIA_PRODUTOS, converterListEmJSONArray(categoria.getProdutos()));

		return categoriaObj;
	}

	public static JSONObject criarJSONPedido(Pedido pedido) {
		JSONObject pedidoObj = new JSONObject();
		pedidoObj.put(Colunas.COL_PEDIDO_PRODUTOS, converterListEmJSONArray(pedido.getProdutos()));
		pedidoObj.put(Colunas.COL_PEDIDO_STATUS, pedido.getStatusPedido().getStatus());

		return pedidoObj;
	}

	public static JSONObject criarJSONProduto(Produto produto) {
		JSONObject produtoObj = new JSONObject();
		produtoObj.put(Colunas.COL_PRODUTO_NOME, produto.getNome());
		produtoObj.put(Colunas.COL_PRODUTO_PRECO, produto.getPreco());
		produtoObj.put(Colunas.COL_PRODUTO_QUANTDISPONIVEL, produto.getQuantDisponivel());

		return produtoObj;
	}

	private static JSONObject criarJSONUsuario(Usuario usuario) {
		JSONObject usuarioObj = new JSONObject();
		usuarioObj.put(Colunas.COL_USUARIO_LOGIN, usuario.getLogin());
		usuarioObj.put(Colunas.COL_USUARIO_SENHA, usuario.getSenha());
		usuarioObj.put(Colunas.COL_USUARIO_NOME, usuario.getNome());

		return usuarioObj;
	}

	public static JSONObject criarJSONAdm(Administrador adm) {
		JSONObject administrador = criarJSONUsuario(adm);

		return administrador;
	}

	public static JSONObject criarJSONCliente(Cliente cliente) {
		JSONObject clienteObj = criarJSONUsuario(cliente);
		clienteObj.put(Colunas.COL_CLIENTE_PEDIDO, criarJSONPedido(cliente.getCarrinho()));

		return clienteObj;
	}

	public static JSONObject criarJSONVendedor(Vendedor vendedor) {
		JSONObject vendedorObj = criarJSONUsuario(vendedor);
		vendedorObj.put(Colunas.COL_VENDEDOR_PRODUTOS, converterListEmJSONArray(vendedor.getProdutos()));

		return vendedorObj;
	}

	public static <T> JSONArray converterListEmJSONArray(List<T> lista) {
		JSONArray jsonList = new JSONArray();
		for (T item : lista) {
			if (item instanceof Categoria) {
				jsonList.add(criarJSONCategoria((Categoria) item));
			} else if (item instanceof Cliente) {
				jsonList.add(criarJSONCliente((Cliente) item));
			} else if (item instanceof Vendedor) {
				jsonList.add(criarJSONVendedor((Vendedor) item));
			} else if (item instanceof Administrador) {
				jsonList.add(criarJSONAdm((Administrador) item));
			} else if (item instanceof Produto) {
				jsonList.add(criarJSONProduto((Produto) item));
			}
		}
		return jsonList;
	}

	@SuppressWarnings("unchecked")
	public static void salvarDados(Ecommerce ecommerce) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				RuntimeTypeAdapterFactory<Usuario> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
						.of(Usuario.class, "tipo")
						.registerSubtype(Administrador.class, "adm")
						.registerSubtype(Cliente.class, "cliente")
						.registerSubtype(Vendedor.class, "vendedor");
				
				Gson gson = new GsonBuilder()
						.excludeFieldsWithoutExposeAnnotation()
						.setPrettyPrinting()
						.registerTypeAdapterFactory(runtimeTypeAdapterFactory)
						.create();
				String jsonOutput = gson.toJson(ecommerce);

//				JSONObject ecommerceObj = criarJSONEcommerce(ecommerce);

				// Write JSON file
				try {
					// We can write any JSONArray or JSONObject instance to the file
					FileWriter file = new FileWriter(FILE_NAME);
//					String jsonString = ecommerceObj.toJSONString();
					System.out.println(String.format("Salvando dados: %s", /* jsonString */jsonOutput));

					file.write(/* jsonString */jsonOutput);
					file.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static Ecommerce recuperarDados() {
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(FILE_NAME)) {
			// Read JSON file
			JSONObject obj = (JSONObject) jsonParser.parse(reader);

			RuntimeTypeAdapterFactory<Usuario> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
					.of(Usuario.class, "tipo")
					.registerSubtype(Administrador.class, "adm")
					.registerSubtype(Cliente.class, "cliente")
					.registerSubtype(Vendedor.class, "vendedor");
			Gson gson = new GsonBuilder()
					.registerTypeAdapterFactory(runtimeTypeAdapterFactory)
					.create();
			return gson.fromJson(obj.toJSONString(), Ecommerce.class);
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
