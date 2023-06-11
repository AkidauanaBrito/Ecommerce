package eCommerce;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Utils {
	
	public static final String FORMATO_DATA_DD_MM_YYYY = "dd/MM/yyyy";

	public static Map<StatusPedido, String> descricaoStatusPedidoMap = new HashMap<StatusPedido, String>() {
		{
			put(StatusPedido.PAGAMENTO_PENDENTE, "Pagamento Pendente");
			put(StatusPedido.PAGAMENTO_APROVADO, "Pagamento Aprovado");
			put(StatusPedido.ENTREGUE, "Entregue");
		}
	};
	
	public static Map<StatusProduto, String> descricaoStatusProdutoMap = new TreeMap<StatusProduto, String>() {
		{
			put(StatusProduto.PREPARANDO, "Preparando para enviar");
			put(StatusProduto.EM_POSSE_TRANSPORTADORA, "Em posse da transportadora");
			put(StatusProduto.EM_TRANSITO, "Em trânsito");
			put(StatusProduto.ROTA_ENTREGA, "Última etapa da entrega");
			put(StatusProduto.EXTRAVIADO, "Extraviado. Por favor, entre em contato com a central de atendimento.");
		}
	};
	
	public static String getMenuStatusProduto() {
		StringBuilder sb = new StringBuilder();
		int pos = 1;
		for (Map.Entry<StatusProduto, String> entry : descricaoStatusProdutoMap.entrySet()) {
			String val = entry.getValue();
			sb.append(pos).append(". ").append(val).append("\n");
			pos++;
		}
		return sb.toString();
	}
	
	public static String getDataAgora() {
		SimpleDateFormat formatter = new SimpleDateFormat(FORMATO_DATA_DD_MM_YYYY);
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}
	
	public static Date getDateDeString(String data) {
	    try {
			return new SimpleDateFormat(FORMATO_DATA_DD_MM_YYYY).parse(data);
		} catch (ParseException e) {
			System.out.println("Formato de data inválido: " + e.getMessage());
		} 
	    return null;
	}
}
