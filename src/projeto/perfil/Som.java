package projeto.perfil;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import projeto.user.Usuario;

import excessoes.SomException;

import util.Date;


public class Som implements Comparable<Som>{

	private int idDono;
	private String link;
	private GregorianCalendar dataCriacao;
	private Set<Integer> quemFavoritou;


	/**
	 * @param link , dataCria��o - da musica
	 * @throws  SomException 
	 **/
	public Som(String link, String dataCriacao, int idDono) throws SomException {
		if(link == null || link.equals("")) throw new SomException("Som inv�lido");
		if(!Date.dataEhValida(dataCriacao)) throw new SomException("Data de Cria��o inv�lida");
		this.link = link;
		
		int dia = Integer.parseInt(dataCriacao.substring(0, 2));
		int mes = Integer.parseInt(dataCriacao.substring(3, 5)) - 1;
		int ano = Integer.parseInt(dataCriacao.substring(6, 10));
		
		this.dataCriacao = new GregorianCalendar(ano, mes, dia);
		
		quemFavoritou = new HashSet<Integer>();
		
		this.idDono = idDono;
	}
	
	public int getIdDono(){
		return idDono;
	}
	
	public int getQtdeFavoritados() {
		return this.quemFavoritou.size();
	}
	
	public void favoritou(Usuario usuario){
		if(!this.usuarioFavoritou(usuario)){
			this.quemFavoritou.add(usuario.hashCode());	
		}
	}
	public Collection<Integer> getQuemFavoritou(){
		
		return quemFavoritou;
	}
	public boolean usuarioFavoritou(Usuario usuario){
		return this.quemFavoritou.contains(usuario);
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
	 * @return String - data de cria��o da musica
	 **/
	public GregorianCalendar getDataCriacao() {
		return dataCriacao;
	}
	
	/**
	 * @param datacria��o a ser atualizafa
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


	
}
