package projeto.perfil;

import excessoes.SomException;
import gerenciadorDeDados.DadosDoSistema;

import java.util.Comparator;

public class NumeroDeVezesFavoritado implements Comparator<Integer>{

	@Override
	public int compare(Integer som1Id, Integer som2Id) {
		DadosDoSistema dados = DadosDoSistema.getInstance();
		Som som1 = null, som2 = null;
		try {
			som1 = dados.Som(som1Id);
			som2 = dados.Som(som2Id);
		} catch (SomException e) {
			e.printStackTrace();
		}
		if(som1.getQtdeFavoritados() > som2.getQtdeFavoritados()) return -1;
		else if(som1.getQtdeFavoritados() < som2.getQtdeFavoritados())	return 1;
		return -1*som1.compareTo(som2);
	}
}
