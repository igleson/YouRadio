package beans;

import excessoes.CadastroException;
import excessoes.UsuarioException;
import excessoes.sistemaEncerradoException;
import projeto.sistem.YouRadio;

public class YouRadioBean {
	private String nome;
	private String email;
	private String login;
	private String senha;
	private YouRadio sistema;
	
	
	
	public YouRadioBean() {
		sistema = new YouRadio();
	}
	
	
	public void cadastrar() throws CadastroException, UsuarioException, sistemaEncerradoException{
		sistema.criarUsuario(login, senha, nome, email);
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	

}
