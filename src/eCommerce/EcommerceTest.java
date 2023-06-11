package eCommerce;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EcommerceTest {

	@Test
	void testUsuarioALogarExiste() {
		Ecommerce ecommerce = new Ecommerce("AkiNat's Place");
		Usuario usuario = new Usuario("aki", "123", "Akidauana", "Vendedor");
		assertEquals(null, ecommerce.usuarioALogarExiste("aki", "123"));
		ecommerce.adicionaUsuario(usuario);
		assertEquals(usuario, ecommerce.usuarioALogarExiste("aki", "123"));
	}

	@Test
	void testLoginEhValido() {
		Ecommerce ecommerce = new Ecommerce("AkiNat's Place");
		assertEquals(false, ecommerce.loginEhValido("ab"));
		assertEquals(true, ecommerce.loginEhValido("aki"));
	}

	@Test
	void testSenhaEhValida() {
		Ecommerce ecommerce = new Ecommerce("AkiNat's Place");
		assertEquals(false, ecommerce.senhaEhValida(""));
		assertEquals(false, ecommerce.senhaEhValida(null));
		assertEquals(true, ecommerce.senhaEhValida("akidauana"));
	}

	@Test
	void testAdicionaUsuario() {
		Ecommerce ecommerce = new Ecommerce("kiNat's Place");
		assertEquals(0, ecommerce.getUsuarios().size());
		ecommerce.adicionaUsuario(new Usuario("aki", "123", "Akidauana", "Vendedor"));
		assertEquals(1, ecommerce.getUsuarios().size());
	}

	@Test
	void testAdicionaCategoria() {
		Ecommerce ecommerce = new Ecommerce("AkiNat's Place");
		assertEquals(0, ecommerce.getCategorias().size());
		ecommerce.adicionaCategoria(new Categoria("Feminino"));
		assertEquals(1, ecommerce.getCategorias().size());
	}

	@Test
	void testRemoveCategoria() {
		Ecommerce ecommerce = new Ecommerce("AkiNat's Place");
		ecommerce.adicionaCategoria(new Categoria("Feminino"));
		assertEquals(1, ecommerce.getCategorias().size());
		ecommerce.removeCategoria("Feminino");
		assertEquals(0, ecommerce.getCategorias().size());
	}
}
