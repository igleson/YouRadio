package projeto.sistem;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import excessoes.CadastroException;
import excessoes.ListaException;
import excessoes.LoginException;
import excessoes.SessaoException;
import excessoes.SomException;
import excessoes.UsuarioException;
import excessoes.sistemaEncerradoException;
import gerenciadorDeDados.DadosDoSistema;

import projeto.comparator.ComparaFontes;
import projeto.perfil.Som;
import projeto.user.Usuario;
import sessao.Sessao;
import sessao.SessaoNormal;
import util.Colecaoes;

public class YouRadio implements Serializable {

	private static final long serialVersionUID = 1L;

	private DadosDoSistema dados;

	private boolean sistemaEstaAberto = false;

	/**
	 * criar um sistema
	 * 
	 * @return cria um sistema
	 **/
	public YouRadio() {
		dados = DadosDoSistema.getInstance();
		sistemaEstaAberto = true;
	}

	/**
	 * zerar um sistema
	 * 
	 * @return void
	 **/
	public void zerarSistema() {
		dados = DadosDoSistema.getInstance();
		dados.zeraSistema();
		sistemaEstaAberto = true;
	}

	/**
	 * @return int - quantidade de usuários
	 * @throws sistemaEncerradoException
	 **/
	public int qtdeUsuarios() throws sistemaEncerradoException {
		if (!sistemaEstaAberto)
			throw new sistemaEncerradoException("sistema encerrado");
		return this.dados.qtdeDeUsuarios();
	}

	/**
	 * @param login
	 *            , senha, nome e email do usuário
	 * @return void
	 * @throws CadastroException
	 *             , UsuarioException, sistemaEncerradoException
	 * 
	 **/
	public void criarUsuario(String login, String senha, String nome,
			String email) throws CadastroException, UsuarioException,
			sistemaEncerradoException {
		if (!sistemaEstaAberto)
			throw new sistemaEncerradoException("sistema encerrado");
		if (this.dados.contemUsuario(login))
			throw new CadastroException("Já existe um usuário com este login");
		if (this.dados.contemEmail(email))
			throw new CadastroException("Já existe um usuário com este email");
		this.dados.adicionaUsuario(login, senha, nome, email);
	}

	/**
	 * @param login
	 *            , senha - do usuário
	 * @return int - identificador da sessão
	 * @throws LoginException
	 *             , sistemaEncerradoException
	 * 
	 **/
	public int abrirSessao(String login, String senha) throws LoginException,
			sistemaEncerradoException {
		if (!sistemaEstaAberto)
			throw new sistemaEncerradoException("sistema encerrado");
		Sessao sessao = new SessaoNormal();
		sessao.abrirSessao(login, senha);
		return sessao.hashCode();
	}

	/**
	 * @param sessãoId
	 *            - identificador da sessão
	 * @return List - perfil musical do usuário
	 * @throws SessaoException
	 *             , sistemaEncerradoException
	 * 
	 **/

	// testar
	public List<Integer> getPerfilMusical(int sessaoId) throws SessaoException,
			sistemaEncerradoException {
		if (!sistemaEstaAberto)
			throw new sistemaEncerradoException("sistema encerrado");
		if (!this.dados.contemSessao(sessaoId))
			throw new SessaoException("Sessao inexistente");
		String login = this.dados.login(sessaoId);
		return this.dados.usuario(login).getPerfilMusical();
	}

	/**
	 * @param sessaoId
	 *            - identificador da sessão, link - link da música, dataCriação
	 *            - data da postagem
	 * @return int identificador do som
	 * @throws SomException
	 *             , SessaoException, sistemaEncerradoException
	 * 
	 **/
	public int postarSom(int sessaoId, String link, String dataCriacao)
			throws SomException, SessaoException, sistemaEncerradoException {
		if (!sistemaEstaAberto)
			throw new sistemaEncerradoException("sistema encerrado");
		if (!this.dados.contemSessao(sessaoId))
			throw new SessaoException("Sessao inexistente");

		Som som = new Som(link, dataCriacao, this.dados.usuario(sessaoId).hashCode());
		this.dados.adicionaSom(som);
		this.dados.usuario(sessaoId).postarSom(som.hashCode());
		return som.hashCode();
	}

	/**
	 * @paramsessaoId - identificador da sessão, link - link da música
	 * @return int identificador do som
	 * @throws SomException
	 *             , SessaoException, sistemaEncerradoException
	 **/
	public int postarSom(int sessaoId, String link) throws SomException,
			SessaoException, sistemaEncerradoException {
		DateFormat formatoDeData = new SimpleDateFormat("dd/MM/yyyy");
		return this.postarSom(sessaoId, link,
				formatoDeData.format((new GregorianCalendar().getTime())));
	}

	/**
	 * @param login
	 *            do usuário
	 * @return String - nome do usuário
	 * @throws UsuarioException
	 *             , sistemaEncerradoException
	 **/
	public String nomeDoUsuario(String login) throws UsuarioException,
			sistemaEncerradoException {
		if (!sistemaEstaAberto)
			throw new sistemaEncerradoException("sistema encerrado");
		if (login == null || login.equals(""))
			throw new UsuarioException("Login inválido");
		if (!this.dados.contemUsuario(login))
			throw new UsuarioException("Usuário inexistente");
		Usuario usuario = this.dados.usuario(login);
		return usuario.getNome();
	}

	/**
	 * @param login
	 *            do usuário
	 * @return String - email do usuário
	 * @throws UsuarioException
	 *             , sistemaEncerradoException
	 **/
	public String emailDoUsuario(String login) throws UsuarioException,
			sistemaEncerradoException {
		if (!sistemaEstaAberto)
			throw new sistemaEncerradoException("sistema encerrado");
		if (login == null || login.equals(""))
			throw new UsuarioException("Login inválido");
		if (!this.dados.contemUsuario(login))
			throw new UsuarioException("Usuário inexistente");
		Usuario usuario = this.dados.usuario(login);
		return usuario.getEmail();
	}

	/**
	 * @param idSom
	 *            - identificador do som
	 * @return String - link da música
	 * @throws SomException
	 *             , sistemaEncerradoException
	 **/
	public String linkDoSom(int idSom) throws SomException,
			sistemaEncerradoException {
		if (!sistemaEstaAberto)
			throw new sistemaEncerradoException("sistema encerrado");
		Som som = this.dados.Som(idSom);
		return som.getLink();
	}

	/**
	 * @param idSom
	 *            - identificador do som
	 * @return String - data da criação
	 * @throws SomException
	 *             , sistemaEncerradoException
	 **/
	public String dataDeCriacaoSom(int idSom) throws SomException,
			sistemaEncerradoException {
		if (!sistemaEstaAberto)
			throw new sistemaEncerradoException("sistema encerrado");
		Som som = this.dados.Som(idSom);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(som.getDataCriacao().getTime());
	}

	/**
	 * @param login
	 *            do usuário
	 * @return void
	 **/
	public void encerrarSessao(String login) {
		this.dados.removeSessao(login);
	}

	/**
	 * @param idSessão
	 *            identificador da sessão
	 * @return boolean
	 * @throws sistemaEncerradoException
	 **/
	public boolean sessaoAberta(int idSessao) throws sistemaEncerradoException {
		if (!sistemaEstaAberto)
			throw new sistemaEncerradoException("sistema encerrado");
		return this.dados.sessaoExiste(idSessao);
	}

	/**
	 * @return void
	 **/
	public void encerrarSistema() {
		sistemaEstaAberto = false;
		this.dados = null;
	}

	public String getIdUsuario(int sessaoId) throws SessaoException {
		if (!this.dados.contemSessao(sessaoId))
			throw new SessaoException("Sessão inexistente");
		return Integer.toString(dados.usuario(sessaoId).hashCode());
	}

	public List<Integer> getFontesDeSons(int sessaoId) throws SessaoException {
		if (!this.dados.contemSessao(sessaoId))
			throw new SessaoException("Sessão inexistente");
		return this.dados.usuario(sessaoId).getFontesDeSons();
	}

	public Collection<Integer> getListaDeSeguidores(int sessaoId)
			throws SessaoException {
		if (!this.dados.contemSessao(sessaoId))
			throw new SessaoException("Sessão inexistente");
		return this.dados.usuario(sessaoId).getListaDeSeguidores();
	}

	public Collection<Integer> getListaDeSeguindo(int sessaoId)
			throws SessaoException {
		if (!this.dados.contemSessao(sessaoId))
			throw new SessaoException("Sessão inexistente");
		return this.dados.usuario(sessaoId).getListaDeSeguindo();
	}

	public void seguirUsuario(int idSessao, String login)
			throws SessaoException, LoginException {
		if (!this.dados.contemSessao(idSessao))
			throw new SessaoException("Sessão inexistente");
		else if (this.dados.usuario(idSessao).getLogin().equals(login))
			throw new LoginException("Login inválido");
		else if (!this.dados.contemLogin(login))
			throw new LoginException("Login inexistente");
		this.dados.usuario(idSessao).seguirUsuario(this.dados.usuario(login));
	}

	public int getNumeroDeSeguidores(int idSessao) throws SessaoException {
		if (!this.dados.contemSessao(idSessao))
			throw new SessaoException("Sessão inexistente");
		return this.dados.usuario(idSessao).getNumeroDeSeguidores();
	}

	public Usuario usuario(int idSessao) {
		return dados.usuario(idSessao);
	}

	public Usuario usuario(String login) throws SessaoException {
		if (login == null || login.equals(""))
			throw new SessaoException("Sessão inválida");
		return dados.usuario(login);
	}

	public List<Som> getVisaoDosSons(int idSessao) throws SessaoException,
			SomException {
		if (!this.dados.contemSessao(idSessao))
			throw new SessaoException("Sessão inexistente");
		return this.dados.usuario(idSessao).getVisaoDosSons();
	}

	public void favoritarSom(int sessaoId, int idSom) throws SessaoException,
			SomException {
		if (!this.dados.contemSessao(sessaoId))
			throw new SessaoException("Sessão inexistente");
		this.dados.usuario(sessaoId).favoritarSom(idSom);
	}

	public List<Integer> getSonsFavoritos(int sessaoId) throws SessaoException,
			sistemaEncerradoException {
		if (!this.dados.contemSessao(sessaoId))
			throw new SessaoException("Sessão inexistente");
		return this.dados.usuario(sessaoId).getSonsFavoritos();
	}

	public List<Integer> getFeedExtra(int idSessao) throws SessaoException {
		if (!this.dados.contemSessao(idSessao))
			throw new SessaoException("Sessão inexistente");
		return this.dados.usuario(idSessao).getFeedExtra();
	}

	public void setMainFeedRule(int idSessao, OrdenacoesFeedPrincipal ordem)
			throws SessaoException {
		if (!dados.contemSessao(idSessao))
			throw new SessaoException("Sessão inexistente");
		Usuario usuario = dados.usuario(idSessao);
		usuario.setMainFeedRule(ordem);
	}

	public boolean contemSessao(String sessaoId) {
		return this.dados.contemSessao(Integer.parseInt(sessaoId));
	}


	public Som Som(int idSom) throws SomException {
		return dados.Som(idSom);
	}

	public List<Integer> getMainFeed(int idSessao) throws SessaoException,
			SomException {
		if (!dados.contemSessao(idSessao))
			throw new SessaoException("Sessão inexistente");
		Usuario usuario = dados.usuario(idSessao);
		return usuario.getMainFeed();
	}

	public int getNumFavoritosEmComum(int idSessao, int idUsuario)
			throws SessaoException {
		if (!dados.contemSessao(idSessao))
			throw new SessaoException("Sessão inexistente");
		Usuario usuario1 = dados.usuario(idSessao);
		return usuario1.getNumFavoritosEmComum(idUsuario);
	}

	public int getNumFontesEmComum(int idSessao, int idUsuario)
			throws SessaoException {
		if (!dados.contemSessao(idSessao))
			throw new SessaoException("Sessão inexistente");
		Usuario usuario1 = dados.usuario(idSessao);
		return usuario1.getNumFontesEmComum(idUsuario);
	}

	public boolean contemUsuario(int idUsuario) {
		Usuario usuario = this.dados.usuarioPorId(idUsuario);
		return this.dados.contemUsuario(usuario.getLogin());
	}

	public List<Integer> getFontesDeSonsRecomendadas(int idSessao)
			throws SomException {
		Usuario usuario = dados.usuario(idSessao);
		List<Integer> retorno = getPossiveisAmigosFontesEmComum(usuario);
		retorno.addAll(getPossiveisAmigosFavoritosEmComum(usuario));
		Collections.sort(retorno,new ComparaFontes(dados.usuario(idSessao)));
		if (retorno.isEmpty()){
			for (Integer idUsuario : quemTeveMaisSonsFavoritados()) {
				if(!usuario.getListaDeSeguindo().contains(idUsuario))
					if (dados.usuarioPorId(idUsuario).getNumeroDeSeguidores()!= 0){
					retorno.add(idUsuario);
					}
			}
		}
		return retorno;
	}

	private List<Integer> getPossiveisAmigosFavoritosEmComum(Usuario usuario)throws SomException {
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
	
	private List<Integer> getPossiveisAmigosFontesEmComum(Usuario usuario) {
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
	
	public void adicionarUsuario(Integer idSessao, int idLista, int idUsuario) throws ListaException {
		if(idSessao == null) throw new ListaException("Nome inválido");
		dados.usuario(idSessao).adicionarUsuario(idLista, dados.usuarioPorId(idUsuario));
	}
	
	public List<Integer> getSonsEmLista(int idSessao, Integer idLista) {
		return dados.usuario(idSessao).getSonsEmLista(dados.usuario(idSessao).getListasPorId(idLista));
	}
	
	public int criarLista(String nomeDaLista, Integer idSessao) throws ListaException {
		if(nomeDaLista==null || nomeDaLista.equals("")) throw new ListaException("Nome inválido");
		return dados.usuario(idSessao).criarLista(nomeDaLista);
	}
	
	private List<Integer> quemTeveMaisSonsFavoritados() throws SomException{
		List<Integer> retorno = new ArrayList<Integer>();
		Set<Integer> sons = dados.Sons();
		for (Integer idSom : sons) {
			Som som = dados.Som(idSom);
			if(som.getQtdeFavoritados()>0) {
				if(!retorno.contains(som.getIdDono())) retorno.add(som.getIdDono());
			}
		}
		return retorno;
	}
	
}
