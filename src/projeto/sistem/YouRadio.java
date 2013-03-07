package projeto.sistem;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;


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
		this.zerarSistema();
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
	 * @return int - quantidade de usu�rios
	 * @throws sistemaEncerradoException 
	 **/
	public int qtdeUsuarios() throws sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		return this.dados.qtdeDeUsuarios();
	}
	
	/**
	 * @param login, senha, nome e email do usu�rio
	 * @return void
	 * @throws CadastroException, UsuarioException, sistemaEncerradoException
	 * 
	 **/
	public void criarUsuario(String login, String senha, String nome,
			String email) throws CadastroException, UsuarioException, sistemaEncerradoException {
		//if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if (this.dados.contemUsuario(login))
			throw new CadastroException("J� existe um usu�rio com este login");
		if(this.dados.contemEmail(email)) throw new CadastroException("J� existe um usu�rio com este email");
		this.dados.adicionaUsuario(login, senha, nome, email);
	}
	

	/**
	 * @param login , senha - do usu�rio
	 * @return int - identificador da sess�o
	 * @throws LoginException, sistemaEncerradoException
	 * 
	 **/
	public int abrirSessao(String login, String senha) throws LoginException, sistemaEncerradoException {
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if (login == null || login.equals(""))
			throw new LoginException("Login inv�lido");
		if (senha == null || senha.equals(""))
			throw new LoginException("Senha inv�lida");
		if (!this.dados.contemUsuario(login))
			throw new LoginException("Usu�rio inexistente");
		if (this.dados.senhaValida(login, senha))
			this.dados.adicionaSessao(login);
		else
			throw new LoginException("Login inv�lido");
		return login.hashCode();
	}
	
	
	/**
	 * @param sess�oId - identificador da sess�o
	 * @return List - perfil musical do usu�rio
	 * @throws SessaoException, sistemaEncerradoException
	 * 
	 **/
	public List<Integer> getPerfilMusical(int sessaoId) throws SessaoException, sistemaEncerradoException {
		//if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sessao inexistente");
		String loginTemp = this.dados.login(sessaoId);
		return this.dados.usuario(loginTemp).getPerfilMusical();
	}
	
	/**
	 * @param sessaoId - identificador da sess�o, link - link da m�sica, dataCria��o - data da postagem
	 * @return int identificador do som
	 * @throws SomException, SessaoException, sistemaEncerradoException 
	 * 
	 **/
	public int postarSom(int sessaoId, String link, String dataCriacao)
			throws SomException, SessaoException, sistemaEncerradoException {
		//if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sessao inexistente");

		Som temp = new Som(link, dataCriacao);
		this.dados.adicionaSom(temp);
		this.dados.usuario(sessaoId).postarSom(temp.hashCode());
		return temp.hashCode();
	}
	
	
	/**
	 * @paramsessaoId - identificador da sess�o, link - link da m�sica
	 * @return int identificador do som
	 * @throws SomException, SessaoException, sistemaEncerradoException 
	 **/
	public int postarSom(int sessaoId, String link) throws SomException, SessaoException, sistemaEncerradoException{
		DateFormat formatoDeData = new SimpleDateFormat("dd/MM/yyyy");
		return this.postarSom(sessaoId, link, formatoDeData.format((new GregorianCalendar().getTime())));
	}	
	
	/**
	 * @param login do usu�rio
	 * @return String - nome do usu�rio
	 * @throws UsuarioException, sistemaEncerradoException
	 **/
	public String nomeDoUsuario(String login) throws UsuarioException, sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if(login == null || login.equals("")) throw new UsuarioException("Login inv�lido");
		if (!this.dados.contemUsuario(login)) throw new UsuarioException("Usu�rio inexistente");
		Usuario temp = this.dados.usuario(login);
		return temp.getNome();
	}
	
	
	/**
	 * @param login do usu�rio
	 * @return String -  email do usu�rio
	 * @throws UsuarioException, sistemaEncerradoException
	 **/
	public String emailDoUsuario(String login) throws UsuarioException, sistemaEncerradoException {
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if(login == null || login.equals("")) throw new UsuarioException("Login inv�lido");
		if (!this.dados.contemUsuario(login)) throw new UsuarioException("Usu�rio inexistente");
		Usuario temp = this.dados.usuario(login);
		return temp.getEmail();
	}
	
	
	/**
	 * @param idSom - identificador do som
	 * @return String - link da m�sica
	 * @throws SomException, sistemaEncerradoException
	 **/
	public String linkDoSom(int idSom) throws SomException, sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		Som temp = this.dados.Som(idSom);
		return temp.getLink();
	}
	
	
	/**
	 * @param idSom - identificador do som
	 * @return String - data da cria��o
	 * @throws SomException, sistemaEncerradoException
	 **/
	public String dataDeCriacaoSom(int idSom) throws SomException, sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		Som temp = this.dados.Som(idSom);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(temp.getDataCriacao().getTime());
	}
	
	
	/**
	 * @param login do usu�rio
	 * @return void
	 **/
	public void encerrarSessao(String login) {
		this.dados.removeSessao(login);
	}
	
	
	/**
	 * @param idSess�o identificador da sess�o
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

	//TESTE FEITO
	public String getIdUsuario(int sessaoId) throws SessaoException {
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sess�o inexistente");
		return Integer.toString(dados.usuario(sessaoId).hashCode());
	}

	//Teste Feito, algumas duvidass
	public List<Integer> getFontesDeSons(int sessaoId) throws SessaoException {
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sess�o inexistente");
		return this.dados.usuario(sessaoId).getFontesDeSons();
	}

	//Teste Feito, algumas duvidas
	public Collection<Integer> getListaDeSeguidores(int sessaoId) throws SessaoException {
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sess�o inexistente");
		return this.dados.usuario(sessaoId).getListaDeSeguidores();
	}


	public void seguirUsuario(int idSessao, String login) throws SessaoException, LoginException {
		if(!this.dados.contemSessao(idSessao)) throw new SessaoException("Sess�o inexistente");
		else if(this.dados.usuario(idSessao).getLogin().equals(login)) throw new LoginException("Login inv�lido");
		else if(!this.dados.contemLogin(login)) throw new LoginException("Login inexistente");
		this.dados.usuario(idSessao).seguirUsuario(this.dados.usuario(login));
	}

	//Teste Feito
	public int getNumeroDeSeguidores(int idSessao) throws SessaoException {
		if(!this.dados.contemSessao(idSessao)) throw new SessaoException("Sess�o inexistente");
		return this.dados.usuario(idSessao).getNumeroDeSeguidores();
	}



	//NOVO METODO
	public Usuario usuario(int idSessao) {
		return dados.usuario(idSessao);
	}

	//NOVO METODO
	public Usuario usuario(String login) throws SessaoException {
		if(login == null || login.equals("")) throw new SessaoException("Sess�o inv�lida");
		return dados.usuario(login);
	}
	
	public List<Som> getVisaoDosSons(int idSessao) throws SessaoException, SomException{
		if(!this.dados.contemSessao(idSessao)) throw new SessaoException("Sess�o inexistente");
		return this.dados.usuario(idSessao).getVisaoDosSons();
	}
	
	public void favoritarSom(int sessaoId, int idSom) throws SessaoException{
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sess�o inexistente");
		this.dados.usuario(sessaoId).favoritarSom(idSom);
		
	}
	public List<Integer> getSonsFavoritos(int sessaoId) throws SessaoException, sistemaEncerradoException {
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sess�o inexistente");
		return this.dados.usuario(sessaoId).getSonsFavoritos();
	}


	public List<Integer> getFeedExtra(int idSessao) throws SessaoException {
		if(!this.dados.contemSessao(idSessao)) throw new SessaoException("Sess�o inexistente");
		return this.dados.usuario(idSessao).getFeedExtra();
	}


	public List<Integer> getMainFeed(int sessaoId) throws SessaoException {
		if(!this.dados.contemSessao(sessaoId)) throw new SessaoException("Sess�o inexistente");
		return this.dados.usuario(sessaoId).getMainFeed();
	}
}
