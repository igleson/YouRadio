package projeto.user;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import excessoes.UsuarioException;


public class Usuario implements Serializable{
	
	private String login;
	private String senha;
	private String nome;
	private String email;
	private List<Integer> perfilMusical;
	private Collection<Integer> seguindo;
	

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
		this.seguindo = new HashSet<Integer>();
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
		// TODO Auto-generated method stub
		return null;
	}


	public Collection<Integer> getListaDeSeguidores() {
		return this.seguindo;
	}


	public void seguirUsuario(String login) {
		// TODO Auto-generated method stub
		
	}


	public int getNumeroDeSeguidores() {
		return this.seguindo.size();
	}




}
