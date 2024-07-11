package base;
import java.util.ArrayList;

public class Cliente {

	private String email, nome, endereco;
	private ArrayList<Entrega> entregasCliente = new ArrayList<>();

	public Cliente(String nome, String email, String endereco) {
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
	}

	public double somatorioEntregas() {
		return entregasCliente.stream().mapToDouble(Entrega::valor).sum();
	}

	public boolean temEntregas() {return !entregasCliente.isEmpty();}

	public void retornaDadosCliente() {System.out.printf("Nome: %s%nEmail: %s%nEndereco: %s%n", nome, email, endereco);}

	public void retornaDadosEntrega() {
		entregasCliente.stream().forEach(e -> System.out.printf("7;%s;%d;%.2f;%s%n", email, e.codigo(), e.valor(), e.descricao()));
	}

	public boolean adicionaEntrega(Entrega entrega) {return entregasCliente.add(entrega);}	
	public ArrayList<Entrega> pesquisaEntregas() {return entregasCliente;}	
	public String getEmail() {return email;}	
	public String getNome() {return nome;}	
	public String getEndereco() {return endereco;}
}
