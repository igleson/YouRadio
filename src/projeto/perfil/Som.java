package projeto.perfil;

import java.io.Serializable;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import projeto.user.Tag;
import projeto.user.Usuario;

import excessoes.SomException;

import util.Date;


public class Som implements Comparable<Som>, Serializable {

	private static final long serialVersionUID = 1L;
	private int idDono;
	private String link;
	private GregorianCalendar dataCriacao;
	private Set<Integer> quemFavoritou;
	private Set<Tag> tags;


	/**
	 * @param link , dataCriação - da musica
	 * @throws  SomException 
	 **/
	public Som(String link, String dataCriacao, int idDono) throws SomException {
		if(link == null || link.equals("")) throw new SomException("Som inválido");
		if(!Date.dataEhValida(dataCriacao)) throw new SomException("Data de Criação inválida");
		this.link = link;
		
		int dia = Integer.parseInt(dataCriacao.substring(0, 2));
		int mes = Integer.parseInt(dataCriacao.substring(3, 5)) - 1;
		int ano = Integer.parseInt(dataCriacao.substring(6, 10));
		
		this.dataCriacao = new GregorianCalendar(ano, mes, dia);
		
		quemFavoritou = new HashSet<Integer>();
		
		this.idDono = idDono;
		this.tags = new LinkedHashSet<Tag>();
	}
	
	public Set<Tag> getTags() {
		return tags;
	}
	
	public int getIdDono(){
		return idDono;
	}
	
	public int getQtdeFavoritados() {
		return this.quemFavoritou.size();
	}
	
	public void meFavoritou(Usuario usuario){
		if(!this.usuarioFavoritou(usuario)){
			this.quemFavoritou.add(usuario.hashCode());	
		}
	}
	public Collection<Integer> getQuemFavoritou(){
		return quemFavoritou;
	}
	
	public boolean usuarioFavoritou(Usuario usuario){
		return this.quemFavoritou.contains(usuario.hashCode());
	}
	
	/**
	 * @return int hascode do som
	 **/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		return Math.abs(result);
	}
	
	
	/**
	 * @return String link da musica
	 **/
	public String getLink() {
		return link;
	}
	
	/**
	 * @param link - musica a ser atualizada
	 * @return void
	 **/
	public void setLink(String link) {
		this.link = link;
	}
	
	/**
	 * @return String - data de criação da musica
	 **/
	public GregorianCalendar getDataCriacao() {
		return dataCriacao;
	}
	
	/**
	 * @param datacriação a ser atualizafa
	 * @return void
	 **/
	public void setDataCriacao(String dataCriacao) {
		int dia = Integer.parseInt(dataCriacao.substring(0, 2));
		int mes = Integer.parseInt(dataCriacao.substring(3, 5)) - 1;
		int ano = Integer.parseInt(dataCriacao.substring(6, 10));
		
		this.dataCriacao = new GregorianCalendar(ano, mes, dia);
	}

	@Override
	public int compareTo(Som som) {
		return -1*this.dataCriacao.compareTo(som.getDataCriacao());
	}

	public int getId() {
		return this.hashCode();
	}

	public void adicionaTag(Tag tag) {
		tags.add(tag);
	}


	
}
