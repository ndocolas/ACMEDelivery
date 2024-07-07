package controle;

import java.util.ArrayList;
import java.util.stream.Collectors;

import base.Entrega;

public class CadastroEntregas {

	private ArrayList<Entrega> entregas;

	public CadastroEntregas () {entregas = new ArrayList<>();}

    public boolean cadastraEntrega(Entrega entrega) {
        if (entrega == null || entrega.getCliente() == null) return false;
        
        return (entregas.stream()
            .anyMatch(e -> e.getCodigo() == entrega.getCodigo())) ?
			false
			:
        	(entregas.add(entrega) && entrega.getCliente().adicionaEntrega(entrega));
    }

	public Entrega pesquisaEntrega(int codigo) {
		return entregas.stream().filter(e -> e.getCodigo() == codigo).findFirst().orElse(null);
	}

	public ArrayList<Entrega> pesquisaEntrega(String email) {
		return entregas.stream().filter(e -> e.getCliente().getEmail().equalsIgnoreCase(email))
		.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}

	@Override
	public String toString() {
		return entregas.stream()
			.map(e -> String.format("%nCodigo: %d%nValor: %.2f%nDescricao: %s%nCliente: %s%n",
				e.getCodigo(), e.getValor(), e.getDescricao(), e.getCliente().getEmail()))
			.collect(Collectors.joining());
	}
	
	public ArrayList<Entrega> getListaEntregas() {return entregas;}

}
