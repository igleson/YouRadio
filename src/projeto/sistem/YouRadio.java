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
	 * @return int - quantidade de usuários
	 * @throws sistemaEncerradoException 
	 **/
	public int qtdeUsuarios() throws sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		return todosOsUsuarios.size();
	}
	
	/**
	 * @param login, senha, nome e email do usuário
	 * @return void
	 * @throws CadastroException, UsuarioException, sistemaEncerradoException
	 * 
	 **/
	public void criarUsuario(String login, String senha, String nome,
			String email) throws CadastroException, UsuarioException, sistemaEncerradoException {
		//if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if (this.todosOsUsuarios.containsKey(login))
			throw new CadastroException("Já existe um usuário com este login");
		for (Usuario usuario : todosOsUsuarios.values()) {
			if (usuario.getEmail().equals(email))
				throw new CadastroException(
						"Já existe um usuário com este email");
		}
		this.todosOsUsuarios.put(login, new Usuario(login, senha, nome, email));
	}
	
	

	/**
	 * @param login , senha - do usuário
	 * @return int - identificador da sessão
	 * @throws LoginException, sistemaEncerradoException
	 * 
	 **/
	public int abrirSessao(String login, String senha) throws LoginException, sistemaEncerradoException {
		//if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if (login == null || login.equals(""))
			throw new LoginException("Login inválido");
		if (senha == null || senha.equals(""))
			throw new LoginException("Senha inválida");
		if (!todosOsUsuarios.containsKey(login))
			throw new LoginException("Usuário inexistente");
		if (this.todosOsUsuarios.get(login).testaSenha(senha))
			this.todasAsSessoes.put(login.hashCode(), login);
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
	public List<Integer> getPerfilMusical(int sessaoId) throws SessaoException, sistemaEncerradoException {
		//if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if(!todasAsSessoes.containsKey(sessaoId)) throw new SessaoException("Sessao inexistente");
		String loginTemp = getLogin(sessaoId);
		return this.todosOsUsuarios.get(loginTemp).getPerfilMusical();
	}
	
	/**
	 * @param sessaoId - identificador da sessão, link - link da música, dataCriação - data da postagem
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
		if (!this.todosOsUsuarios.containsKey(login)) throw new UsuarioException("Usuário inexistente");
		Usuario temp = this.todosOsUsuarios.get(login);
		return temp.getNome();
	}
	
	
	/**
	 * @param login do usuário
	 * @return String -  email do usuário
	 * @throws UsuarioException, sistemaEncerradoException
	 **/
	public String emailDoUsuario(String login) throws UsuarioException, sistemaEncerradoException {
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if(login == null || login.equals("")) throw new UsuarioException("Login inválido");
		if (!this.todosOsUsuarios.containsKey(login)) throw new UsuarioException("Usuário inexistente");
		Usuario temp = this.todosOsUsuarios.get(login);
		return temp.getEmail();
	}
	
	
	/**
	 * @param idSom - identificador do som
	 * @return String - link da música
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
	 * @return String - data da criação
	 * @throws SomException, sistemaEncerradoException
	 **/
	public String dataDeCriacaoSom(int idSom) throws SomException, sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		if (!this.todosOsSons.containsKey(idSom)) throw new SomException("Som inexistente");
		Som temp = this.todosOsSons.get(idSom);
		return temp.getDataCriacao();
	}
	
	
	/**
	 * @param login do usuário
	 * @return void
	 **/
	public void encerrarSessao(String login) {
		this.todasAsSessoes.remove(login.hashCode());
	}
	
	
	/**
	 * @param idSessão identificador da sessão
	 * @return boolean
	 * @throws sistemaEncerradoException
	 **/
	public boolean sessaoAberta(int idSessao) throws sistemaEncerradoException{
		if(!sistemaEstaAberto) throw new sistemaEncerradoException("sistema encerrado");
		return todasAsSessoes.containsKey(idSessao);
	}
	
	
	/**
	 * @param idSessão identificador da sessão
	 * @return String - sessão
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
