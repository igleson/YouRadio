package projeto.sistem;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import excessoes.CadastroException;
import excessoes.LoginException;
import excessoes.SessaoException;
import excessoes.SomException;
import excessoes.UsuarioException;
import excessoes.sistemaEncerradoException;
import gerenciadorDeDados.DadosDoSistema;

import projeto.perfil.Som;
import projeto.user.Usuario;

public class YouRadio implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private DadosDoSistema dados;
		
	private boolean sistemaEstaAberto = false;
	
	
	/** criar um sistema
	 * @return cria um sistema 
	 **/
	public YouRadio(){
		dados = DadosDoSistema.getInstance();
		sistemaEstaAberto = true;
	}
	
	
	/** zerar um sistema
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
	public int qtdeUsuarios() throws sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		return this.dados.qtdeDeUsuarios();
	}
	
	/**
	 * @param login, senha, nome e email do usuário
	 * @return void
	 * @throws CadastroException, UsuarioException, sistemaEncerradoException
	 * 
	 **/
	public void criarUsuario(String login, String senha, String nome,
			String email) throws CadastroException, UsuarioException, sistemaEncerradoException {
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if (this.dados.contemUsuario(login))
			throw new CadastroException("Já existe um usuário com este login");
		if(this.dados.contemEmail(email)) throw new CadastroException("Já existe um usuário com este email");
		this.dados.adicionaUsuario(login, senha, nome, email);
	}
	

	/**
	 * @param login , senha - do usuário
	 * @return int - identificador da sessão
	 * @throws LoginException, sistemaEncerradoException
	 * 
	 **/
	public int abrirSessao(String login, String senha) throws LoginException, sistemaEncerradoException {
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if (login == null || login.equals(""))
			throw new LoginException("Login inválido");
		if (senha == null || senha.equals(""))
			throw new LoginException("Senha inválida");
		if (!this.dados.contemUsuario(login))
			throw new LoginException("Usuário inexistente");
		if (this.dados.senhaValida(login, senha))
			this.dados.adicionaSessao(login);
		else
			throw new LoginException("Login inválido");
		return login.hashCode();
	}
	
	
	/**
	 * @param sessãoId - identificador da sessão
	 * @return List - perfil musical do usuário
	 * @throws SessaoException, sistemaEncerradoException
	 * 
	 **/
	
	// testar
	public List<Integer> getPerfilMusical(int sessaoId) throws SessaoException, sistemaEncerradoException {
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sessao inexistente");
		String login = this.dados.login(sessaoId);
		return this.dados.usuario(login).getPerfilMusical();
	}
	
	/**
	 * @param sessaoId - identificador da sessão, link - link da música, dataCriação - data da postagem
	 * @return int identificador do som
	 * @throws SomException, SessaoException, sistemaEncerradoException 
	 * 
	 **/
	public int postarSom(int sessaoId, String link, String dataCriacao)
			throws SomException, SessaoException, sistemaEncerradoException {
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sessao inexistente");

		Som som = new Som(link, dataCriacao, this.dados.usuario(sessaoId).hashCode());
		this.dados.adicionaSom(som);
		this.dados.usuario(sessaoId).postarSom(som.hashCode());
		return som.hashCode();
	}
	
	/**
	 * @paramsessaoId - identificador da sessão, link - link da música
	 * @return int identificador do som
	 * @throws SomException, SessaoException, sistemaEncerradoException 
	 **/
	public int postarSom(int sessaoId, String link) throws SomException, SessaoException, sistemaEncerradoException{
		DateFormat formatoDeData = new SimpleDateFormat("dd/MM/yyyy");
		return this.postarSom(sessaoId, link, formatoDeData.format((new GregorianCalendar().getTime())));
	}	
	
	/**
	 * @param login do usuário
	 * @return String - nome do usuário
	 * @throws UsuarioException, sistemaEncerradoException
	 **/
	public String nomeDoUsuario(String login) throws UsuarioException, sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if(login == null || login.equals("")) throw new UsuarioException("Login inválido");
		if (!this.dados.contemUsuario(login)) throw new UsuarioException("Usuário inexistente");
		Usuario usuario = this.dados.usuario(login);
		return usuario.getNome();
	}
	
	
	/**
	 * @param login do usuário
	 * @return String -  email do usuário
	 * @throws UsuarioException, sistemaEncerradoException
	 **/
	public String emailDoUsuario(String login) throws UsuarioException, sistemaEncerradoException {
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if(login == null || login.equals("")) throw new UsuarioException("Login inválido");
		if (!this.dados.contemUsuario(login)) throw new UsuarioException("Usuário inexistente");
		Usuario usuario = this.dados.usuario(login);
		return usuario.getEmail();
	}
	
	
	/**
	 * @param idSom - identificador do som
	 * @return String - link da música
	 * @throws SomException, sistemaEncerradoException
	 **/
	public String linkDoSom(int idSom) throws SomException, sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		Som som = this.dados.Som(idSom);
		return som.getLink();
	}
	
	
	/**
	 * @param idSom - identificador do som
	 * @return String - data da criação
	 * @throws SomException, sistemaEncerradoException
	 **/
	public String dataDeCriacaoSom(int idSom) throws SomException, sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		Som som = this.dados.Som(idSom);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(som.getDataCriacao().getTime());
	}
	
	
	/**
	 * @param login do usuário
	 * @return void
	 **/
	public void encerrarSessao(String login) {
		this.dados.removeSessao(login);
	}
	
	
	/**
	 * @param idSessão identificador da sessão
	 * @return boolean
	 * @throws sistemaEncerradoException
	 **/
	public boolean sessaoAberta(int idSessao) throws sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
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
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sessão inexistente");
		return Integer.toString(dados.usuario(sessaoId).hashCode());
	}


	public List<Integer> getFontesDeSons(int sessaoId) throws SessaoException {
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sessão inexistente");
		return this.dados.usuario(sessaoId).getFontesDeSons();
	}


	public Collection<Integer> getListaDeSeguidores(int sessaoId) throws SessaoException {
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sessão inexistente");
		return this.dados.usuario(sessaoId).getListaDeSeguidores();
	}


	public Collection<Integer> getListaDeSeguindo(int sessaoId) throws SessaoException {
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sessão inexistente");
		return this.dados.usuario(sessaoId).getListaDeSeguindo();
	}


	public void seguirUsuario(int idSessao, String login) throws SessaoException, LoginException {
		if(!this.dados.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		else if(this.dados.usuario(idSessao).getLogin().equals(login)) throw new LoginException("Login inválido");
		else if(!this.dados.contemLogin(login)) throw new LoginException("Login inexistente");
		this.dados.usuario(idSessao).seguirUsuario(this.dados.usuario(login));
	}


	public int getNumeroDeSeguidores(int idSessao) throws SessaoException {
		if(!this.dados.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		return this.dados.usuario(idSessao).getNumeroDeSeguidores();
	}

	//testar
	public Usuario usuario(int idSessao) {
		return dados.usuario(idSessao);
	}

	//testar
	public Usuario usuario(String login) throws SessaoException {
		if(login == null || login.equals("")) throw new SessaoException("Sessão inválida");
		return dados.usuario(login);
	}
	

	public List<Som> getVisaoDosSons(int idSessao) throws SessaoException, SomException{
		if(!this.dados.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		return this.dados.usuario(idSessao).getVisaoDosSons();
	}
	
	
	public void favoritarSom(int sessaoId, int idSom) throws SessaoException, SomException{
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sessão inexistente");
		this.dados.usuario(sessaoId).favoritarSom(idSom);		
	}
	
	
	public List<Integer> getSonsFavoritos(int sessaoId) throws SessaoException, sistemaEncerradoException {
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sessão inexistente");
		return this.dados.usuario(sessaoId).getSonsFavoritos();
	}
	
	//testar
	public List<Integer> getFeedExtra(int idSessao) throws SessaoException {
		if(!this.dados.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		return this.dados.usuario(idSessao).getFeedExtra();
	}
	
	//testar
	public void setMainFeedRule(int idSessao, OrdenacoesFeedPrincipal ordem) throws SessaoException {
		if(!dados.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		Usuario usuario = dados.usuario(idSessao);
		usuario.setMainFeedRule(ordem);
	}	

	//testar
	public boolean contemSessao(String sessaoId) {
		return this.dados.contemSessao(Integer.parseInt(sessaoId));
	}
	
	//testar
	public Som Som(int idSom) throws SomException{
		return dados.Som(idSom);
	}
	
	//testar
	public List<Integer> getMainFeed(int idSessao) throws SessaoException, SomException {
		if(!dados.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		Usuario usuario = dados.usuario(idSessao);
		return usuario.getMainFeed();
	}


	public int getNumFavoritosEmComum(int idSessao, int idUsuario) throws SessaoException {
		if(!dados.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		Usuario usuario1 = dados.usuario(idSessao);
		
		return usuario1.getNumFavoritosEmComum(idUsuario);
		
	}
	
	public int getNumFontesEmComum(int idSessao, int idUsuario) throws SessaoException {
		if(!dados.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		Usuario usuario1 = dados.usuario(idSessao);
		return usuario1.getNumFontesEmComum(idUsuario);
		
	}


	public boolean contemUsuario(int idUsuario) {
		Usuario usuario = this.dados.usuarioPorId(idUsuario);
		return this.dados.contemUsuario(usuario.getLogin());
	}


	public List<Integer> getFontesDeSonsRecomendadas(int idSessao) throws SomException {
		List<Integer> retorno = new ArrayList<Integer>();
		Usuario usuario = dados.usuario(idSessao);
		//Map<Integer, Integer> usuarios = new HashMap<Integer, Integer>();
		retorno.add(usuario.getFontesDeSons().get(0));
		
		for (int i = 1; i < usuario.getFontesDeSons().size(); i++) {
			
			int num1 = usuario.getNumFavoritosEmComum(retorno.get(0))+usuario.getNumFontesEmComum(retorno.get(0));
			int num2 =usuario.getNumFavoritosEmComum(i)+usuario.getNumFontesEmComum(i);
			
			/**if(num2 > num1) retorno.add(0,i);
			else if(num2 < num1) retorno.add(i);
			else{
				
				int favoritei1 = usuario.qntsSonsFavoritei(retorno.get(0));
				int favoritei2 = usuario.qntsSonsFavoritei(i);
				
				if(favoritei2 > favoritei1) retorno.add(0,i);
				else if(favoritei2 < favoritei1) retorno.add(i);
				else{
					DadosDoSistema dados = DadosDoSistema.getInstance();
					Usuario usuarioInd0 = dados.usuarioPorId(retorno.get(0));
					if(usuarioInd0.compareTo(i)==-1) retorno.add(0, i);
					else retorno.add(i);
				}
			//}
			//Integer nums = usuario.getNumFavoritosEmComum(idUsuario) + usuario.getNumFontesEmComum(idUsuario);
			**///usuarios.put(idUsuario, nums);
		}
		//TODO
		//criterios de desempate e preencher a lista de retorno;
		return retorno;
	}
	
	
}
