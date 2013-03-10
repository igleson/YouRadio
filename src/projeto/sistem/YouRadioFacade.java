package projeto.sistem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import projeto.perfil.Som;
import util.Colecaoes;
import util.Strings;
import excessoes.AtributoException;
import excessoes.CadastroException;
import excessoes.LoginException;
import excessoes.SessaoException;
import excessoes.SomException;
import excessoes.UsuarioException;
import excessoes.sistemaEncerradoException;

public class YouRadioFacade {

	private YouRadio sistema;

	public YouRadioFacade() {
		sistema = new YouRadio();
	}

	public void zerarSistema() {
		sistema.zerarSistema();
	}

	public void criarUsuario(String login, String senha, String nome,
			String email) throws UsuarioException, CadastroException,
			sistemaEncerradoException {
		sistema.criarUsuario(login, senha, nome, email);
	}

	public String getIDUsuario(int sessaoId) throws SessaoException {
		return sistema.getIdUsuario(sessaoId);
	}

	public int abrirSessao(String login, String senha) throws LoginException,
			sistemaEncerradoException {
		return sistema.abrirSessao(login, senha);
	}

	public String getPerfilMusical(int sessaoId) throws SessaoException,
			sistemaEncerradoException {
		List<Integer> listSonsIds = sistema.getPerfilMusical(sessaoId);
		return Colecaoes.ColecaoParaStringReverso(listSonsIds);
	}

	public int postarSom(int sessaoId, String link, String dataCriacao)
			throws SomException, SessaoException, sistemaEncerradoException {
		return sistema.postarSom(sessaoId, link, dataCriacao);

	}

	public String getAtributoUsuario(String login, String atributo)
			throws UsuarioException, AtributoException,
			sistemaEncerradoException {
		if (atributo == null || atributo.equals(""))
			throw new AtributoException("Atributo inválido");
		else if (atributo.equals("nome"))
			return sistema.nomeDoUsuario(login);
		else if (atributo.equals("email"))
			return sistema.emailDoUsuario(login);
		else
			throw new AtributoException("Atributo inexistente");
	}

	public String getAtributoSom(String idSom, String atributo)
			throws SomException, AtributoException, NumberFormatException,
			sistemaEncerradoException {
		if (idSom == null || idSom.equals(""))
			throw new SomException("Som inválido");
		else if (atributo == null || atributo.equals(""))
			throw new AtributoException("Atributo inválido");
		else if (!atributo.equals("link") && !atributo.endsWith("dataCriacao"))
			throw new AtributoException("Atributo inexistente");
		else if (idSom == null || idSom.equals(""))
			throw new SomException("Som inválido");
		else if (atributo.equals("link"))
			return sistema.linkDoSom(Integer.parseInt(idSom));
		else
			return sistema.dataDeCriacaoSom(Integer.parseInt(idSom));

	}

	public int getNumeroDeSeguidores(String idSessao) throws SessaoException {
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
		return sistema.getNumeroDeSeguidores(Integer.parseInt(idSessao));
	}

	public String getFontesDeSons(String sessaoId) throws SessaoException {
		if (sessaoId == null || sessaoId.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(sessaoId))
			throw new SessaoException("Sessão inexistente");
		if (!Strings.ehUmNumero(sessaoId))
			throw new SessaoException("Sessão inexistente");
		List<Integer> listSonsIds = sistema.getFontesDeSons(Integer
				.parseInt(sessaoId));
		return Colecaoes.ColecaoParaString(listSonsIds);
	}

	public String getListaDeSeguidores(String sessaoId) throws SessaoException {
		if (sessaoId == null || sessaoId.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(sessaoId))
			throw new SessaoException("Sessão inexistente");
		Collection<Integer> listSonsIds = sistema.getListaDeSeguidores(Integer
				.parseInt(sessaoId));
		return Colecaoes.ColecaoParaStringReverso(listSonsIds);
	}

	public void seguirUsuario(String idSessao, String login)
			throws SessaoException, LoginException {
		if (login == null || login.equals(""))
			throw new LoginException("Login inválido");
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
		sistema.seguirUsuario(Integer.parseInt(idSessao), login);
	}

	public void encerrarSessao(String login) throws LoginException {
		if (login == null || login.equals(""))
			throw new LoginException("Login inválido");
		sistema.encerrarSessao(login);
	}

	public String getVisaoDosSons(String sessaoId) throws SessaoException,
			NumberFormatException, SomException {
		if (sessaoId == null || sessaoId.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(sessaoId))
			throw new SessaoException("Sessão inexistente");
		List<Integer> listSonsIds = new ArrayList<Integer>();
		for (Som sons : sistema.getVisaoDosSons(Integer.parseInt(sessaoId))) {
			listSonsIds.add(sons.hashCode());
		}
		return Colecaoes.ColecaoParaString(listSonsIds);
	}

	public void favoritarSom(String sessaoId, String idSom)
			throws SessaoException, SomException {
		if (sessaoId == null || sessaoId.equals(""))
			throw new SessaoException("Sessão inválida");
		if (idSom == null || idSom.equals("")) {
			System.out.println("aqui");
			System.out.println(idSom);
			throw new SomException("Som inválido");
		}
		if (!Strings.ehUmNumero(sessaoId))
			throw new SessaoException("Sessão inexistente");
		if (!Strings.ehUmNumero(idSom))
			throw new SomException("Som inválido");
		sistema.favoritarSom(Integer.parseInt(sessaoId),
				Integer.parseInt(idSom));

	}

	public String getSonsFavoritos(String sessaoId) throws SessaoException,
			sistemaEncerradoException {
		if (sessaoId == null || sessaoId.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(sessaoId))
			throw new SessaoException("Sessão inexistente");
		List<Integer> listSonsIds = sistema.getSonsFavoritos(Integer
				.parseInt(sessaoId));

		return Colecaoes.ColecaoParaStringReverso(listSonsIds);
	}

	public String getFeedExtra(String sessaoId) throws SessaoException,
			sistemaEncerradoException {
		if (sessaoId == null || sessaoId.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(sessaoId))
			throw new SessaoException("Sessão inexistente");
		List<Integer> listSonsIds = sistema.getFeedExtra(Integer
				.parseInt(sessaoId));

		return Colecaoes.ColecaoParaStringReverso(listSonsIds);
	}

	public void encerrarSistema() {
		sistema.encerrarSistema();
	}

}