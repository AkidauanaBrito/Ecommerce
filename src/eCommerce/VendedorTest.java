package eCommerce;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VendedorTest {

	@Test
	void testAdicionaProduto() {
		Vendedor vendedor = new Vendedor("aki", "123", "Akidauana");
		assertEquals(0, vendedor.getProdutos().size());
		vendedor.adicionaProduto(new Produto("Calça", 25, 35, "Akidauana", "Feminino"));
		assertEquals(1, vendedor.getProdutos().size());
		vendedor.adicionaProduto(new Produto("Blusa", 25, 35, "Akidauana", "Feminino"));
		assertEquals(2, vendedor.getProdutos().size());
	}
	
	@Test
	void testRemoverProduto() {
		Vendedor vendedor = new Vendedor("aki", "123", "Akidauana");
		assertEquals(0, vendedor.getProdutos().size());
		vendedor.adicionaProduto(new Produto("Calça", 25, 35, "Akidauana", "Feminino"));
		assertEquals(1, vendedor.getProdutos().size());
		vendedor.removeProduto("Calça");
		assertEquals(0, vendedor.getProdutos().size());
	}
}
