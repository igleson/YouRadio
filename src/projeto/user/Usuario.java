package projeto.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import projeto.perfil.populares;
import projeto.perfil.Som;
import projeto.sistem.OrdenacoesFeedPrincipal;
import util.Colecaoes;
import excessoes.ListaException;
import excessoes.SomException;
import excessoes.TagException;
import excessoes.UsuarioException;
import gerenciadorDeDados.DadosDoSistema;

public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private String senha;
	private String nome;
	private String email;
	private List<Integer> perfilMusical;
	private List<Integer> seguindo;
	private List<Integer> seguidores;
	private List<Integer> sonsFavoritos;
	private List<Integer> feedExtra;
	private OrdenacoesFeedPrincipal ordem;
	private HashMap<String, Lista> listas;
	private HashMap<Integer, String> listasPorId;
	private LinkedHashMap<String, Tag> tagsDisponiveis;
	private LinkedHashMap<Integer, String> tagsPorId;
	
	
	public Map<String, Tag> mapTags() {
		return tagsDisponiveis;
	}

	private Lock lock;

	/**
	 * @param login
	 *            , senha , nome e email
	 * @throws UsuarioException
	 **/
	public Usuario(String login, String senha, String nome, String email)
			throws UsuarioException {
		if (login == null || login.equals(""))
			throw new UsuarioException("Login inv�lido");
		if (nome == null || nome.equals(""))
			throw new UsuarioException("Nome inv�lido");
		if (email == null || email.equals(""))
			throw new UsuarioException("Email inv�lido");
		if (senha == null || senha.equals(""))
			throw new UsuarioException("Senha inv�lida");
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
		this.seguindo = new ArrayList<Integer>();
		this.seguidores = new ArrayList<Integer>();
		this.sonsFavoritos = new ArrayList<Integer>();
		this.feedExtra = new ArrayList<Integer>();
		this.ordem = OrdenacoesFeedPrincipal.MAIS_RECENTES;
		this.listas = new HashMap<String, Lista>();
		this.listasPorId = new HashMap<Integer, String>();
		this.tagsDisponiveis = new LinkedHashMap<String, Tag>();
		this.tagsPorId = new LinkedHashMap<Integer, String>();
		this.lock = DadosDoSistema.getLock();
	}

	public HashMap<Integer, String> getTagsPorId() {
		return tagsPorId;
	}

	public String getListasPorId(Integer idLista) {
		return listasPorId.get(idLista);
	}

	/**
	 * @return String
	 **/
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            a ser atualizado
	 * @return void
	 * @throws UsuarioException
	 **/
	public void setLogin(String login) throws UsuarioException {
		if (login == null || login.equals(""))
			throw new UsuarioException("Login inv�lido");
		lock.lock();
		try {
			this.login = login;
		}
		finally {
			lock.unlock();
		}
	}

	/**
	 * @param senha
	 *            a ser atualizada
	 * @return void
	 * @throws UsuarioException
	 * 
	 **/
	public void setSenha(String senha) throws UsuarioException {
		if (senha == null || senha.equals(""))
			throw new UsuarioException("Senha inv�lida");
		lock.lock();
		try {
			this.senha = senha;
		}
		finally {
			lock.unlock();
		}
	}

	/**
	 * @return String
	 **/
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            a ser atualizado
	 * @return void
	 * @throws UsuarioException
	 * 
	 **/
	public void setNome(String nome) throws UsuarioException {
		if (nome == null || nome.equals(""))
			throw new UsuarioException("Nome inv�lido");
		lock.lock();
		try {
			this.nome = nome;
		}
		finally {
			lock.unlock();
		}
	}

	/**
	 * @param senha
	 *            a se averiguada
	 * @return boolean
	 **/
	// testar
	public boolean testaSenha(String senha) {
		return this.senha.equals(senha);
	}

	/**
	 * @return String
	 **/
	// testar
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            a ser atualizado
	 * @return void
	 * @throws UsuarioException
	 * 
	 **/
	// testar
	public void setEmail(String email) throws UsuarioException {
		if (email == null || email.equals(""))
			throw new UsuarioException("Email inv�lido");
		lock.lock();
		try {
			this.email = email;
		}
		finally {
			lock.unlock();
		}
	}

	/**
	 * @param som
	 *            id - identificador do som
	 * @return void
	 **/
	// testar
	public void postarSom(int somId) {
		if (perfilMusical == null)
			perfilMusical = new ArrayList<Integer>();
		lock.lock();
		try {
			perfilMusical.add(somId);
		}
		finally {
			lock.unlock();
		}
	}

	/**
	 * @return List<Integer>
	 **/
	// testar
	public List<Integer> getPerfilMusical() {
		if (perfilMusical == null)
			return new ArrayList<Integer>();
		return perfilMusical;
	}

	public List<Integer> getFontesDeSons() {
		List<Integer> retorno = new ArrayList<Integer>();
		retorno.addAll(seguindo);
		return retorno;

	}

	// testar
	public List<Integer> getListaDeSeguidores() {
		return seguidores;
	}

	// testar
	public void addSeguidores(Usuario usuario) {
		lock.lock();
		try {
			if (!this.seguidores.contains(usuario.hashCode()))
				this.seguidores.add(usuario.hashCode());
		}
		finally {
			lock.unlock();
		}
	}

	// testar
	public void seguirUsuario(Usuario usuario) {
		lock.lock();
		try {
			if (!this.seguindo.contains(usuario.hashCode())) {
				this.seguindo.add(usuario.hashCode());
				usuario.addSeguidores(this);
			}
		}
		finally {
			lock.unlock();
		}
	}


	public int getNumeroDeSeguidores() {
		return this.seguidores.size();
	}

	// testar
	public void favoritarSom(Som som) throws SomException {
		lock.lock();
		try {
			sonsFavoritos.add(som.getId());
			som.meFavoritou(this);
		}
		finally {
			lock.unlock();
		}
	}

	// testar
	public List<Integer> getSonsFavoritos() {
		return sonsFavoritos;
	}

	// testar
	public void adicionaAoFeedExtra(Som som) {
		lock.lock();
		try {
			this.feedExtra.add(som.getId());
		}
		finally {
			lock.unlock();
		}
	}

	// testar
	public List<Integer> getFeedExtra() {
		return this.feedExtra;
	}

	// testar
	public void setMainFeedRule(OrdenacoesFeedPrincipal ordem) {
		lock.lock();
		try {
			this.ordem = ordem;
		}
		finally {
			lock.unlock();
		}
	}

	private List<Integer> mainFeed() {
		List<Integer> retorno = new ArrayList<Integer>();
		DadosDoSistema dados = DadosDoSistema.getInstance();
		for (int i = seguindo.size() - 1; i >= 0; i--) {
			Usuario u = dados.usuarioPorId(seguindo.get(i));
			retorno.addAll(u.getPerfilMusical());
		}
		return retorno;
	}

	private List<Integer> mainFeedMaisRecentes() {
		List<Integer> retorno = mainFeed();
		Collections.reverse(retorno);
		return retorno;
	}

	private List<Integer> mainFeedPopulares() {
		List<Integer> ids = mainFeed();
		Collections.sort(ids, new populares());
		return ids;
	}

	// testar
	public List<Integer> getMainFeed() throws SomException {
		DadosDoSistema dados = DadosDoSistema.getInstance();

		if (ordem.ordinal() == 0)
			return mainFeedMaisRecentes();
		else if (ordem.ordinal() == 1) {
			return mainFeedPopulares();
		} else if (ordem.ordinal() == 2) {
			Map<Integer, Integer> qtdeDeFavoritados = new HashMap<Integer, Integer>();
			for (Integer id : sonsFavoritos) {
				Som som = dados.Som(id);
				if (!qtdeDeFavoritados.containsKey(som.getIdDono()))
					qtdeDeFavoritados.put(som.getIdDono(), 0);
				int qtde = qtdeDeFavoritados.get(som.getIdDono());
				qtdeDeFavoritados.put(som.getIdDono(), qtde + 1);
			}
			List<Integer> tempSeguindo = new ArrayList<Integer>();
			Colecaoes.copiar(tempSeguindo, this.seguindo);
			Collections.sort(tempSeguindo, new qtdeDeFavoritos(
					qtdeDeFavoritados));
			List<Integer> retorno = new ArrayList<Integer>();
			for (Integer id : tempSeguindo) {
				List<Integer> perfil = new ArrayList<Integer>();
				Colecaoes.copiar(perfil, dados.usuarioPorId(id)
						.getPerfilMusical());
				Collections.reverse(perfil);
				retorno.addAll(perfil);
			}
			return retorno;
		}
		return new ArrayList<Integer>();
	}

	// testar
	public Collection<Integer> getListaDeSeguindo() {
		return this.seguindo;
	}

	// testar
	public int getNumFavoritosEmComum(Usuario usuario) {
		int resultado = 0;
		for (Integer idSom : usuario.getSonsFavoritos()) {
			if (sonsFavoritos.contains(idSom))
				resultado++;
		}
		return resultado;
	}

	// testar
	public Integer getNumFontesEmComum(Usuario usuario) {
		int resultado = 0;
		for (Integer idSom : seguindo) {
			if (usuario.getFontesDeSons().contains(idSom))
				resultado++;
		}
		return resultado;
	}

	// testar
	public void adicionarUsuario(int idLista, Usuario usuario)
			throws ListaException {
		if (this.hashCode() == usuario.hashCode())
			throw new ListaException(
					"Usu�rio n�o pode adicionar-se a pr�pria lista");
		lock.lock();
		try {
			listas.get(listasPorId.get(idLista)).adicionarUsuario(usuario);
		}
		finally {
			lock.unlock();
		}
	}

	// testar

	public List<Integer> getSonsEmLista(String nomeDaLista) {
		return listas.get(nomeDaLista).getSonsEmLista();
	}

	// testar
	public int criarLista(String nomeDaLista) throws ListaException {
		if (nomeDaLista == "")
			throw new ListaException(" digite um nome");
		if (listas.containsKey(nomeDaLista))
			throw new ListaException("Nome j� escolhido");
		Lista temp = new Lista(this, nomeDaLista);
		lock.lock();
		try {
			listas.put(nomeDaLista, temp);
			listasPorId.put(temp.getId(), temp.getNomeDaLista());
		}
		finally {
			lock.unlock();
		}
		return temp.getId();
	}

	// testar
	public List<Lista> getListas() {
		List<Lista> retorno = new ArrayList<Lista>();
		for (Lista lista : listas.values()) {
			retorno.add(lista);
		}
		return retorno;
	}

	// testar
	public int compareTo(Usuario usuario) {
		String nome2 = usuario.getNome();
		return this.nome.compareTo(nome2);
	}

	// testar
	public Lista getListaEspecifica(String nomeDaLista) {
		return listas.get(nomeDaLista);

	}

	public int criarTag(String tag) throws TagException {
		Tag novaTag = new Tag(this, tag);
		if (tagsPorId.containsValue(tag))
			throw new TagException("Tag inv�lida");
		lock.lock();
		try {
			tagsDisponiveis.put(tag, novaTag);
			tagsPorId.put(novaTag.getId(), novaTag.getNomeDaTag());
		}
		finally {
			lock.unlock();
		}
		return novaTag.getId();
	}

	public void adicionarTagASom(Som som, String tag)
			throws SomException, TagException {
		if (!tagsPorId.containsValue(tag))
			throw new TagException("Tag inexistente");
		lock.lock();
		try {
			som.adicionaTag(tagsDisponiveis.get(tag));
		}
		finally {
			lock.unlock();
		}
	}

	public Set<Tag> getListaTagsEmSom(Som som) throws SomException {
		return som.getTags();
	}

	public String getNomeTag(Integer idTag) throws TagException {
		if (!tagsPorId.containsKey(idTag))
			throw new TagException("Tag inexistente");
		return tagsPorId.get(idTag);
	}

	public Set<Integer> getTagsDisponiveis() {
		return tagsPorId.keySet();
	}

}
