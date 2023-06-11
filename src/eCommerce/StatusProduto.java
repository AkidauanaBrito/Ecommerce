package eCommerce;

import java.util.HashMap;
import java.util.Map;

public enum StatusProduto {

	PREPARANDO("preparando"), // o vendedor está preparando o pedido para o envio.
	EM_POSSE_TRANSPORTADORA("em_posse_transportadora"), // o vendedor despachou o pedido.
	EM_TRANSITO("em_transito"), // o pedido está a caminho.
	ROTA_ENTREGA("rota_entrega"), // o pedido está na última etapa da entrega.
	ENTREGUE("entregue"), // o pedido foi entregue ao comprador.
	EXTRAVIADO("extraviado"); // o pedido não foi localizado.

	private String status;

	StatusProduto(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public static StatusProduto getStatus(int status) {
		switch (status) {
		case 1:
			return PREPARANDO;
		case 2:
			return EM_POSSE_TRANSPORTADORA;
		case 3:
			return EM_TRANSITO;
		case 4:
			return ROTA_ENTREGA;
		case 5:
			return ENTREGUE;
		case 6:
			return EXTRAVIADO;
		default:
			return PREPARANDO;
		}
	}
}
