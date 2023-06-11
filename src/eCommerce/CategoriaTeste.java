package eCommerce;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CategoriaTeste {

	@Test
	public void testAdicionaProduto() { //testa se 
		Categoria categoria = new Categoria("Feminino");
		
		assertEquals(0, categoria.getProdutos().size());
		
		categoria.adicionaProduto(new Produto("Calça", 25, 35, "Akidauana", "Feminino"));
		categoria.adicionaProduto(new Produto("Casaco", 50, 10, "Akidauana", "Feminino"));
		categoria.adicionaProduto(new Produto("Sapato", 30, 11, "Akidauana", "Feminino"));
		
		assertEquals(3, categoria.getProdutos().size());
	}

	@Test
	public void testRemoveProduto() {
		Categoria categoria = new Categoria("Feminino");
		
		Produto produto = new Produto("Calça", 25, 35, "Akidauana", "Feminino"); //criou produto e atribuiu a variavel produto
		
		categoria.adicionaProduto(produto); 
		categoria.adicionaProduto(new Produto("Casaco", 50, 10, "Akidauana", "Feminino")); //n pode ser removido, pois nao esta referenciado por variavel
		
		assertEquals(2, categoria.getProdutos().size());
		
		categoria.removeProduto(produto);
		
		assertEquals(1, categoria.getProdutos().size());
	}

}
