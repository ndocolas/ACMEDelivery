package controle;

import java.util.ArrayList;
import java.util.stream.Collectors;

import base.Entrega;

public class CadastroEntregas {

	private ArrayList<Entrega> entregas;

	public CadastroEntregas () {entregas = new ArrayList<>();}

    public boolean cadastraEntrega(Entrega entrega) {
        if (entrega == null || entrega.cliente() == null) return false;
        
        return (entregas.stream()
            .anyMatch(e -> e.codigo() == entrega.codigo())) ?
			false
			:
        	(entregas.add(entrega) && entrega.cliente().adicionaEntrega(entrega));
    }

	public Entrega pesquisaEntrega(int codigo) {
		return entregas.stream().filter(e -> e.codigo() == codigo).findFirst().orElse(null);
	}

	public ArrayList<Entrega> pesquisaEntrega(String email) {
		return entregas.stream().filter(e -> e.cliente().getEmail().equalsIgnoreCase(email))
		.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}

	@Override
	public String toString() {
		return entregas.stream()
			.map(e -> String.format("%nCodigo: %d%nValor: %.2f%nDescricao: %s%nCliente: %s%n",
				e.codigo(), e.valor(), e.descricao(), e.cliente().getEmail()))
			.collect(Collectors.joining());
	}
	
	public ArrayList<Entrega> getListaEntregas() {return entregas;}

}
