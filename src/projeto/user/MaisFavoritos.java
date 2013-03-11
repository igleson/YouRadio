package projeto.user;

import java.util.Comparator;

public class MaisFavoritos implements Comparator<Usuario>{

	private Usuario usuario;
	
	public MaisFavoritos(Usuario usuario){
		this.usuario = usuario;
	}
	
	@Override
	public int compare(Usuario o1, Usuario o2) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
