package controle;

import java.util.ArrayList;
import java.util.stream.Collectors;

import base.Cliente;

public class Clientela {

	private ArrayList<Cliente> listaClientes;

	public Clientela() {listaClientes = new ArrayList<>();}

	public boolean cadastraCliente(Cliente cliente) {
		return (listaClientes.stream()
            .anyMatch(c -> c.getEmail().equalsIgnoreCase(cliente.getEmail()))) ?
			false
			:
			listaClientes.add(cliente);
	}

	public Cliente pesquisaCliente(String email) {
		return listaClientes.stream().filter(c -> email.equalsIgnoreCase(c.getEmail())).findFirst().orElse(null);
	}

	@Override
	public String toString() {
		return listaClientes.stream().map(c -> String.format("%nNome: %s%nEmail: %s%nEndereco: %s%n", c.getNome(), c.getEmail(), c.getEndereco())).collect(Collectors.joining());
	}
	
	public ArrayList<Cliente> getListaCliente() {return listaClientes;}
}
