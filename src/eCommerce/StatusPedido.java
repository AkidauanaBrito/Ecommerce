package eCommerce;

import java.util.HashMap;
import java.util.Map;

public enum StatusPedido {

	PAGAMENTO_PENDENTE("pagamento_pendente"),
	PAGAMENTO_APROVADO("pagamento_aprovado"),
	ENTREGUE("entregue"); //o pedido foi entregue ao comprador.
	
	private String status;
	
	StatusPedido(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
}
