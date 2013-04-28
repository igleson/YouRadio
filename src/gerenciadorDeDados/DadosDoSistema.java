package gerenciadorDeDados;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import projeto.perfil.Som;
import projeto.user.Usuario;
import sessao.Sessao;
import excessoes.SomException;
import excessoes.UsuarioException;

public class DadosDoSistema implements Serializable {
	
	//dados.dat sendo salvo no diretório padrão do JBOSS
	private static final String NOME_DO_ARQUIVO = "dados.dat";
	private static final long serialVersionUID = 1L;

	private static DadosDoSistema dados;

	private Map<String, Usuario> todosOsUsuarios;
	private Map<Integer, Sessao> todasAsSessoes;
	private Map<Integer, Som> todosOsSons;
	private Map<Integer, Integer> sonsComMaisFavoritos;
	private static Lock lock;
	

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
		if (lock == null) DadosDoSistema.lock = new ReentrantLock();
		if (dados == null) {
			try {
				dados = recuperarDados();
			} catch (IOException e) {
				dados = new DadosDoSistema();
			}
		}
		return dados;
	}
	
	public static Lock getLock() {
		return lock;
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
	
	public void encerrarSistema() {
		lock.lock();
		try {
			dados = null;
		}
		finally {
			lock.unlock();
		}
	}
	
	public boolean senhaValida(String login, String senha) {
		return this.todosOsUsuarios.get(login).testaSenha(senha);
	}

	public void adicionaUsuario(String login, String senha, String nome,
			String email) throws UsuarioException {
		lock.lock();
		try {
			this.todosOsUsuarios.put(login, new Usuario(login, senha, nome, email));
		}
		finally {
			lock.unlock();
		}
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
		lock.lock();
		try {
			todosOsSons.put(som.hashCode(), som);
		}
		finally {
			lock.unlock();
		}
	}

	public Som Som(int idSom) throws SomException {
		if (!this.todosOsSons.containsKey(idSom))
			throw new SomException("Som inexistente");
		return this.todosOsSons.get(idSom);
	}

	// gerenciando sessoes

	public void adicionaSessao(Sessao sessao) {
		lock.lock();
		try {
			this.todasAsSessoes.put(sessao.hashCode(), sessao);
		}
		finally {
			lock.unlock();
		}
	}

	public void removeSessao(String login) {
		lock.lock();
		try {
			this.todasAsSessoes.remove(login.hashCode());
		}
		finally {
			lock.unlock();
		}
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
		lock.lock();
		try {
			sonsComMaisFavoritos.put(idSom, +numero);
		}
		finally {
			lock.unlock();
		}

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

	public Set<Integer> Sons() {
		
		return todosOsSons.keySet();
		
	}
	
	private static void persisteDados() throws IOException{
		ObjectOutputStream out = null;
		try{
			out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(NOME_DO_ARQUIVO)));
			out.writeObject(dados);
		}catch(IOException e){
			System.err.println(e.getMessage());
		}finally{
			if (out!=null){
				out.close();
			}
		}
	}
	
	public static void persistirDados() throws IOException {
		lock.lock();
		try {
			persisteDados();
		}
		finally{
			lock.unlock();
		}
	}
	
	public static DadosDoSistema recuperarDados() throws IOException{
		ObjectInputStream in = null;
		try{
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(NOME_DO_ARQUIVO)));
			return (DadosDoSistema) in.readObject();
		}catch(ClassNotFoundException e){
			System.err.println(e.getMessage());
		}finally{
			if (in!=null){
				in.close();
			}
		}
		return null;
	}

}