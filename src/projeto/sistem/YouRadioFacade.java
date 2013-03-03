package projeto.sistem;

import java.util.List;
import excessoes.AtributoException;
import excessoes.CadastroException;
import excessoes.LoginException;
import excessoes.SessaoException;
import excessoes.SomException;
import excessoes.UsuarioException;
import excessoes.sistemaEncerradoException;

public class YouRadioFacade {

	private YouRadio sistema;
	
	public YouRadioFacade(){
		sistema = new YouRadio();
	}
	
	public void zerarSistema() {
		sistema.zerarSistema();
	}

	public void criarUsuario(String login, String senha, String nome,
			String email) throws UsuarioException, CadastroException, sistemaEncerradoException {
		sistema.criarUsuario(login, senha, nome, email);
	}

	public int abrirSessao(String login, String senha) throws LoginException, sistemaEncerradoException {
		return sistema.abrirSessao(login, senha);
	}

	public String getPerfilMusical(int sessaoId) throws SessaoException, sistemaEncerradoException {
		List<Integer> listSonsIds = sistema.getPerfilMusical(sessaoId);
		if (listSonsIds.size() == 0)
			return "{}";
		String retorno ="" ;
		for (Integer integer : listSonsIds) {
			retorno = integer + "," + retorno ;

		}
		return "{" +retorno.substring(0, retorno.length() - 1) + "}";

	}

	public int postarSom(int sessaoId, String link, String dataCriacao)
			throws SomException, SessaoException, sistemaEncerradoException {

		return sistema.postarSom(sessaoId, link, dataCriacao);

	}

	public String getAtributoUsuario(String login, String atributo) throws UsuarioException, AtributoException, sistemaEncerradoException {
		if (atributo == null || atributo.equals("")) throw new AtributoException("Atributo inv�lido");
		else if (atributo.equals("nome")) return sistema.nomeDoUsuario(login);
		else if (atributo.equals("email")) return sistema.emailDoUsuario(login);
		else throw new AtributoException("Atributo inexistente");
	}

	public String getAtributoSom(String idSom, String atributo) throws SomException, AtributoException, NumberFormatException, sistemaEncerradoException {
		if(idSom == null || idSom.equals("")) throw new SomException("Som inv�lido");
		else if (atributo == null || atributo.equals("")) throw new AtributoException("Atributo inv�lido");
		else if(!atributo.equals("link") && !atributo.endsWith("dataCriacao")) throw new AtributoException("Atributo inexistente");
		else if(idSom == null || idSom.equals("")) throw new SomException("Som inv�lido");
		else if (atributo.equals("link")) return sistema.linkDoSom(Integer.parseInt(idSom));
		else return sistema.dataDeCriacaoSom(Integer.parseInt(idSom));

	}

	public void encerrarSessao(String login) {
		sistema.encerrarSessao(login);
	}

	public void encerrarSistema() {
		sistema.encerrarSistema();
	}
}