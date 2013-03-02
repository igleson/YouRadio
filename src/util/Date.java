package util;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Date {

	
	/**
	 * @param data a ser averiguada
	 * @return boolean
	 * 
	 **/
	public static boolean dataEhValida(String data){
		Pattern pattern = Pattern.compile("\\d\\d/\\d\\d/\\d\\d\\d\\d");
		Matcher mt = pattern.matcher(data);
		
		if(!mt.find()) return false;
		
		int dia = Integer.parseInt(data.substring(0, 2));
		int mes = Integer.parseInt(data.substring(3, 5));
		int ano = Integer.parseInt(data.substring(6, 10));
		
		int qtdeDias[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		if(ano % 4 == 0 && (ano % 100 != 0)){
			qtdeDias[1] = 29;
		}else if(ano % 4 == 0 && (ano % 400 == 0)){
			qtdeDias[1] = 29;
		}
		return mes <= 12 && mes > 0 && dia > 0 && dia <= qtdeDias[mes - 1] &&
		(new GregorianCalendar()).compareTo(new GregorianCalendar(ano, mes, dia)) <= 0;
	}
}
