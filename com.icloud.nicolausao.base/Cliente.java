import java.util.ArrayList;

public class Cliente {

	private String email;
	private String nome;
	private String endereco;
	private ArrayList<Entrega> entregasCliente = new ArrayList<>();

	public Cliente(String nome, String email, String endereco) {
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
	}

	public double somatorioEntregas() {
		double contador = 0.0;
		for(Entrega e : entregasCliente) {
			contador += e.getValor();
		}
		return contador;
	}

	@Override
	public String toString() {
		return nome + email + endereco;
	}

	public boolean temEntregas() {
		if(entregasCliente.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public void retornaDadosCliente() {
		System.out.println("Nome: " + nome
				+ "\nEmail: " + email
				+ "\nEndereco: " + endereco);
	}

	public void retornaDadosEntrega() {
		for(Entrega e : entregasCliente) {
			System.out.println("7;" + email + ";" + e.getCodigo() + ";" + e.getValor() + ";" + e.getDescricao());
		}
	}

	public boolean adicionaEntrega(Entrega entrega) {
			return entregasCliente.add(entrega);
	}

	public ArrayList<Entrega> pesquisaEntregas() {
		return entregasCliente;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}



}
