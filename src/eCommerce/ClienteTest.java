package eCommerce;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ClienteTest {

	@Test
	void testAdicionaProduto() {
		Cliente cliente = new Cliente("jem", "123", "Jemerson");
		assertEquals(0, cliente.getCarrinho().getProdutos().size());
		cliente.adicionaProduto(new Produto("Calça", 25, 35, "Jemerson", "Feminino"));
		assertEquals(1, cliente.getCarrinho().getProdutos().size());
		cliente.adicionaProduto(new Produto("Blusa", 25, 35, "Jemerson", "Feminino"));
		assertEquals(2, cliente.getCarrinho().getProdutos().size());
	}
	
	@Test
	void testRemoverProduto() {
		Cliente cliente = new Cliente("jem", "123", "Jemerson");
		assertEquals(0, cliente.getCarrinho().getProdutos().size());
		cliente.adicionaProduto(new Produto("Calça", 25, 35, "Jemerson", "Feminino"));
		assertEquals(1, cliente.getCarrinho().getProdutos().size());
		cliente.removerProdutoCarrinho(new Produto("Calça", 25, 35, "Jemerson", "Feminino"));
		assertEquals(0, cliente.getCarrinho().getProdutos().size());
	}
}
