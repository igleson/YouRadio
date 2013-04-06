package gerenciadorDeDados;

import java.util.HashMap;
import java.util.Map;

import excessoes.SomException;
import excessoes.UsuarioException;

import projeto.perfil.Som;
import projeto.user.Usuario;
import sessao.Sessao;

public class DadosDoSistema {

	private static DadosDoSistema dados;

	private Map<String, Usuario> todosOsUsuarios;
	private Map<Integer, Sessao> todasAsSessoes;
	private Map<Integer, Som> todosOsSons;
	private Map<Integer, Integer> sonsComMaisFavoritos;

	private DadosDoSistema() {
		this.zeraSistema();
	}

	public void zeraSistema() {
		todosOsUsuarios = new HashMap<String, Usuario>();
		todasAsSessoes = new HashMap<Integer, Sessao>();
		todosOsSons = new HashMap<Integer, Som>();
		sonsComMaisFavoritos = new HashMap<Integer, Integer>();
	}

	public static DadosDoSistema getInstance() {
		if (dados == null) {
			dados = new DadosDoSistema();
		}
		return dados;
	}

	public int qtdeDeUsuarios() {
		return todosOsUsuarios.size();
	}


	public boolean contemEmail(String email) {
		for (Usuario usuario : todosOsUsuarios.values()) {
			if (usuario.getEmail().equals(email))
				return true;
		}
		return false;
	}

	public boolean contemLogin(String login) {
		return this.todosOsUsuarios.get(login) != null;
	}

	public boolean senhaValida(String login, String senha) {
		return this.todosOsUsuarios.get(login).testaSenha(senha);
	}

	public void adicionaUsuario(String login, String senha, String nome,
			String email) throws UsuarioException {
		this.todosOsUsuarios.put(login, new Usuario(login, senha, nome, email));
	}

	public Usuario usuario(String login) {
		return this.todosOsUsuarios.get(login);
	}

	public Usuario usuario(int idSessao) {
		return todosOsUsuarios.get(todasAsSessoes.get(idSessao).getLogin());
	}

	public Usuario usuarioPorId(Integer o1) {
		for (Usuario usuario : todosOsUsuarios.values()) {
			if (usuario.hashCode() == o1)
				return usuario;
		}
		return null;
	}

	// gerenciando sons

	public void adicionaSom(Som som) {
		todosOsSons.put(som.hashCode(), som);
	}

	public Som Som(int idSom) throws SomException {
		if (!this.todosOsSons.containsKey(idSom))
			throw new SomException("Som inexistente");
		return this.todosOsSons.get(idSom);
	}

	// gerenciando sessoes

	public void adicionaSessao(Sessao sessao) {
		this.todasAsSessoes.put(sessao.hashCode(), sessao);
	}

	public void removeSessao(String login) {
		this.todasAsSessoes.remove(login.hashCode());
	}

	public boolean contemSessao(int sessaoId) {
		return todasAsSessoes.containsKey(sessaoId);
	}

	public boolean sessaoExiste(int idSessao) {
		return todasAsSessoes.containsKey(idSessao);
	}

	public String login(int sessaoId) {
		return todasAsSessoes.get(sessaoId).getLogin();
	}

	public void adicionaQtdeDeFavoritos(Integer idSom, Integer numero) {
		sonsComMaisFavoritos.put(idSom, +numero);

	}

	public Integer getQtdeFavoritos(Integer idSom) {
		return sonsComMaisFavoritos.get(idSom);
	}

	public boolean contemFavorito(Integer idSom) {
		return sonsComMaisFavoritos.containsKey(idSom);
	}

	public boolean contemUsuario(String login) {
		return this.todosOsUsuarios.containsKey(login);
	}
	
	

}