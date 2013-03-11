package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import excessoes.LoginException;
import excessoes.SessaoException;
import excessoes.SomException;
import excessoes.sistemaEncerradoException;

import projeto.sistem.YouRadio;

@ManagedBean
@SessionScoped
public class YouRadioBean {
	private String login;
	private String senha;
	private String subLogin;
	private YouRadio sistema = new YouRadio();
	private int idSessao;
	private UsuarioLogadoBean usuarioLogado;
	private String postagem;
	private String pesquisa;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.subLogin = login;
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String logar() {
		try {
			idSessao = sistema.abrirSessao(subLogin, senha);
			usuarioLogado = new UsuarioLogadoBean(subLogin, idSessao);
			return "faces/usuariologado.xhtml";

		} catch (LoginException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", e.getLocalizedMessage()));
		} catch (sistemaEncerradoException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", e.getLocalizedMessage()));
		} catch (SessaoException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", e.getLocalizedMessage()));
			e.printStackTrace();
		} finally {
			login = null;
		}
		return null;
	}

	public String deslogar() {
		sistema.encerrarSessao(subLogin);
		usuarioLogado = null;
		return "faces/index.xhtml";
	}

	public UsuarioLogadoBean getUsuarioLogado() {
		return usuarioLogado;
	}
	
	
	public void ordenarRecentes(){}
	public void ordenarFavoritos(){}
	public void ordenarPopular(){}

	public String postar() {
		if (postagem.equals(""))return null;
		try {
			sistema.postarSom(idSessao, postagem);
		} catch (SomException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Falhou",  e.getLocalizedMessage()));

		} catch (SessaoException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Falhou",  e.getLocalizedMessage()));

		} catch (sistemaEncerradoException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Falhou", e.getLocalizedMessage()));

		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Ok", "postado com sucesso"));
		apagar();
		return "";

	}

	public void apagar() {
		this.postagem = null;

	}

	public void setUsuarioLogado(UsuarioLogadoBean usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getPostagem() {
		return postagem;
	}

	public void setPostagem(String postagem) {
		this.postagem = postagem;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
	public void pesquisar() {
		if (!pesquisa.equals("")) {

			try {
				sistema.seguirUsuario(idSessao, pesquisa);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Ok", ("Agora você está seguindo "+ pesquisa)));
				
			} catch (SessaoException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Falhou", e.getLocalizedMessage()));
				
			} catch (LoginException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Falhou", e.getLocalizedMessage()));
				
			}finally{pesquisa = null;}

		}
	}

}
