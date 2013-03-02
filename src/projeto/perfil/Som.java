package projeto.perfil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import excessoes.SomException;

import util.Date;


public class Som {

	private String link;
	private GregorianCalendar dataCriacao;
	
	
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
	 * @param link , dataCriação - da musica
	 * @throws  SomException 
	 **/
	public Som(String link, String dataCriacao) throws SomException {
		if(link == null || link.equals("")) throw new SomException("Som inválido");
		if(!Date.dataEhValida(dataCriacao)) throw new SomException("Data de Criação inválida");
		this.link = link;
		
		int dia = Integer.parseInt(dataCriacao.substring(0, 2));
		int mes = Integer.parseInt(dataCriacao.substring(3, 5)) - 1;
		int ano = Integer.parseInt(dataCriacao.substring(6, 10));
		
		this.dataCriacao = new GregorianCalendar(ano, mes, dia);
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
	public String getDataCriacao() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(dataCriacao.getTime());
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
	
}
