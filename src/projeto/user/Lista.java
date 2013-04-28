package projeto.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import excessoes.ListaException;

public class Lista implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Usuario dono;
	private Integer id;
	private String nomeDaLista;
	private List<Usuario> usuariosNaLista;
	
	public Lista(Usuario dono, String nomeDaLista) {
		usuariosNaLista = new ArrayList<Usuario>();
		this.dono = dono;
		this.nomeDaLista = nomeDaLista;
		id = this.hashCode();
	}
	
	public Usuario getDono() {
		return dono;
	}

	public Integer getId() {
		return id;
	}
	
	public String getNomeDaLista(){
		return nomeDaLista;
	}
	
	public void adicionarUsuario(Usuario usuario) throws ListaException {
		if(usuariosNaLista.contains(usuario)) throw new ListaException("Usu�rio j� existe nesta lista");
		usuariosNaLista.add(usuario);

	}

	public List<Integer> getSonsEmLista() {
		List<Integer> sons = new ArrayList<Integer>();
		for (int i = 0; i < usuariosNaLista.size(); i++) {
			sons.addAll(usuariosNaLista.get(i).getPerfilMusical());
		}
		Collections.reverse(sons);
		return sons;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dono == null) ? 0 : dono.hashCode());
		result = prime * result + ((nomeDaLista == null) ? 0 : nomeDaLista.hashCode());
		return Math.abs(result);
	}
}
