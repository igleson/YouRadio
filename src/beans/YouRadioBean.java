package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import excessoes.LoginException;
import excessoes.SessaoException;
import excessoes.sistemaEncerradoException;

import projeto.sistem.YouRadio;

@ManagedBean
@SessionScoped
public class YouRadioBean {
	private String login;
	private String senha;
	private YouRadio sistema = new YouRadio();
	private int idSessao;
	private UsuarioLogadoBean usuarioLogado;
	
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
	
	public String logar(){
		try {
			idSessao = sistema.abrirSessao(login, senha);
			usuarioLogado = new UsuarioLogadoBean(login, idSessao);
			return "faces/usuariologado.xhtml";
			
		} catch (LoginException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Falhou", e.getLocalizedMessage()));
		} catch (sistemaEncerradoException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Falhou", e.getLocalizedMessage()));
		} catch (SessaoException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Falhou", e.getLocalizedMessage()));
			e.printStackTrace();
		}
		return null;
	}
	
	public String deslogar(){
		sistema.encerrarSessao(login);
		System.out.println("deslogou");
		return "faces/index.xhtml";
	}
	public UsuarioLogadoBean getUsuarioLogado() {
		return usuarioLogado;
	}
	public void setUsuarioLogado(UsuarioLogadoBean usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	

}
