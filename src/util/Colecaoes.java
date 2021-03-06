package util;

import gerenciadorDeDados.DadosDoSistema;

import java.util.Collection;

public class Colecaoes {

	public static String ColecaoParaString(Collection<Integer> coletion){
		if(coletion.size() == 0) return "{}";
		String retorno = "";
		for (Integer numero : coletion) {
			retorno = retorno + numero + ",";
		}
		return "{" +retorno.substring(0, retorno.length() - 1) + "}";
	}
	
	public static String ColecaoParaStringReverso(Collection<Integer> coletion){
		if(coletion.size() == 0) return "{}";
		String retorno = "";
		for (Integer numero : coletion) {
			retorno = numero + "," + retorno ;
		}
		return "{" +retorno.substring(0, retorno.length() - 1) + "}";
	}
	
	public static void copiar(Collection<Integer> destino, Collection<Integer> origem){
		for (Integer inteiro : origem) {
			destino.add(inteiro);
		}
	}
	
	
	public static String ColecaoParaStringNome(Collection<Integer> coletion){
		if(coletion.size() == 0) return "{}";
		String retorno = "";
		DadosDoSistema  dados = DadosDoSistema.getInstance();
		
		for (Integer numero : coletion) {
			retorno = retorno + dados.usuarioPorId(numero).getNome() + ",";
		}
		return "{" +retorno.substring(0, retorno.length() - 1) + "}";
	}
}
