package projeto.comparator;
import gerenciadorDeDados.DadosDoSistema;

import java.util.Comparator;

import projeto.user.Usuario;


public class ComparaFontes implements Comparator<Integer> {
	private Usuario usuario;
	public ComparaFontes(Usuario usuario){
		this.usuario = usuario;
	}


	@Override
	public int compare(Integer o1, Integer o2) {
		DadosDoSistema dados  = DadosDoSistema.getInstance();
		Usuario usuario1 = dados.usuarioPorId( o1);
		Usuario usuario2 = dados.usuarioPorId( o2);
		int retorno = 0;
		int num1 = usuario.getNumFavoritosEmComum( usuario1) + usuario.getNumFontesEmComum( usuario1);
		int num2 = usuario.getNumFavoritosEmComum( usuario2) + usuario.getNumFontesEmComum( usuario2);
		retorno = num1 - num2;
		if  (retorno  == 0){
			retorno = usuario.getNumFavoritosEmComum( usuario1) - usuario.getNumFavoritosEmComum( usuario2);
		}
		if (retorno == 0){
			retorno = -1 * usuario1.compareTo(usuario2);
		}
		return -1*retorno;
	}

}
