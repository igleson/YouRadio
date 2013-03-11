package projeto.user;

import java.util.Comparator;
import java.util.Map;

public class qtdeDeFavoritos implements Comparator<Integer> {

	private Map<Integer, Integer> qtdeDeFavoritados;
	
	public qtdeDeFavoritos(Map<Integer, Integer> qtdeDeFavoritados) {
		this.qtdeDeFavoritados = qtdeDeFavoritados;
	}

	@Override
	public int compare(Integer int1, Integer int2) {
		if(!qtdeDeFavoritados.containsKey(int1)) qtdeDeFavoritados.put(int1, 0);
		if(!qtdeDeFavoritados.containsKey(int2)) qtdeDeFavoritados.put(int2, 0);
		if(qtdeDeFavoritados.get(int1) >= qtdeDeFavoritados.get(int2)) return -1;
		else return 1;
	}

}
