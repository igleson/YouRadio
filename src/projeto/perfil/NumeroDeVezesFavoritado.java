package projeto.perfil;

import java.util.Comparator;

public class NumeroDeVezesFavoritado implements Comparator<Som>{

	@Override
	public int compare(Som som1, Som som2) {
		if(som1.getQtdeFavoritados() > som2.getQtdeFavoritados()) return -1;
		else if(som1.getQtdeFavoritados() < som2.getQtdeFavoritados())	return 1;
		return som1.compareTo(som2);
	}
}
