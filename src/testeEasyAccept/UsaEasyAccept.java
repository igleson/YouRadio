package testeEasyAccept;



import java.util.ArrayList;
import java.util.List;

import projeto.sistem.YouRadioFacade;

import easyaccept.EasyAcceptFacade;

public class UsaEasyAccept {

	public static void main(String[] args) throws Exception {

		List<String> files = new ArrayList<String>();

		files.add("src\\testeEasyAccept\\US01.txt");
		files.add("src\\testeEasyAccept\\US02.txt");
		files.add("src\\testeEasyAccept\\US03.txt");
		files.add("src\\testeEasyAccept\\US04.txt");
		files.add("src\\testeEasyAccept\\US05.txt");
		files.add("src\\testeEasyAccept\\US07.txt");
		YouRadioFacade facadeSistema = new YouRadioFacade();
		EasyAcceptFacade eaFacade = new EasyAcceptFacade(facadeSistema,
				files);
		eaFacade.executeTests();
		System.out.println(eaFacade.getCompleteResults());
	}
}