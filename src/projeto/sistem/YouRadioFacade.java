package projeto.sistem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import projeto.perfil.Som;
import projeto.user.Tag;
import util.Colecaoes;
import util.Strings;
import excessoes.AtributoException;
import excessoes.CadastroException;
import excessoes.ListaException;
import excessoes.LoginException;
import excessoes.SessaoException;
import excessoes.SomException;
import excessoes.TagException;
import excessoes.UsuarioException;
import excessoes.sistemaEncerradoException;

public class YouRadioFacade {

	private YouRadio sistema;
	
	 private final String REGRA1 = "PRIMEIRO OS SONS POSTADOS MAIS RECENTEMENTE PELAS FONTES DE SONS";
	 private final String REGRA2 = "PRIMEIRO OS SONS COM MAIS FAVORITOS";
	 private final String REGRA3 = "PRIMEIRO SONS DE FONTES DAS QUAIS FAVORITEI SONS NO PASSADO";

	public YouRadioFacade() {
		sistema = new YouRadio();
	}

	public void zerarSistema() {
		sistema.zerarSistema();
	}
	
	public void reiniciarSistema() {
		sistema.reiniciarSistema();
	}

	public void criarUsuario(String login, String senha, String nome,
			String email) throws UsuarioException, CadastroException,
			sistemaEncerradoException {
		sistema.criarUsuario(login, senha, nome, email);
	}

	public String getIDUsuario(int sessaoId) throws SessaoException {
		return sistema.getIdUsuario(sessaoId);
	}

	public int abrirSessao(String login, String senha) throws LoginException,
			sistemaEncerradoException {
		return sistema.abrirSessao(login, senha);
	}

	public String getPerfilMusical(int sessaoId) throws SessaoException,
			sistemaEncerradoException {
		List<Integer> listSonsIds = sistema.getPerfilMusical(sessaoId);
		return Colecaoes.ColecaoParaStringReverso(listSonsIds);
	}

	public int postarSom(int sessaoId, String link, String dataCriacao)
			throws SomException, SessaoException, sistemaEncerradoException {
		return sistema.postarSom(sessaoId, link, dataCriacao);

	}

	public String getAtributoUsuario(String login, String atributo)
			throws UsuarioException, AtributoException,
			sistemaEncerradoException {
		if (atributo == null || atributo.equals(""))
			throw new AtributoException("Atributo inválido");
		else if (atributo.equals("nome"))
			return sistema.nomeDoUsuario(login);
		else if (atributo.equals("email"))
			return sistema.emailDoUsuario(login);
		else
			throw new AtributoException("Atributo inexistente");
	}

	public String getAtributoSom(String idSom, String atributo)
			throws SomException, AtributoException, NumberFormatException,
			sistemaEncerradoException {
		if (idSom == null || idSom.equals(""))
			throw new SomException("Som inválido");
		else if (atributo == null || atributo.equals(""))
			throw new AtributoException("Atributo inválido");
		else if (!atributo.equals("link") && !atributo.endsWith("dataCriacao"))
			throw new AtributoException("Atributo inexistente");
		else if (idSom == null || idSom.equals(""))
			throw new SomException("Som inválido");
		else if (atributo.equals("link"))
			return sistema.linkDoSom(Integer.parseInt(idSom));
		else
			return sistema.dataDeCriacaoSom(Integer.parseInt(idSom));

	}

	public int getNumeroDeSeguidores(String idSessao) throws SessaoException {
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
		return sistema.getNumeroDeSeguidores(Integer.parseInt(idSessao));
	}

	public String getFontesDeSons(String sessaoId) throws SessaoException {
		if (sessaoId == null || sessaoId.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(sessaoId))
			throw new SessaoException("Sessão inexistente");
		if (!Strings.ehUmNumero(sessaoId))
			throw new SessaoException("Sessão inexistente");
		List<Integer> listSonsIds = sistema.getFontesDeSons(Integer
				.parseInt(sessaoId));
		return Colecaoes.ColecaoParaString(listSonsIds);
	}

	public String getListaDeSeguidores(String sessaoId) throws SessaoException {
		if (sessaoId == null || sessaoId.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(sessaoId))
			throw new SessaoException("Sessão inexistente");
		Collection<Integer> listSonsIds = sistema.getListaDeSeguidores(Integer
				.parseInt(sessaoId));
		return Colecaoes.ColecaoParaString(listSonsIds);
	}

	public void seguirUsuario(String idSessao, String login)
			throws SessaoException, LoginException {
		if (login == null || login.equals(""))
			throw new LoginException("Login inválido");
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
		sistema.seguirUsuario(Integer.parseInt(idSessao), login);
	}

	public void encerrarSessao(String login) throws LoginException {
		if (login == null || login.equals(""))
			throw new LoginException("Login inválido");
		sistema.encerrarSessao(login);
	}

	public String getVisaoDosSons(String sessaoId) throws SessaoException,
			NumberFormatException, SomException {
		if (sessaoId == null || sessaoId.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(sessaoId))
			throw new SessaoException("Sessão inexistente");
		List<Integer> listSonsIds = new ArrayList<Integer>();
		for (Som sons : sistema.getVisaoDosSons(Integer.parseInt(sessaoId))) {
			listSonsIds.add(sons.hashCode());
		}
		return Colecaoes.ColecaoParaString(listSonsIds);
	}

	public void favoritarSom(String sessaoId, String idSom)
			throws SessaoException, SomException {
		if (sessaoId == null || sessaoId.equals(""))
			throw new SessaoException("Sessão inválida");
		if (idSom == null || idSom.equals("")) {
			throw new SomException("Som inválido");
		}
		if (!Strings.ehUmNumero(sessaoId))
			throw new SessaoException("Sessão inexistente");
		if (!Strings.ehUmNumero(idSom))
			throw new SomException("Som inválido");
		sistema.favoritarSom(Integer.parseInt(sessaoId),
				Integer.parseInt(idSom));

	}

	public String getSonsFavoritos(String sessaoId) throws SessaoException,
			sistemaEncerradoException {
		if (sessaoId == null || sessaoId.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(sessaoId))
			throw new SessaoException("Sessão inexistente");
		List<Integer> listSonsIds = sistema.getSonsFavoritos(Integer
				.parseInt(sessaoId));

		return Colecaoes.ColecaoParaStringReverso(listSonsIds);
	}

	public String getFeedExtra(String sessaoId) throws SessaoException,
			sistemaEncerradoException {
		if (sessaoId == null || sessaoId.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(sessaoId))
			throw new SessaoException("Sessão inexistente");
		List<Integer> listSonsIds = sistema.getFeedExtra(Integer
				.parseInt(sessaoId));

		return Colecaoes.ColecaoParaStringReverso(listSonsIds);
	}

	public String getFirstCompositionRule(){
		return REGRA1;
	}
	
	public String getSecondCompositionRule(){
		return REGRA2;
	}
	
	public String getThirdCompositionRule(){
		return REGRA3;
	}
	
	public String getMainFeed(String idSessao) throws SessaoException, NumberFormatException, SomException{
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
		return Colecaoes.ColecaoParaString(sistema.getMainFeed(Integer.parseInt(idSessao)));
	}

	public void setMainFeedRule(String idSessao, String regra) throws Exception{
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
		if (!sistema.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		if(regra == null || regra.equals("")) throw new Exception("Regra de composição inválida");
		if(!regra.equals(REGRA1) && !regra.equals(REGRA2) && !regra.equals(REGRA3)) throw new Exception("Regra de composição inexistente");
		OrdenacoesFeedPrincipal ordem = null;
		if(regra.equals(REGRA1)) ordem = OrdenacoesFeedPrincipal.MAIS_RECENTES;
		if(regra.equals(REGRA2)) ordem = OrdenacoesFeedPrincipal.COM_MAIS_FAVORITOS;
		if(regra.equals(REGRA3)) ordem = OrdenacoesFeedPrincipal.DE_QUEM_FAVORITEI_NO_PASSADO;
		sistema.setMainFeedRule(Integer.parseInt(idSessao), ordem);
	}
	
	public void encerrarSistema() {
		sistema.encerrarSistema();
	}
	
	public String getNumFavoritosEmComum(String idSessao, String idUsuario) throws NumberFormatException, SessaoException, UsuarioException{
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		
		if (idUsuario == null || idUsuario.equals(""))
			throw new UsuarioException("Usuário inválido");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
		
		if (!sistema.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		if (!sistema.contemUsuario(Integer.parseInt(idUsuario)))
			throw new UsuarioException("Usuário inexistente");
		
		String retorno = "";
		return retorno + sistema.getNumFavoritosEmComum(Integer.parseInt(idSessao),Integer.parseInt(idUsuario));
	}
	
	public String getNumFontesEmComum(String idSessao, String idUsuario) throws NumberFormatException, SessaoException, UsuarioException{
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (idUsuario == null || idUsuario.equals(""))
			throw new UsuarioException("Usuário inválido");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
	
		if (!sistema.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		if (!sistema.contemUsuario(Integer.parseInt(idUsuario))) throw new UsuarioException("Usuário inexistente");
		
		String retorno = "";
		return retorno + sistema.getNumFontesEmComum(Integer.parseInt(idSessao),Integer.parseInt(idUsuario));
	}
	
	public void adicionarUsuario(String idSessao, String idLista, String idUsuario) throws SessaoException, UsuarioException, ListaException {
		if (idUsuario == null || idUsuario.equals(""))
			throw new UsuarioException("Usuário inválido");
		if (idLista == null || idLista.equals("")) throw new ListaException("Lista inválida");
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
	
		if (!sistema.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		if (!sistema.contemUsuario(Integer.parseInt(idUsuario))) throw new UsuarioException("Usuário inexistente");
		
		sistema.adicionarUsuario(Integer.parseInt(idSessao), Integer.parseInt(idLista), Integer.parseInt(idUsuario));
	}
	
	public String getSonsEmLista(String idSessao, String idLista) throws SessaoException, ListaException {
		if (idLista == null || idLista.equals("")) throw new ListaException("Lista inválida");
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
	
		if (!sistema.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		return Colecaoes.ColecaoParaString(sistema.getSonsEmLista(Integer.parseInt(idSessao), Integer.parseInt(idLista)));
	}
	
	public String criarLista(String idSessao, String nomeDaLista) throws SessaoException, ListaException {
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
		if (!sistema.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		String retorno = "";
		return retorno + sistema.criarLista(nomeDaLista, Integer.parseInt(idSessao));
	}
	
	public String getFontesDeSonsRecomendadas(String idSessao) throws Exception{
		
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");

		if (!sistema.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		List<Integer>retorno = sistema.getFontesDeSonsRecomendadas(Integer.parseInt(idSessao));
		
	
		return Colecaoes.ColecaoParaString(retorno);
		 
	}

	public String criarTag(String idSessao, String tag) throws SessaoException, TagException {
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
		if (!sistema.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		if (tag == null || tag.equals("")) throw new TagException("Tag inválida");
		
		return Integer.toString(sistema.criarTag(Integer.parseInt(idSessao), tag));
	}
	
	public void adicionarTagASom(String idSessao, String tag, String idSom) throws TagException, SessaoException, SomException {
		if (idSom == null || idSom.equals("")) throw new SomException("Som inválido");
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
		if (!sistema.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		if (tag == null || tag.equals("")) throw new TagException("Tag inválida");
		sistema.adicionarTagASom(Integer.parseInt(idSessao), tag, Integer.parseInt(idSom));
	}
	
	public String getListaTagsEmSom(String idSessao, String idSom) throws SomException, SessaoException{
		if (idSom == null || idSom.equals("")) throw new SomException("Som inválido");
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
		if (!sistema.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		List<Integer> retorno = new ArrayList<Integer>();
		for (Tag tag : sistema.getListaTagsEmSom(Integer.parseInt(idSessao), Integer.parseInt(idSom))) {
			retorno.add(tag.hashCode());
		}
		return Colecaoes.ColecaoParaString(retorno);
	}
	
	public String getNomeTag(String idSessao, String idTag) throws SessaoException, TagException{
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
		if (!sistema.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");
		if (idTag == null || idTag.equals("")) throw new TagException("Tag inválida");
		
		return sistema.getNomeTag(Integer.parseInt(idSessao), Integer.parseInt(idTag));
	}

	public String getTagsDisponiveis(String idSessao) throws SessaoException{
		if (idSessao == null || idSessao.equals(""))
			throw new SessaoException("Sessão inválida");
		if (!Strings.ehUmNumero(idSessao))
			throw new SessaoException("Sessão inexistente");
		if (!sistema.contemSessao(idSessao)) throw new SessaoException("Sessão inexistente");		
		return Colecaoes.ColecaoParaString(sistema.getTagsDisponiveis(Integer.parseInt(idSessao)));
	}
	
}