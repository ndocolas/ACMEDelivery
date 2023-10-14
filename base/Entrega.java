package base;
public class Entrega {

	private int codigo;
	private double valor;
	private String descricao;
	private Cliente cliente;

	public Entrega(int codigo, double valor, String descricao, Cliente cliente) {
			this.codigo = codigo;
			this.valor = valor;
			this.descricao = descricao;
			this.cliente = cliente;
	}

	public int getCodigo() {
		return codigo;
	}

	public double getValor() {
		return valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public Cliente getCliente() {
		return cliente;
	}


}
