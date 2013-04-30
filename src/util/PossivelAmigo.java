package util;

import java.util.ArrayList;
import java.util.List;

import projeto.perfil.Som;
import projeto.user.Usuario;
import excessoes.SomException;
import gerenciadorDeDados.DadosDoSistema;

public class PossivelAmigo {
	private static DadosDoSistema dados = DadosDoSistema.getInstance();
	
	public static List<Integer> getPossiveisAmigosFavoritosEmComum(Usuario usuario)throws SomException {
		List<Integer> retorno = new ArrayList<Integer>();
		List<Integer> favoritos = usuario.getSonsFavoritos();
		for (int i = 0; i < favoritos.size(); i++) {
			Som som = dados.Som(favoritos.get(i));
			for (Integer idPossivelAmigo : som.getQuemFavoritou()) {
				if (!favoritos.contains(idPossivelAmigo)
						&& !usuario.getFontesDeSons().contains(idPossivelAmigo)
						&& !retorno.contains(idPossivelAmigo)
						&& !idPossivelAmigo.equals(usuario.hashCode()))
					retorno.add(idPossivelAmigo);
			}
		}
		return retorno;
	}
	
	public static List<Integer> getPossiveisAmigosFontesEmComum(Usuario usuario) {
		List<Integer> retorno = new ArrayList<Integer>();
		List<Integer> fontes = usuario.getFontesDeSons();
		for (int i = 0; i < fontes.size(); i++) {
			Usuario usuarioLocal = dados.usuarioPorId(fontes.get(i));
			for (Integer idPossivelAmigo : usuarioLocal.getFontesDeSons()) {
				if (!fontes.contains(idPossivelAmigo)
						&& !retorno.contains(idPossivelAmigo)
						&& !idPossivelAmigo.equals(usuario.hashCode()))
					retorno.add(idPossivelAmigo);
			}
		}
		return retorno;
	}

}
