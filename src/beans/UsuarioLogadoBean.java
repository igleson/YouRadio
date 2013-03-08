package beans;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



import excessoes.SessaoException;
import excessoes.sistemaEncerradoException;
import gerenciadorDeDados.DadosDoSistema;

import projeto.sistem.YouRadio;

public class UsuarioLogadoBean implements Serializable {

	private static final long serialVersionUID = 5600369132889054255L;
	
	private String nome;
    private List<SeguindoBean> seguindo;
    private List<String> feed;
    private YouRadio sistema;
    private int idSessao;

    public UsuarioLogadoBean(String nome, int idSessao) throws SessaoException, sistemaEncerradoException{
    	this.nome = nome;
    	this.setIdSessao(idSessao);
    	sistema = new YouRadio();
    	System.out.println(idSessao);
    	setSeguindo(seguidoresDoUsuario());
    	setFeed(perfilMusical());
    }
    
    //TODO fazer o adapter
    private List<SeguindoBean> seguidoresDoUsuario() throws SessaoException{
    	List<SeguindoBean> retorno = new ArrayList<SeguindoBean>();
    	System.out.println(DadosDoSistema.getInstance().contemSessao(getIdSessao()));
    	Collection<Integer> seguindo = sistema.getListaDeSeguidores(getIdSessao());
    	for (Integer inteiro : seguindo) {
			retorno.add(new SeguindoBean("igleson")); //TODO implementar ap�s o push de raiff
		}
    	return retorno;
    }
    
    private List<String> perfilMusical() throws SessaoException, sistemaEncerradoException{
    	List<String> retorno = new ArrayList<String>();
    	List<Integer> sons = sistema.getPerfilMusical(getIdSessao());
    	for (Integer inteiro : sons) {
			retorno.add("google.com");//TODO ap�s o push de raiff
		}
    	return retorno;
    }
  
    
    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public List<SeguindoBean> getSeguindo() {
		return seguindo;
	}


	public void setSeguindo(List<SeguindoBean> seguindo) {
		this.seguindo = seguindo;
	}


	public List<String> getFeed() {
		return feed;
	}


	public void setFeed(List<String> feed) {
		this.feed = feed;
	}

	public int getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(int idSessao) {
		this.idSessao = idSessao;
	}

}
