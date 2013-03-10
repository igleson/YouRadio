package projeto.user;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import projeto.perfil.NumeroDeVezesFavoritado;
import projeto.perfil.Som;
import projeto.sistem.OrdenacoesFeedPrincipal;


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
	private OrdenacoesFeedPrincipal ordem;
		

	/**
	 * @param login, senha , nome e email
	 * @throws UsuarioException
	 **/
	public Usuario(String login, String senha, String nome, String email) throws UsuarioException {
		if (login == null || login.equals("")) throw new UsuarioException("Login inv�lido");
		if (nome == null || nome.equals("")) throw new UsuarioException("Nome inv�lido");
		if (email == null || email.equals("")) throw new UsuarioException("Email inv�lido");
		if (senha == null || senha.equals("")) throw new UsuarioException("Senha inv�lida");
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
		this.seguindo = new ArrayList<Integer>();
		this.seguidores = new ArrayList<Integer>();
		this.sonsFavoritos = new ArrayList<Integer>();
		this.feedExtra = new ArrayList<Integer>();
		this.ordem = OrdenacoesFeedPrincipal.MAIS_RECENTES;
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
		if (login == null || login.equals("")) throw new UsuarioException("Login inv�lido");
		this.login = login;
	}

	
	/**
	 * @param senha a ser atualizada
	 * @return void
	 * @throws UsuarioException
	 * 
	 **/
	public void setSenha(String senha) throws UsuarioException {
		if (senha == null || senha.equals("")) throw new UsuarioException("Senha inv�lida");
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
		if (nome == null || nome.equals("")) throw new UsuarioException("Nome inv�lido");
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
		if (email == null || email.equals("")) throw new UsuarioException("Email inv�lido");
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
	
	
	public void addSeguidores(Usuario usuario){
		
		this.seguidores.add(usuario.hashCode());
	}
	
	
	public void seguirUsuario(Usuario usuario) {
		this.seguindo.add(usuario.hashCode());
		usuario.addSeguidores(this);	
		
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
	
	public void favoritarSom(int idSom) throws SomException{
		sonsFavoritos.add(idSom);
		DadosDoSistema dados = DadosDoSistema.getInstance();
		dados.Som(idSom).favoritou();
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

	public void setMainFeedRule(OrdenacoesFeedPrincipal ordem) {
		this.ordem = ordem;
	}
	
	private List<Integer> mainFeed(){
		List<Integer> retorno = new ArrayList<Integer>();
		DadosDoSistema dados = DadosDoSistema.getInstance();
		for (int i = seguindo.size()-1; i >= 0; i--) {
			Usuario u = dados.usuarioPorId(seguindo.get(i));
			retorno.addAll(u.getPerfilMusical());
		}
		return retorno;
	}
	
	private List<Som> idsParaSons(List<Integer> ids) throws SomException{
		List<Som> retorno = new ArrayList<Som>();
		DadosDoSistema dados = DadosDoSistema.getInstance();
		for (Integer id : ids) {
			retorno.add(dados.Som(id));
		}
		return retorno;
	}
	
	private List<Integer> sonsParaIds(List<Som> sons){
		List<Integer> retorno = new ArrayList<Integer>();
		for (Som som : sons) {
			retorno.add(som.hashCode());
		}
		return retorno;
	}
	
	private List<Integer> mainFeedMaisRecentes(){
		List<Integer> retorno = mainFeed();
		Collections.reverse(retorno);
		return retorno;
	}


	public List<Integer> getMainFeed() throws SomException {
		if(ordem.ordinal() == 0) return mainFeedMaisRecentes();
		else if(ordem.ordinal() == 1){
			List<Integer> ids = mainFeed();
			List<Som> sons = idsParaSons(ids);
			Collections.sort(sons, new NumeroDeVezesFavoritado());
			return sonsParaIds(sons);
		}
		else if(ordem.ordinal() == 2){}
		return new ArrayList<Integer>();
	}

}
