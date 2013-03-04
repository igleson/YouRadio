package gerenciadorDeDados;

import java.util.HashMap;
import java.util.Map;

import excessoes.SomException;
import excessoes.UsuarioException;

import projeto.perfil.Som;
import projeto.user.Usuario;

public class DadosDoSistema {
	
	private Map<String, Usuario> todosOsUsuarios;
	private Map<Integer, String> todasAsSessoes;
	private Map<Integer, Som> todosOsSons;
	
	public DadosDoSistema(){
		todosOsUsuarios = new HashMap<String, Usuario>();
		todasAsSessoes = new HashMap<Integer, String>();
		todosOsSons = new HashMap<Integer, Som>();
	}
		
	public int qtdeDeUsuarios(){
		return todosOsUsuarios.size();
	}
	
	public boolean contemUsuario(String login){
		return this.todosOsUsuarios.containsKey(login);
	}
	
	public boolean contemEmail(String email){
		for (Usuario usuario : todosOsUsuarios.values()) {
			if (usuario.getEmail().equals(email))
				return true;
		}
		return false;
	}
	
	public boolean senhaValida(String login, String senha){
		return this.todosOsUsuarios.get(login).testaSenha(senha);
	}
	
	public void adicionaUsuario(String login, String senha, String nome, String email) throws UsuarioException{
		this.todosOsUsuarios.put(login, new Usuario(login, senha, nome, email));
	}
	
	public Usuario usuario(String login){
		return this.todosOsUsuarios.get(login);
	}
	
	public Usuario usuario(int idSessao){
		return todosOsUsuarios.get(todasAsSessoes.get(idSessao));
	}
	
	//gerenciando sons
	
	public void adicionaSom(Som som){
		todosOsSons.put(som.hashCode(), som);
	}
	
	public Som Som(int idSom) throws SomException{
		if (!this.todosOsSons.containsKey(idSom)) throw new SomException("Som inexistente");
		return this.todosOsSons.get(idSom);
	}
	
	//gerenciando sessoes
	
	public void adicionaSessao(String login){
		this.todasAsSessoes.put(login.hashCode(), login);
	}
	
	public void removeSessao(String login){
		this.todasAsSessoes.remove(login.hashCode());
	}
	
	public boolean contemSessao(int sessaoId){
		return todasAsSessoes.containsKey(sessaoId);
	}
	
	public boolean sessaoExiste(int idSessao){
		return todasAsSessoes.containsKey(idSessao);
	}
	
	public String login(int sessaoId) {
		return todasAsSessoes.get(sessaoId);
	}
	

}