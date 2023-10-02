import java.util.ArrayList;

public class Clientela {

	private ArrayList<Cliente> listaClientes;

	public Clientela() {
		listaClientes = new ArrayList<>();
	}

	public ArrayList<Cliente> getListaCliente() {
		return listaClientes;
	}

	public boolean cadastraCliente(Cliente cliente) {
		for(Cliente c : listaClientes) {
			if(c.getEmail().equalsIgnoreCase(cliente.getEmail())) {
				return false;
			}
		}
		return listaClientes.add(cliente);
	}

	public Cliente pesquisaCliente(String email) {
		for(Cliente c : listaClientes) {
			if(email.equalsIgnoreCase(c.getEmail())) {
				return c;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		System.out.println("\nClientes: ");
		for(Cliente c : listaClientes) {
			System.out.print("\nNome: " + c.getNome() + "\nEmail: " + c.getEmail() + "\nEndereco: " + c.getEndereco() + "\n");
		}
		return "";
	}
}
