package beans;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import excessoes.LoginException;
import excessoes.SessaoException;
import excessoes.SomException;
import excessoes.sistemaEncerradoException;
import gerenciadorDeDados.DadosDoSistema;

import projeto.perfil.Som;
import projeto.sistem.OrdenacoesFeedPrincipal;
import projeto.sistem.YouRadio;
import projeto.sistem.adapterWUISistema;

public class UsuarioLogadoBean implements Serializable {

	private static final long serialVersionUID = 5600369132889054255L;

	private String nome;
	private List<SeguindoBean> seguindo;
	private List<Som> feed;
	private adapterWUISistema sistema;
	private int idSessao;
	private List<Som> feedPrincipal;
	private OrdenacoesFeedPrincipal regra;


	public UsuarioLogadoBean(String nome, int idSessao) throws SessaoException,
			sistemaEncerradoException, SomException {
		if (nome.length()> 8) this.nome = nome.substring(0,7); else this.nome = nome;
		this.setIdSessao(idSessao);
		sistema = new adapterWUISistema();
		setFeedPrincipal(sistema.getMainFeed(idSessao));
		setSeguindo(usuariosSenguidos());
		setFeed(perfilMusical());
	}

	private List<SeguindoBean> usuariosSenguidos() throws SessaoException {
		List<SeguindoBean> retorno = new ArrayList<SeguindoBean>();
		Collection<String> seguindo = sistema.getListaDeSeguindo(getIdSessao());
		for (String nome : seguindo) {
			retorno.add(new SeguindoBean(nome)); 
		}
		return retorno;
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

	public List<SeguindoBean> getSeguindo() throws SessaoException {
		this.seguindo = usuariosSenguidos();
		return seguindo;
	}

	public void setSeguindo(List<SeguindoBean> seguindo) {
		this.seguindo = seguindo;
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

	public List<Som> getFeedPrincipal() {
		return feedPrincipal;
	}

	public void setFeedPrincipal(List<Som> feedPrincipal) {
		this.feedPrincipal = feedPrincipal;
	}

	public OrdenacoesFeedPrincipal getRegra() {
		return regra;
	}

	public void setRegra(OrdenacoesFeedPrincipal regra) throws SessaoException, SomException {
		sistema.setMainFeedRule(idSessao, regra);
		feedPrincipal = sistema.getMainFeed(idSessao);
		this.regra = regra;
	}

}
