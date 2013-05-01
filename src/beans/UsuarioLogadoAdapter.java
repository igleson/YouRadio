package beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import projeto.perfil.Som;
import projeto.sistem.adapterWUISistema;
import projeto.user.Lista;
import projeto.user.Tag;
import excessoes.ListaException;
import excessoes.SessaoException;
import excessoes.SomException;
import excessoes.TagException;
import excessoes.sistemaEncerradoException;


public class UsuarioLogadoAdapter implements Serializable {

	private static final long serialVersionUID = 5600369132889054255L;

	private String nome;
	private List<Som> feed;
	private adapterWUISistema sistema;
	private int idSessao;
	private String nomeDaLista;
	private Lista listaSelecionada;
	private String usuarioAdicionado;
	private List<String> sonsDoGrupo;
	private Map<String, Tag> tags;
	


	public List<String> getSonsDoGrupo() {
		return sonsDoGrupo;
	}



	public void setSonsDoGrupo(List<String> sonsDoGrupo) {
		this.sonsDoGrupo = sonsDoGrupo;
	}



	public String getUsuarioAdicionado() {
		return usuarioAdicionado;
	}



	public void setUsuarioAdicionado(String usuarioAdicionado) {
		this.usuarioAdicionado = usuarioAdicionado;
	}



	public Lista getListaSelecionada() {
		return listaSelecionada;
	}



	public void setListaSelecionada(Lista listaSelecionada) {
		this.listaSelecionada = listaSelecionada;
	}



	public String getNomeDaLista() {
		return nomeDaLista;
	}



	public void setNomeDaLista(String nomeDaLista) {
		this.nomeDaLista = nomeDaLista;
	}



	public UsuarioLogadoAdapter(String nome, int idSessao) throws SessaoException,
			sistemaEncerradoException, SomException {
		if (nome.length()> 8) this.nome = nome.substring(0,7); else this.nome = nome;
		this.setIdSessao(idSessao);
		sistema = new adapterWUISistema();
		tags = sistema.usuario(idSessao).mapTags();
		setFeedPrincipal(sistema.getMainFeed(idSessao));
		setTags(sistema.usuario(idSessao).mapTags());
		setFeed(perfilMusical());
		
		
		
	}

	

	private List<Som> perfilMusical(){

				try {
					List<Som> temp = sistema.getPerfilMusical(getIdSessao());
					Collections.reverse(temp);
					return temp;
				} catch (SessaoException e) {
				
					e.printStackTrace();
				} catch (sistemaEncerradoException e) {
					
					e.printStackTrace();
				} catch (SomException e) {
					e.printStackTrace();
				}
				return null;
	}

	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<String> getSeguindo() throws SessaoException {

		return sistema.getListaDeSeguindo(getIdSessao());
	}
	
	public List<Som> getFeed() throws SessaoException,
			sistemaEncerradoException {
		this.feed = perfilMusical();
		return feed;
	}

	public void setFeed(List<Som> feed) {
		this.feed = feed;
	}

	public int getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(int idSessao) {
		this.idSessao = idSessao;
	}

	public List<Som> getFeedPrincipal(){
		try {
			return sistema.getMainFeed(idSessao);
		} catch (SessaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public List<Som> feedExtra(){
		try {
			return sistema.feedExtra(idSessao);
		} catch (SessaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void setFeedPrincipal(List<Som> feedPrincipal) {
		//this.feedPrincipal = feedPrincipal;
	}
	
	
	public List<String> recomendacoesDoSistema() throws SomException{
		return sistema.recomendacaoDoSistema(getIdSessao());
	}
	
	public int criarLista(){
		try {
			return sistema.criarLista(nomeDaLista,idSessao);
		} catch (ListaException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", e.getLocalizedMessage()));
		}
		return 0;
	}
	
	
	
	public void adicionarUsuario() throws SessaoException{
		
		if(listaSelecionada==null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", "Selecione uma lista"));
		}
		else if(usuarioAdicionado == null||usuarioAdicionado.equals("")) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", "Nome invalido"));
		}else if (sistema.usuario(idSessao).getListaDeSeguindo().contains(sistema.usuario(usuarioAdicionado))){
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", "Usuário não é seu amigo"));
		}
		else{
			sistema.adicionarUsuario(idSessao, listaSelecionada.getId(), usuarioAdicionado);
			listaSelecionada = null;
			usuarioAdicionado = null;
		}
	}
	
	public List<Lista> listas(){
		return sistema.getListas(idSessao);
	}
	
	public void verSonsEmGrupo(){
		if(listaSelecionada==null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", "Selecione uma lista"));
		}else{
			try {
				sonsDoGrupo = sistema.verSonsEmGrupo(listaSelecionada);
			} catch (SomException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage("Falhou", e.getLocalizedMessage()));
			}
		}
	}



	public Map<String, Tag> getTags() {
		return tags;
	}



	public void setTags(Map<String, Tag> tags) {
		this.tags = tags;
	}



	public void criarTag(String nomeTag) {
		try {
			sistema.criarTag(idSessao, nomeTag);
		} catch (TagException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage("Falhou", e.getLocalizedMessage()));
		}
		
	}

	
	

}
