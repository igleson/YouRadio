package util;

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
	
}
