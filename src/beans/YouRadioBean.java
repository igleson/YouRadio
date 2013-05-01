package beans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import excessoes.LoginException;
import excessoes.SessaoException;
import excessoes.SomException;
import excessoes.sistemaEncerradoException;
import projeto.sistem.OrdenacoesFeedPrincipal;
import projeto.sistem.adapterWUISistema;
import threads.ThreadQueSalva;

@ManagedBean
@SessionScoped
public class YouRadioBean {
	private String login;
	private String senha;
	private String subLogin;
	private adapterWUISistema sistema = adapterWUISistema.getInstance();
	private Integer idSessao;
	private UsuarioLogadoAdapter usuarioLogado;
	private String nomeTag;

	@PostConstruct
	public void init() {
		Thread thread = new ThreadQueSalva();
		thread.start();
		
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

	public String logar() {
		try {
			if (usuarioLogado == null) {
				this.subLogin = login;
				idSessao = sistema.abrirSessao(subLogin, senha);
				usuarioLogado = new UsuarioLogadoAdapter(subLogin, idSessao);
				sistema.setUsuarioLogado(usuarioLogado);
				sistema.setSessao(idSessao);
				sistema.setLogin(subLogin);
				dispose();
			}
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

		} catch (SomException e) {
			// FacesContext context = FacesContext.getCurrentInstance();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", e.getLocalizedMessage()));
		} finally {
			login = null;
			senha = null;
		}
		return null;
	}

	public void dispose() {
		login = null;
		senha = null;
		subLogin = null;
		idSessao = null;
		finally
		{
			return "faces/index.xhtml";
		}
	}
		login = "";
		senha = "";
		subLogin = "";
		idSessao = 0;
		this.usuarioLogado = null;
		this.postagem = "";
		this.seguir = "";
		this.ordenador = "";
	public void criarTag(){
		usuarioLogado.criarTag(nomeTag);
		this.nomeTag = "";
	}
	}
	}

	public String getNomeTag() {
		return nomeTag;
	}

	public void setNomeTag(String nomeTag) {
		this.nomeTag = nomeTag;
}
