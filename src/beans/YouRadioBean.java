package beans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import projeto.perfil.Som;
import projeto.sistem.OrdenacoesFeedPrincipal;
import projeto.sistem.adapterWUISistema;
import threads.ThreadQueSalva;
import excessoes.LoginException;
import excessoes.SessaoException;
import excessoes.SomException;
import excessoes.sistemaEncerradoException;

@ManagedBean
@SessionScoped
public class YouRadioBean {
	private String login;
	private String senha;
	private String subLogin;
	private adapterWUISistema sistema = adapterWUISistema.getInstance();
	private Integer idSessao;
	private UsuarioLogadoAdapter usuarioLogado;
	private String postagem;
	private String seguir;
	private String nomeTag;
	private String ordenador;

	public UsuarioLogadoAdapter getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(UsuarioLogadoAdapter usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

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

			this.subLogin = login;
			idSessao = sistema.abrirSessao(subLogin, senha);
			usuarioLogado = new UsuarioLogadoAdapter(subLogin, idSessao);
			FacesContext.getCurrentInstance().getExternalContext()
					.getApplicationMap().put("user", usuarioLogado);
			dispose();

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
		idSessao = null;
		// finally
		// {
		// return "faces/index.xhtml";
		// }
	}

	public String postar() {
		if (postagem.equals(""))
			return null;
		try {
			sistema.postarSom(this.usuarioLogado.getIdSessao(), postagem);
			apagar();
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
		return "";

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
				sistema.seguirUsuario(usuarioLogado.getIdSessao(), seguir);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("OK",
						"adicionado com sucesso"));

				usuarioLogado.setFeedPrincipal(sistema
						.getMainFeed(usuarioLogado.getIdSessao()));
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

	public void apagar() {
		this.postagem = null;

	}

	public void ordenarRecentes() throws SessaoException, SomException {
		sistema.setMainFeedRule(usuarioLogado.getIdSessao(),
				OrdenacoesFeedPrincipal.MAIS_RECENTES);
		ordenador = "Recentes";
	}

	public void ordenarPopular() throws SessaoException, SomException {
		sistema.setMainFeedRule(usuarioLogado.getIdSessao(),
				OrdenacoesFeedPrincipal.COM_MAIS_FAVORITOS);
		ordenador = "Popular";
	}

	public void ordenarFavoritos() throws SessaoException, SomException {
		sistema.setMainFeedRule(usuarioLogado.getIdSessao(),
				OrdenacoesFeedPrincipal.DE_QUEM_FAVORITEI_NO_PASSADO);
		ordenador = "Favoritos";
	}

	public String deslogar() {
		sistema.encerrarSessao(usuarioLogado.getLogin());
		dispose();
		return "faces/index.xhtml";
	}

	public void favoritar(Som musica) {

		try {
			sistema.favoritarSom(usuarioLogado.getIdSessao(), musica.getId());
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

	public boolean favoritou(Som som) {
		return som
				.usuarioFavoritou(sistema.usuario(usuarioLogado.getIdSessao()));
	}

	public void criarTag() {
		this.usuarioLogado.criarTag(nomeTag);
		this.nomeTag = "";
	}

	public String getNomeTag() {
		return nomeTag;
	}

	public void setNomeTag(String nomeTag) {
		this.nomeTag = nomeTag;
	}

}
