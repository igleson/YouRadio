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

	/**
	 * @param login
	 *            , senha , nome e email
	 * @throws UsuarioException
	 **/
	public Usuario(String login, String senha, String nome, String email)
			throws UsuarioException {
		if (login == null || login.equals(""))
			throw new UsuarioException("Login inválido");
		if (nome == null || nome.equals(""))
			throw new UsuarioException("Nome inválido");
		if (email == null || email.equals(""))
			throw new UsuarioException("Email inválido");
		if (senha == null || senha.equals(""))
			throw new UsuarioException("Senha inválida");
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
			throw new UsuarioException("Login inválido");
		this.login = login;
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
			throw new UsuarioException("Senha inválida");
		this.senha = senha;
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
			throw new UsuarioException("Nome inválido");
		this.nome = nome;
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
			throw new UsuarioException("Email inválido");
		this.email = email;
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
		perfilMusical.add(somId);
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
		if (!this.seguidores.contains(usuario.hashCode()))
			this.seguidores.add(usuario.hashCode());
	}

	// testar
	public void seguirUsuario(Usuario usuario) {
		if (!this.seguindo.contains(usuario.hashCode())) {
			this.seguindo.add(usuario.hashCode());
			usuario.addSeguidores(this);
		}
	}

	// testar
	public List<Som> getVisaoDosSons() throws SomException {
		List<Som> retorno = new ArrayList<Som>();
		DadosDoSistema dados = DadosDoSistema.getInstance();
		for (int i = seguindo.size() - 1; i >= 0; i--) {
			Usuario u = dados.usuarioPorId(seguindo.get(i));
			List<Som> temp = new ArrayList<Som>();
			for (Integer id : u.getPerfilMusical()) {
				temp.add(dados.Som(id));
			}
			Collections.sort(temp);
			retorno.addAll(temp);
		}
		return retorno;
	}

	public int getNumeroDeSeguidores() {
		return this.seguidores.size();
	}

	// testar
	public void favoritarSom(Som som) throws SomException {
		sonsFavoritos.add(som.getId());
		DadosDoSistema dados = DadosDoSistema.getInstance();
		som.meFavoritou(this);
		for (int idUsuario : this.seguidores) {
			dados.usuarioPorId(idUsuario).adicionaAoFeedExtra(som.getId());
		}
	}

	// testar
	public List<Integer> getSonsFavoritos() {
		return sonsFavoritos;
	}

	// testar
	public void adicionaAoFeedExtra(int somId) {
		this.feedExtra.add(somId);
	}

	// testar
	public List<Integer> getFeedExtra() {
		return this.feedExtra;
	}

	// testar
	public void setMainFeedRule(OrdenacoesFeedPrincipal ordem) {
		this.ordem = ordem;
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
	public int getNumFavoritosEmComum(Integer idUsuario) {
		int resultado = 0;
		DadosDoSistema dados = DadosDoSistema.getInstance();
		Usuario usuario = dados.usuarioPorId(idUsuario);
		for (Integer idSom : usuario.getSonsFavoritos()) {
			if (sonsFavoritos.contains(idSom))
				resultado++;
		}

		return resultado;
	}

	// testar
	public Integer getNumFontesEmComum(Integer idUsuario) {
		int resultado = 0;
		DadosDoSistema dados = DadosDoSistema.getInstance();
		Usuario usuario = dados.usuarioPorId(idUsuario);
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
					"Usuário não pode adicionar-se a própria lista");
		listas.get(listasPorId.get(idLista)).adicionarUsuario(usuario);
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
			throw new ListaException("Nome já escolhido");
		Lista temp = new Lista(this, nomeDaLista);
		listas.put(nomeDaLista, temp);
		listasPorId.put(temp.getId(), temp.getNomeDaLista());
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
	public int qntsSonsFavoritei(Integer idUsuario) throws SomException {
		int retorno = 0;
		DadosDoSistema dados = DadosDoSistema.getInstance();
		for (Integer idSom : sonsFavoritos) {
			Som som = dados.Som(idSom);
			if (som.getIdDono() == idUsuario)
				retorno++;
		}
		return retorno;
	}

	// testar
	public int compareTo(Integer idUsuario) {
		DadosDoSistema dados = DadosDoSistema.getInstance();
		Usuario usuario = dados.usuarioPorId(idUsuario);
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
			throw new TagException("Tag inválida");
		tagsDisponiveis.put(tag, novaTag);
		tagsPorId.put(novaTag.getId(), novaTag.getNomeDaTag());
		return novaTag.getId();
	}

	public void adicionarTagASom(Integer idSom, String tag)
			throws SomException, TagException {
		DadosDoSistema dados = DadosDoSistema.getInstance();
		if (!tagsPorId.containsValue(tag))
			throw new TagException("Tag inexistente");
		dados.Som(idSom).adicionaTag(tagsDisponiveis.get(tag));
	}

	public Set<Tag> getListaTagsEmSom(Integer idSom) throws SomException {
		DadosDoSistema dados = DadosDoSistema.getInstance();
		return dados.Som(idSom).getTags();
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
