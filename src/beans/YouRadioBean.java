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
	private adapterWUISistema sistema = new adapterWUISistema();
	private Integer idSessao;
	private UsuarioLogadoBean usuarioLogado;
	private String postagem;
	private String seguir;
	private String ordenador;
	private String nomeTag;

	
	@PostConstruct
	public void init(){
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

	public void favoritar(projeto.perfil.Som musica) {

		try {
			sistema.favoritarSom(idSessao, musica.getId());
		} catch (SessaoException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", e.getLocalizedMessage()));

		} catch (SomException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", e.getLocalizedMessage()));

		}

	}

	public boolean favoritou(projeto.perfil.Som som) {
		return som.usuarioFavoritou(sistema.usuario(idSessao));

	}

	public String logar() {
		try {
		if (usuarioLogado == null) {
				this.subLogin = login;
				idSessao = sistema.abrirSessao(subLogin, senha);
				usuarioLogado = new UsuarioLogadoBean(subLogin, idSessao);
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

	@SuppressWarnings("finally")
	public String deslogar() {
		try {
		sistema.encerrarSessao(subLogin);
		dispose();}
		finally
		{
			return "faces/index.xhtml";
		}
	}

	private void dispose() {
		this.login = "";
		this.senha = "";
		this.subLogin = "";
		this.idSessao = 0;
		this.usuarioLogado = null;
		this.postagem = "";
		this.seguir = "";
		this.ordenador = "";

	}
	public void criarTag(){
		usuarioLogado.criarTag(nomeTag);
		this.nomeTag = "";
	}
	public UsuarioLogadoBean getUsuarioLogado() {
		return usuarioLogado;
	}

	public void ordenarRecentes() throws SessaoException, SomException {
		sistema.setMainFeedRule(idSessao, OrdenacoesFeedPrincipal.MAIS_RECENTES);
		ordenador = "Recentes";
	}


	public void ordenarPopular() throws SessaoException, SomException {
		sistema.setMainFeedRule(idSessao,
				OrdenacoesFeedPrincipal.COM_MAIS_FAVORITOS);
		ordenador = "Popular";
	}
	
	public void ordenarFavoritos() throws SessaoException, SomException {
		sistema.setMainFeedRule(idSessao,
				OrdenacoesFeedPrincipal.DE_QUEM_FAVORITEI_NO_PASSADO);
		ordenador = "Favoritos";
	}


	public String postar() {
		if (postagem.equals(""))
			return null;
		try {
			sistema.postarSom(idSessao, postagem);
		} catch (SomException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", e.getLocalizedMessage()));

		} catch (SessaoException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", e.getLocalizedMessage()));

		} catch (sistemaEncerradoException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", e.getLocalizedMessage()));

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

	public String getSeguir() {
		return seguir;
	}

	public void setSeguir(String seguir) {
		this.seguir = seguir;
	}

	public void seguir() throws SomException {
		if (!seguir.equals("")) {

			try {
				sistema.seguirUsuario(idSessao, seguir);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("OK",
						"adicionado com sucesso"));

				usuarioLogado.setFeedPrincipal(sistema.getMainFeed(idSessao));
			} catch (SessaoException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage("Falhou", e.getLocalizedMessage()));

			} catch (LoginException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage("Falhou", e.getLocalizedMessage()));

			} finally {
				seguir = null;
			}

		}
	}

	public String getOrdenador() {
		return ordenador;
	}

	public void setOrdenador(String ordenador) {
		this.ordenador = ordenador;
	}

	public String getNomeTag() {
		return nomeTag;
	}

	public void setNomeTag(String nomeTag) {
		this.nomeTag = nomeTag;
	}
	

}
