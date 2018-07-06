package report;

import java.util.ArrayList;
import java.util.List;

public class IngredientiA {

	public List<String> lista = new ArrayList<>();
	
	public IngredientiA(List<String> lista) {
		this.lista = lista;
	}

	public List<String> getLista() {
		return lista;
	}

	public void setLista(List<String> lista) {
		this.lista = lista;
	}
	
}
