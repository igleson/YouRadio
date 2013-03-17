package projeto.sistem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import projeto.perfil.Som;
import projeto.user.Usuario;
import excessoes.CadastroException;
import excessoes.LoginException;
import excessoes.SessaoException;
import excessoes.SomException;
import excessoes.UsuarioException;
import excessoes.sistemaEncerradoException;
import gerenciadorDeDados.DadosDoSistema;

public class adapterWUISistema {

	YouRadio sistema;
	
	public adapterWUISistema(){
		sistema = new YouRadio();
	}

	public void zerarSistema() {
		sistema.zerarSistema();
	}

	public int qtdeUsuarios() throws sistemaEncerradoException {
		return sistema.qtdeUsuarios();
	}

	public void criarUsuario(String login, String senha, String nome,
			String email) throws CadastroException, UsuarioException,
			sistemaEncerradoException {
		sistema.criarUsuario(login, senha, nome, email);
	}

	public int abrirSessao(String login, String senha) throws LoginException,
			sistemaEncerradoException {
		return sistema.abrirSessao(login, senha);
	}

	public List<Som> getPerfilMusical(int sessaoId) throws SessaoException,
		sistemaEncerradoException, SomException {
		List<Integer> sons = sistema.getPerfilMusical(sessaoId);
		return idsParaSons(sons);
	}

	public int postarSom(int sessaoId, String link, String dataCriacao)
			throws SomException, SessaoException, sistemaEncerradoException {
		return sistema.postarSom(sessaoId, link, dataCriacao);
	}

	public int postarSom(int sessaoId, String link) throws SomException,
			SessaoException, sistemaEncerradoException {
		return sistema.postarSom(sessaoId, link);
	}

	public String nomeDoUsuario(String login) throws UsuarioException,
			sistemaEncerradoException {
		return sistema.nomeDoUsuario(login);
	}

	public String emailDoUsuario(String login) throws UsuarioException,
			sistemaEncerradoException {
		return sistema.emailDoUsuario(login);
	}

	public String linkDoSom(int idSom) throws SomException,
			sistemaEncerradoException {
		return sistema.linkDoSom(idSom);
	}

	public String dataDeCriacaoSom(int idSom) throws SomException,
			sistemaEncerradoException {
		return sistema.dataDeCriacaoSom(idSom);
	}

	public void encerrarSessao(String login) {
		sistema.encerrarSessao(login);
	}

	public boolean sessaoAberta(int idSessao) throws sistemaEncerradoException {
		return sistema.sessaoAberta(idSessao);
	}

	public void encerrarSistema() {
		sistema.encerrarSistema();
	}

	public String getIdUsuario(int sessaoId) throws SessaoException {
		return sistema.getIdUsuario(sessaoId);
	}

	public List<Integer> getFontesDeSons(int sessaoId) throws SessaoException {
		return sistema.getFontesDeSons(sessaoId);
	}

	public Collection<Integer> getListaDeSeguidores(int sessaoId)
			throws SessaoException {
		return sistema.getListaDeSeguidores(sessaoId);
	}

	public Collection<String> getListaDeSeguindo(int sessaoId)
			throws SessaoException {
		Collection<Integer> seguindo = sistema.getListaDeSeguindo(sessaoId);
		Collection<String> retorno = new ArrayList<String>();
		DadosDoSistema dados = DadosDoSistema.getInstance();
		for (Integer id : seguindo) {
			retorno.add(dados.usuarioPorId(id).getNome());
		}
		return retorno;
	}

	public void seguirUsuario(int idSessao, String login)
			throws SessaoException, LoginException {
		sistema.seguirUsuario(idSessao, login);
	}

	public int getNumeroDeSeguidores(int idSessao) throws SessaoException {
		return sistema.getNumeroDeSeguidores(idSessao);
	}

	public Usuario usuario(int idSessao) {
		return sistema.usuario(idSessao);
	}

	public Usuario usuario(String login) throws SessaoException {
		return sistema.usuario(login);
	}

	public List<Som> getVisaoDosSons(int idSessao) throws SessaoException,
			SomException {
		return sistema.getVisaoDosSons(idSessao);
	}

	public void favoritarSom(int sessaoId, int idSom) throws SessaoException,
			SomException {
		sistema.favoritarSom(sessaoId, idSom);
	}

	public List<Integer> getSonsFavoritos(int sessaoId) throws SessaoException,
			sistemaEncerradoException {
		return sistema.getSonsFavoritos(sessaoId);
	}

	public List<Integer> getFeedExtra(int idSessao) throws SessaoException {
		return sistema.getFeedExtra(idSessao);
	}

	public void setMainFeedRule(int idSessao, OrdenacoesFeedPrincipal ordem)
			throws SessaoException {
		sistema.setMainFeedRule(idSessao, ordem);
	}

	public boolean contemSessao(String sessaoId) {
		return sistema.contemSessao(sessaoId);
	}

	public boolean equals(Object obj) {
		return sistema.equals(obj);
	}

	public List<Som> getMainFeed(int idSessao) throws SessaoException,
			SomException {
		return idsParaSons(sistema.getMainFeed(idSessao));
	}
	
	private List<Som> idsParaSons(List<Integer> ids) throws SomException{
		List<Som> retorno = new ArrayList<Som>();
		DadosDoSistema dados = DadosDoSistema.getInstance();
		for (Integer id : ids) {
			retorno.add(dados.Som(id));
		}
		return retorno;
	}
	
	public List<Som> feedExtra(int idSessao) throws SessaoException, SomException{
		return idsParaSons(sistema.getFeedExtra(idSessao));
	}
	
}
