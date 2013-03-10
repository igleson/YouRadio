package util;

public class Strings {

	public static boolean ehUmNumero(String str){
		try{
			Integer.parseInt(str);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
}
