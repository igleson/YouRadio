package beans;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import projeto.perfil.Som;
import projeto.sistem.OrdenacoesFeedPrincipal;
import projeto.sistem.adapterWUISistema;
import excessoes.LoginException;
import excessoes.SessaoException;
import excessoes.SomException;
import excessoes.sistemaEncerradoException;

@ManagedBean
@SessionScoped
public class LogadoBean {

		private adapterWUISistema sistema;		
		private String postagem = "";
		private String seguir;
		private String ordenador;
		private String nomeTag;
		
		public LogadoBean(){
			sistema = adapterWUISistema.getInstance();
		}
		
		public UsuarioLogadoAdapter getUsuarioLogado() {
			System.out.println(sistema.getUsuarioLogado()== null);
			return this.sistema.getUsuarioLogado();
		}

		public void setUsuarioLogado(UsuarioLogadoAdapter usuarioLogado) {
			this.sistema.setUsuarioLogado(usuarioLogado);
		}
		
		public String postar() {
			if (postagem.equals(""))
				return null;
			try {
				sistema.postarSom(this.sistema.getSessao(), postagem);
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
					sistema.seguirUsuario(sistema.getSessao(), seguir);
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage("OK",
							"adicionado com sucesso"));

					sistema.getUsuarioLogado().setFeedPrincipal(sistema.getMainFeed(sistema.getSessao()));
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
			sistema.setMainFeedRule(sistema.getSessao(), OrdenacoesFeedPrincipal.MAIS_RECENTES);
			ordenador = "Recentes";
		}


		public void ordenarPopular() throws SessaoException, SomException {
			sistema.setMainFeedRule(sistema.getSessao(),
					OrdenacoesFeedPrincipal.COM_MAIS_FAVORITOS);
			ordenador = "Popular";
		}
		
		public void ordenarFavoritos() throws SessaoException, SomException {
			sistema.setMainFeedRule(sistema.getSessao(),
					OrdenacoesFeedPrincipal.DE_QUEM_FAVORITEI_NO_PASSADO);
			ordenador = "Favoritos";
		}
		
		public String deslogar() {
			sistema.encerrarSessao(this.sistema.getLogin());
			dispose();
			return "faces/index.xhtml";
		}
		
		public void favoritar(Som musica) {

			try {
				sistema.favoritarSom(sistema.getSessao(), musica.getId());
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
			return som.usuarioFavoritou(sistema.usuario(sistema.getSessao()));
		}
		
		private void dispose() {
//			login = "";
//			idSessao = 0;
//			usuarioLogado = null;
//			postagem = "";
//			seguir = "";
//			ordenador = "";
		}
		
		public void criarTag(){
			this.sistema.getUsuarioLogado().criarTag(nomeTag);
			this.nomeTag = "";
		}
		
		public String getNomeTag() {
			return nomeTag;
		}

		public void setNomeTag(String nomeTag) {
			this.nomeTag = nomeTag;
		}

}