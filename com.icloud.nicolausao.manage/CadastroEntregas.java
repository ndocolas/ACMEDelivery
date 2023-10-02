import java.util.ArrayList;

public class CadastroEntregas {

	private ArrayList<Entrega> entregas;

	public CadastroEntregas () {
		entregas = new ArrayList<>();
	}

	public ArrayList<Entrega> getListaEntregas() {
		return entregas;
	}

	public boolean cadastraEntrega(Entrega entrega) {
		if(entrega!=null) {
			for(Entrega e : entregas) {
				if(entrega.getCodigo() == e.getCodigo()) {
					return false;
				}
			}
			if(entrega.getCliente()!=null) {
				entrega.getCliente().adicionaEntrega(entrega);
				return entregas.add(entrega);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


	public Entrega pesquisaEntrega(int codigo) {
		for(Entrega e : entregas) {
			if(e.getCodigo() == codigo) {
				return e;
			}
		}
		return null;
	}

	public ArrayList<Entrega> pesquisaEntrega(String email) {
		ArrayList<Entrega> entregasCliente = new ArrayList<>();
		for(Entrega e : entregas) {
			if(e.getCliente().getEmail().equalsIgnoreCase(email)) {
				entregasCliente.add(e);
			}
		}
		if(entregasCliente.isEmpty()) {
			return null;
		} else {
			return entregasCliente;
		}
	}

	@Override
	public String toString() {
		System.out.println("Entregas: ");
		for(Entrega e : entregas) {
			System.out.println("\nCodigo: " + e.getCodigo() + "\nValor: " + e.getValor() + "\nDescricao: " + e.getDescricao() + "\nCliente: " + e.getCliente().getEmail());
		}
		return " ";
	}

}
