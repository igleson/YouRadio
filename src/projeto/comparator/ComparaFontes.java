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
		int retorno = 0;
		int num1 = usuario.getNumFavoritosEmComum( o1) + usuario.getNumFontesEmComum( o1);
		int num2 = usuario.getNumFavoritosEmComum( o2) + usuario.getNumFontesEmComum( o2);
		retorno = num1 - num2;
		if  (retorno  == 0){
			retorno = usuario.getNumFavoritosEmComum( o1) - usuario.getNumFavoritosEmComum( o2);
		}
		if (retorno == 0){
			retorno = -1 * usuario1.compareTo(o2);
		}
		return -1*retorno;
	}

}
