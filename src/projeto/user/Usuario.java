package projeto.user;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import excessoes.UsuarioException;
import gerenciadorDeDados.DadosDoSistema;



public class Usuario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private String senha;
	private String nome;
	private String email;
	private List<Integer> perfilMusical;
	private List<Integer> seguindo;
	private List<Integer> seguidores;
	private List<Integer> amigos;
	private List<Integer> solicitacoes;
	

	/**
	 * @param login, senha , nome e email
	 * @throws UsuarioException
	 **/
	public Usuario(String login, String senha, String nome, String email) throws UsuarioException {
		if (login == null || login.equals("")) throw new UsuarioException("Login inválido");
		if (nome == null || nome.equals("")) throw new UsuarioException("Nome inválido");
		if (email == null || email.equals("")) throw new UsuarioException("Email inválido");
		if (senha == null || senha.equals("")) throw new UsuarioException("Senha inválida");
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
		this.seguindo = new ArrayList<Integer>();
		this.seguidores = new ArrayList<Integer>();
		this.amigos = new ArrayList<Integer>();
		this.solicitacoes = new ArrayList<Integer>();
	}

	
	/**
	 * @return String 
	 **/
	public String getLogin() {
		return login;
	}

	
	/**
	 * @param login a ser atualizado
	 * @return void
	 * @throws UsuarioException
	 **/
	public void setLogin(String login) throws UsuarioException {
		if (login == null || login.equals("")) throw new UsuarioException("Login inválido");
		this.login = login;
	}

	
	/**
	 * @param senha a ser atualizada
	 * @return void
	 * @throws UsuarioException
	 * 
	 **/
	public void setSenha(String senha) throws UsuarioException {
		if (senha == null || senha.equals("")) throw new UsuarioException("Senha inválida");
		this.senha = senha;
	}

	
	/**
	 * @return String
	 **/
	public String getNome(){
		return nome;
	}

	
	/**
	 * @param nome a ser atualizado
	 * @return void
	 * @throws UsuarioException
	 * 
	 **/
	public void setNome(String nome) throws UsuarioException {
		if (nome == null || nome.equals("")) throw new UsuarioException("Nome inválido");
		this.nome = nome;
	}
	
	
	/**
	 * @param senha a se averiguada
	 * @return boolean 
	 **/
	public boolean testaSenha(String senha) {
		return this.senha.equals(senha);
	}

	
	/**
	 * @return  String 
	 **/
	public String getEmail() {
		return email;
	}

	
	/**
	 * @param email a ser atualizado
	 * @return void
	 * @throws UsuarioException
	 * 
	 **/
	public void setEmail(String email) throws UsuarioException {
		if (email == null || email.equals("")) throw new UsuarioException("Email inválido");
		this.email = email;
	}

	
	/**
	 * @param som id - identificador do som
	 * @return void 
	 **/
	public void postarSom(int somId) {
		if (perfilMusical == null) perfilMusical = new ArrayList<Integer>();
		perfilMusical.add(somId);
	}

	
	/**
	 * @return List<Integer>
	 **/
	public List<Integer> getPerfilMusical() {
		if (perfilMusical == null) return new ArrayList<Integer>();
		return perfilMusical;
	}


	public List<Integer> getFontesDeSons() {
		List<Integer> retorno = new ArrayList<Integer>();
		retorno.addAll(seguindo);
		retorno.addAll(amigos);
		return retorno;

	}
	
	public List<Integer> getListaDeSeguidores() {
		return seguidores;
	}

	public void aceitaSolicitacaoAmizada(Usuario usuario){
		if(this.solicitacoes.contains(usuario.hashCode())){
			this.amigos.add(usuario.hashCode());
			usuario.solicitacaoAceita(this);
			this.solicitacoes.remove(solicitacoes.indexOf(usuario.hashCode()));
		}
	}
	
	public void rejeitaSolicitacao(Usuario usuario){
		if(this.solicitacoes.contains(usuario.hashCode())){
			this.solicitacoes.remove(solicitacoes.indexOf(usuario.hashCode()));
		}
	}
	
	public void solicitacaoAceita(Usuario usuario){
		this.amigos.add(usuario.hashCode());
	}
	
	public void solicitaAmizade(Usuario usuario){
		usuario.recebeSolicitacaoAmizade(this);
	}
	
	public void recebeSolicitacaoAmizade(Usuario usuario){
		this.solicitacoes.add(usuario.hashCode());
	}
	
	public void seguirUsuario(Usuario usuario) {
		this.seguindo.add(usuario.hashCode());
		usuario.seguidores.add(this.hashCode());	
		
	}

	public List<Integer> getVisaoDosSons(){
		List<Integer> retorno = new ArrayList<Integer>();
		DadosDoSistema dados = DadosDoSistema.getInstance();
		for (int i = 0; i < seguindo.size(); i++) {
			Usuario u = dados.usuarioPorId(seguindo.get(i));
			add(retorno, u.getPerfilMusical());
		}
		for (int i = 0; i < amigos.size(); i++) {
			Usuario u = dados.usuarioPorId(amigos.get(i));
			add(retorno, u.getPerfilMusical());
		}
		Collections.reverse(retorno);
		return retorno;
	}
	
	private void add(List<Integer> destino, List<Integer> origem){
		for (int i = 0; i < origem.size(); i++) {
			destino.add(origem.get(i));
		}
	}

	public int getNumeroDeSeguidores() {
		return this.seguidores.size();
	}




}
