package teste;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import excessoes.CadastroException;
import excessoes.UsuarioException;
import excessoes.sistemaEncerradoException;

import projeto.sistem.YouRadio;

@ManagedBean
@SessionScoped
public class CadastroBean implements Serializable {

	private YouRadio sistema;

	private static final long serialVersionUID = 1L;

	private String nome, login, email, senha, senhaRepetida;

	public String getSenhaRepetida() {
		return senhaRepetida;
	}

	public void setSenhaRepetida(String senhaRepetida) {
		this.senhaRepetida = senhaRepetida;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	private void iniciaSistema() {
		sistema = new YouRadio();
	}

	public String cadastrar() {
		if(!senha.equals(senhaRepetida)){
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Falhou", "Confirmação da senha inválida"));
			return null;
		}
		if (sistema == null)
			iniciaSistema();
		try {
			sistema.criarUsuario(login, senha, nome, email);
			return "faces/index.xhtml";
		} catch (CadastroException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Falhou", e.getLocalizedMessage()));
			System.out.println(e.getMessage());
		} catch (UsuarioException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Falhou", e.getLocalizedMessage()));
			System.out.println(e.getMessage());
		} catch (sistemaEncerradoException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Falhou", e.getLocalizedMessage()));
			System.out.println(e.getMessage());
		}
		return null;
	}

}
