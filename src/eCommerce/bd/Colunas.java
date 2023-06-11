package eCommerce.bd;

public class Colunas {
	
	//Colunas comuns de Usuário
	public static String COL_USUARIO_NOME = "nome";
	public static String COL_USUARIO_LOGIN = "login";
	public static String COL_USUARIO_SENHA = "senha";
	
	//Colunas de Cliente
	public static String COL_CLIENTE_PEDIDO = "pedido";
	
	//Colunas de Vendedor
	public static String COL_VENDEDOR_PRODUTOS = "produtos";
	
	//Colunas de Categoria
	public static String COL_CATEGORIA_NOME = "nome";
	public static String COL_CATEGORIA_PRODUTOS = "produtos";
	
	//Colunas de Ecommerce
	public static String COL_ECOMMERCE_NOME = "nome";
	public static String COL_ECOMMERCE_CATEGORIAS = "categorias";
	public static String COL_ECOMMERCE_USUARIOS = "usuarios";
	
	//Colunas de Pedido
	public static String COL_PEDIDO_PRODUTOS = "produtos";
	public static String COL_PEDIDO_STATUS = "statusPedido";
	
	//Colunas de Produto
	public static String COL_PRODUTO_NOME = "nome";
	public static String COL_PRODUTO_PRECO = "preco";
	public static String COL_PRODUTO_QUANTDISPONIVEL = "quantDisponivel";
}
