package projeto.user;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import projeto.perfil.Som;


import excessoes.SomException;
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
	private List<Integer> sonsFavoritos;
	private List<Integer> feedExtra;
	
	private String regra = "PRIMEIRO OS SONS POSTADOS MAIS RECENTEMENTE PELAS FONTES DE SONS";
	

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
		this.sonsFavoritos = new ArrayList<Integer>();
		this.feedExtra = new ArrayList<Integer>();
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
		return retorno;

	}
	
	public List<Integer> getListaDeSeguidores() {
		return seguidores;
	}
	
	public void seguirUsuario(Usuario usuario) {
		this.seguindo.add(usuario.hashCode());
		usuario.seguidores.add(this.hashCode());	
		
	}

	public List<Som> getVisaoDosSons() throws SomException{
		List<Som> retorno = new ArrayList<Som>();
		DadosDoSistema dados = DadosDoSistema.getInstance();
		for (int i = seguindo.size()-1; i >= 0; i--) {
			Usuario u = dados.usuarioPorId(seguindo.get(i));
			List<Som> temp = new ArrayList<Som>();
			for (Integer id : u.getPerfilMusical()) {
				temp.add(dados.Som(id));
			}
			Collections.sort(temp);
			retorno.addAll(temp);
		}
		return retorno;
	}

	public int getNumeroDeSeguidores() {
		return this.seguidores.size();
	}
	
	public void favoritarSom(int idSom){
		sonsFavoritos.add(idSom);
		DadosDoSistema dados = DadosDoSistema.getInstance();
		for (int usuario : this.seguidores) {
			dados.usuarioPorId(usuario).adicionaAoFeedExtra(idSom);
		}
	}
	
	public List<Integer> getSonsFavoritos(){
		return sonsFavoritos;
	}

	public void adicionaAoFeedExtra(int somId){
		this.feedExtra.add(somId);
	}
	
	public List<Integer> getFeedExtra() {
		return this.feedExtra;
	}

	
	public void setMainFeedRule(String regra){
		this.regra = regra;
	}
	
//Construção
	public List<Som> getMainFeed() throws SomException {
		
		
		if(regra.equals("PRIMEIRO OS SONS POSTADOS MAIS RECENTEMENTE PELAS FONTES DE SONS")){
		
			List<Som> retorno = new ArrayList<Som>();
			DadosDoSistema dados = DadosDoSistema.getInstance();
			for (int i = seguindo.size()-1; i >= 0; i--) {
				Usuario u = dados.usuarioPorId(seguindo.get(i));
				for (Integer id : u.getPerfilMusical()) {
					retorno.add(dados.Som(id));
				}
			}
			return retorno;
		}
		
		else if(regra.equals("PRIMEIRO OS SONS COM MAIS FAVORITOS")){
			List<Som> retorno = new ArrayList<Som>();
			DadosDoSistema dados = DadosDoSistema.getInstance();
			for (int i = seguindo.size()-1; i >= 0; i--) {
				Usuario u = dados.usuarioPorId(seguindo.get(i));
				for (Integer id : u.getSonsFavoritos()) {
					retorno.add(dados.Som(id));
				}
			}
			return retorno;
			
		}else{
			
			return null;
		}
		
	}

}
