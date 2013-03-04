package projeto.sistem;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import projeto.perfil.Som;
import projeto.user.Usuario;

public class YouRadio implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Map<String, Usuario> todosOsUsuarios;
	private Map<Integer, String> todasAsSessoes;
	private Map<Integer, Som> todosOsSons;
	
	private boolean sistemaEstaAberto = false;
	
	
	/** criar um sistema
	 * @return cria um sistema 
	 **/
	public YouRadio(){
		this.zerarSistema();
		sistemaEstaAberto = true;
	}
	
	
	/** zerar um sistema
	 * @return void
	 **/
	public void zerarSistema() {
		this.todosOsUsuarios = new HashMap<String, Usuario>();
		this.todasAsSessoes = new HashMap<Integer, String>();
		this.todosOsSons = new HashMap<Integer, Som>();
	}
	
	
	/**
	 * @return int - quantidade de usu�rios
	 * @throws sistemaEncerradoException 
	 **/
	public int qtdeUsuarios() throws sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		return todosOsUsuarios.size();
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
		if (this.todosOsUsuarios.containsKey(login))
			throw new CadastroException("J� existe um usu�rio com este login");
		for (Usuario usuario : todosOsUsuarios.values()) {
			if (usuario.getEmail().equals(email))
				throw new CadastroException(
						"J� existe um usu�rio com este email");
		}
		this.todosOsUsuarios.put(login, new Usuario(login, senha, nome, email));
	}
	
	

	/**
	 * @param login , senha - do usu�rio
	 * @return int - identificador da sess�o
	 * @throws LoginException, sistemaEncerradoException
	 * 
	 **/
	public int abrirSessao(String login, String senha) throws LoginException, sistemaEncerradoException {
		//if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if (login == null || login.equals(""))
			throw new LoginException("Login inv�lido");
		if (senha == null || senha.equals(""))
			throw new LoginException("Senha inv�lida");
		if (!todosOsUsuarios.containsKey(login))
			throw new LoginException("Usu�rio inexistente");
		if (this.todosOsUsuarios.get(login).testaSenha(senha))
			this.todasAsSessoes.put(login.hashCode(), login);
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
		if(!todasAsSessoes.containsKey(sessaoId)) throw new SessaoException("Sessao inexistente");
		String loginTemp = getLogin(sessaoId);
		return this.todosOsUsuarios.get(loginTemp).getPerfilMusical();
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
		if(!todasAsSessoes.containsKey(sessaoId)) throw new SessaoException("Sessao inexistente");

		Som temp = new Som(link, dataCriacao);
		todosOsSons.put(temp.hashCode(), temp);
		todosOsUsuarios.get(todasAsSessoes.get(sessaoId)).postarSom(
				temp.hashCode());
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
		if (!this.todosOsUsuarios.containsKey(login)) throw new UsuarioException("Usu�rio inexistente");
		Usuario temp = this.todosOsUsuarios.get(login);
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
		if (!this.todosOsUsuarios.containsKey(login)) throw new UsuarioException("Usu�rio inexistente");
		Usuario temp = this.todosOsUsuarios.get(login);
		return temp.getEmail();
	}
	
	
	/**
	 * @param idSom - identificador do som
	 * @return String - link da m�sica
	 * @throws SomException, sistemaEncerradoException
	 **/
	public String linkDoSom(int idSom) throws SomException, sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if (!this.todosOsSons.containsKey(idSom)) throw new SomException("Som inexistente");
		Som temp = this.todosOsSons.get(idSom);
		return temp.getLink();
	}
	
	
	/**
	 * @param idSom - identificador do som
	 * @return String - data da cria��o
	 * @throws SomException, sistemaEncerradoException
	 **/
	public String dataDeCriacaoSom(int idSom) throws SomException, sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if (!this.todosOsSons.containsKey(idSom)) throw new SomException("Som inexistente");
		Som temp = this.todosOsSons.get(idSom);
		return temp.getDataCriacao();
	}
	
	
	/**
	 * @param login do usu�rio
	 * @return void
	 **/
	public void encerrarSessao(String login) {
		this.todasAsSessoes.remove(login.hashCode());
	}
	
	
	/**
	 * @param idSess�o identificador da sess�o
	 * @return boolean
	 * @throws sistemaEncerradoException
	 **/
	public boolean sessaoAberta(int idSessao) throws sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		return todasAsSessoes.containsKey(idSessao);
	}
	
	
	/**
	 * @param idSess�o identificador da sess�o
	 * @return String - sess�o
	 **/
	private String getLogin(int sessaoId) {
		return todasAsSessoes.get(sessaoId);
	}
	
	//NOVO TESTE
	public  String getIdUsuario(int sessaoId){
		if (todosOsUsuarios.containsKey(getLogin(sessaoId))){
			return this.getLogin(sessaoId);
		}
		return null;
		
	}
	
	/**
	 * @return void
	 **/
	public void encerrarSistema() {
		this.todosOsUsuarios = null;
		this.todasAsSessoes = null;
		//linha nova
		this.todosOsSons = null;
		sistemaEstaAberto = false;
	}


}
