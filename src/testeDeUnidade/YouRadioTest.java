package testeDeUnidade;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import excessoes.CadastroException;
import excessoes.LoginException;
import excessoes.SessaoException;
import excessoes.SomException;
import excessoes.UsuarioException;
import excessoes.sistemaEncerradoException;
import gerenciadorDeDados.DadosDoSistema;

import projeto.perfil.Som;
import projeto.sistem.YouRadio;

public class YouRadioTest {

	YouRadio sistema;
	
	@Before
	public void setup(){
		sistema = new YouRadio();
		sistema.zerarSistema();
	}
	
	@Test
	public void testaCriarUsuario() throws UsuarioException, CadastroException, sistemaEncerradoException {
		assertEquals(0, sistema.qtdeUsuarios());
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		assertEquals(1, sistema.qtdeUsuarios());
		sistema.criarUsuario("steve", "StEvE", "Steve", "steve@apple.com");
		assertEquals(2, sistema.qtdeUsuarios());
	}

	@Test
	public void testaCriarUsuarioMesmoLogin() throws UsuarioException, CadastroException, sistemaEncerradoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		try {
			sistema.criarUsuario("mark", "MaRk", "Mark", "mark@facess.com");
		} catch (UsuarioException e) {
			fail("Exceção errada");
		} catch (CadastroException e) {
			assertEquals("Já existe um usuário com este login", e.getMessage());
		}
		sistema.criarUsuario("steve", "StEvE", "Steve", "steve@apple.com");
		try {
			sistema.criarUsuario("steve", "StEvE", "Steve", "steve@applessss.com");
		} catch (UsuarioException e) {
			fail("Exceção errada");
		} catch (CadastroException e) {
			assertEquals("Já existe um usuário com este login", e.getMessage());
		}
	}
	
	@Test
	public void testaCriarUsuarioMesmoEmail() throws UsuarioException, CadastroException, sistemaEncerradoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		try {
			sistema.criarUsuario("mark2", "MaRk", "Mark", "mark@face.com");
		} catch (UsuarioException e) {
			fail("Exceção errada");
		} catch (CadastroException e) {
			assertEquals("Já existe um usuário com este email", e.getMessage());
		}
		sistema.criarUsuario("steve", "StEvE", "Steve", "steve@apple.com");
		try {
			sistema.criarUsuario("steve2", "StEvE", "Steve", "steve@apple.com");
		} catch (UsuarioException e) {
			fail("Exceção errada");
		} catch (CadastroException e) {
			assertEquals("Já existe um usuário com este email", e.getMessage());
		}
	}
	
	@Test
	public void testUsuarioComLoginInvalido() throws CadastroException, UsuarioException, sistemaEncerradoException{
		try{
			sistema.criarUsuario(null, "MaRk", "Mark", "mark@face.com");
		}catch (UsuarioException e) {
			assertEquals("Login inválido", e.getMessage());
		}
		
		try{
			sistema.criarUsuario("", "MaRk", "Mark", "mark@face.com");
		}catch (UsuarioException e) {
			assertEquals("Login inválido", e.getMessage());
		}
	}
	
	
	@Test
	public void testUsuarioComSenhaInvalida() throws CadastroException, UsuarioException, sistemaEncerradoException{
		try{
			sistema.criarUsuario("mark", null, "Mark", "mark@face.com");
		}catch (UsuarioException e) {
			assertEquals("Senha inválida", e.getMessage());
		}
		
		try{
			sistema.criarUsuario("mark", "", "Mark", "mark@face.com");
		}catch (UsuarioException e) {
			assertEquals("Senha inválida", e.getMessage());
		}
	}
	
	@Test
	public void testUsuarioComNomeInvalido() throws CadastroException, UsuarioException, sistemaEncerradoException{
		try{
			sistema.criarUsuario("mark", "maRk", null, "mark@face.com");
		}catch (UsuarioException e) {
			assertEquals("Nome inválido", e.getMessage());
		}
		
		try{
			sistema.criarUsuario("mark", "maRk", "", "mark@face.com");
		}catch (UsuarioException e) {
			assertEquals("Nome inválido", e.getMessage());
		}
	}
	
	@Test
	public void testUsuarioComEmailInvalido() throws CadastroException, UsuarioException, sistemaEncerradoException{
		try{
			sistema.criarUsuario("mark", "maRk", "Mark", null);
		}catch (UsuarioException e) {
			assertEquals("Email inválido", e.getMessage());
		}
		
		try{
			sistema.criarUsuario("mark", "maRk", "Mark", "");
		}catch (UsuarioException e) {
			assertEquals("Email inválido", e.getMessage());
		}
	}
	
	
	@Test
	public void testAbrirSessaoLoginInvalido() throws sistemaEncerradoException{
		try{
			sistema.abrirSessao(null, "MaRk");
		}catch (LoginException e) {
			assertEquals("Login inválido", e.getMessage());
		}
		try{
			sistema.abrirSessao("", "MaRk");
		}catch (LoginException e) {
			assertEquals("Login inválido", e.getMessage());
		}
	}
	
	@Test 
	public void testAbrirSessaoSenhaInvalida() throws sistemaEncerradoException{
		try{
			sistema.abrirSessao("mark", null);
		}catch (LoginException e) {
			assertEquals("Senha inválida", e.getMessage());
		}
		try{
			sistema.abrirSessao("mark", "");
		}catch (LoginException e) {
			assertEquals("Senha inválida", e.getMessage());
		}
	}
	
	@Test
	public void testAbrirSessaoUsuarioInexistente() throws sistemaEncerradoException{
		try{
			sistema.abrirSessao("mark", "MaRk");
		}catch (LoginException e) {
			assertEquals("Usuário inexistente", e.getMessage());
		}
	}
	
	@Test
	public void testAbrirSessão() throws CadastroException, UsuarioException, LoginException, sistemaEncerradoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		
		assertTrue(sistema.sessaoAberta(sistema.abrirSessao("mark", "MaRk")));
	}
	
	@Test
	public void testPostarSomEGetPerfilMusicalSessaoInexistente() throws sistemaEncerradoException{
		int idSessao = 0;
		try{
			sistema.postarSom(idSessao, "musica1");
			}catch(SessaoException e){
				assertEquals("Sessao inexistente", e.getMessage());
		} catch (SomException e) {
				fail();
			}
	}
	
	@Test
	public void testPostarSomEGetPerfilMusical() throws SomException, CadastroException, UsuarioException, LoginException, SessaoException, sistemaEncerradoException{
		int idSessao = 0;
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		
		idSessao = sistema.abrirSessao("mark", "MaRk");
		assertEquals(sistema.getPerfilMusical(idSessao).size(),0);
		sistema.postarSom(idSessao, "musica1");
		assertEquals(sistema.getPerfilMusical(idSessao).size(),1);
	}
	
	@Test
	public void testNomeDoUsuarioLoginInvalido() throws sistemaEncerradoException{
		String loginTemp = null;
		try{
		sistema.nomeDoUsuario(loginTemp);
		}catch(UsuarioException e){
			assertEquals("Login inválido", e.getMessage());
		}
		try{
		sistema.nomeDoUsuario(loginTemp);
		}catch(UsuarioException e){
			assertEquals("Login inválido", e.getMessage());
		}
	}

	@Test
	public void testNomeDoUsuarioUsuarioInexistente() throws sistemaEncerradoException{
		String loginTemp = null;
		
		loginTemp= "steve";
		try{
			sistema.nomeDoUsuario(loginTemp);
		}catch(UsuarioException e){
			assertEquals("Usuário inexistente", e.getMessage());
		}
	}
	
	@Test
	public void testNomeDoUsuario() throws UsuarioException, CadastroException, sistemaEncerradoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		
		assertEquals("Mark", sistema.nomeDoUsuario("mark"));
	}
	
	@Test
	public void testEmailDoUsuarioLoginInvalido() throws sistemaEncerradoException{
		String loginTemp = null;
		
		try{
		sistema.emailDoUsuario(loginTemp);
		}catch(UsuarioException e){
			assertEquals("Login inválido", e.getMessage());
		}
		loginTemp = "";
		try{
		sistema.emailDoUsuario(loginTemp);
		}catch(UsuarioException e){
			assertEquals("Login inválido", e.getMessage());
		}
	}
	
	@Test
	public void testEmailDoUsuarioUsuarioInexistente() throws sistemaEncerradoException{
		String loginTemp= "steve";
		try{
			sistema.emailDoUsuario(loginTemp);
		}catch(UsuarioException e){
			assertEquals("Usuário inexistente", e.getMessage());
		}
	}
	
	@Test
	public void testEmailDoUsuario() throws UsuarioException, CadastroException, sistemaEncerradoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		
		assertEquals("mark@face.com", sistema.emailDoUsuario("mark"));
	}

	@Test
	public void testLinkDoSomSomInexistente() throws sistemaEncerradoException{
		try {
			sistema.linkDoSom(-4564);
		} catch (SomException e) {
			assertEquals("Som inexistente", e.getMessage());
		}
	}
	
	@Test
	public void testLinkDoSom() throws SomException, CadastroException, UsuarioException, SessaoException, LoginException, sistemaEncerradoException{
		int linkTemp;
		
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		linkTemp = sistema.postarSom(sistema.abrirSessao("mark", "MaRk"), "musica1");
		assertEquals("musica1", sistema.linkDoSom(linkTemp));
	}
	
	@Test
	public void testDataDeCriacaoSom() throws CadastroException, UsuarioException, LoginException, SomException, SessaoException, sistemaEncerradoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		int sessao = sistema.abrirSessao("mark", "MaRk");
		int som = sistema.postarSom(sessao, "musica1", "22/02/2013");
		assertEquals("22/02/2013", sistema.dataDeCriacaoSom(som));
	}
	
	@Test
	public void testDataDeCriacaoSomSomInexistente() throws CadastroException, UsuarioException, SomException, SessaoException, LoginException, sistemaEncerradoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		try {
			sistema.dataDeCriacaoSom(-51615321);
		} catch (SomException e) {
			assertEquals("Som inexistente", e.getMessage());
		}
	}
	
	@Test
	public void testEncerrarSessao() throws CadastroException, UsuarioException, LoginException, sistemaEncerradoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		int sessao = sistema.abrirSessao("mark", "MaRk");
		sistema.encerrarSessao("mark");
		assertFalse(sistema.sessaoAberta(sessao));
	}
	
	@Test
	public void testEncerrarSistema() throws CadastroException, UsuarioException, sistemaEncerradoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		sistema.encerrarSistema();
		try{
			sistema.abrirSessao("a", "abcdefghijk");
		}catch (sistemaEncerradoException e) {
			assertEquals("sistema encerrado", e.getMessage());
		}catch (Exception e){
			fail();
		}
	}
	
	@Test
	public void testGetIdUsuarioSessaoInexistente() throws SessaoException{
		try{
			sistema.getIdUsuario(123456);
		}catch(SessaoException e){
			assertEquals("Sessão inexistente", e.getMessage());
		}	
	}
	
	@Test
	public void testGetIdUsuario() throws SessaoException, LoginException, sistemaEncerradoException, CadastroException, UsuarioException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		assertEquals(Integer.toString(sistema.usuario("mark").hashCode()), sistema.getIdUsuario(sistema.abrirSessao("mark", "MaRk")));
	}
	
	@Test
	public void testGetFonteDeSonsSessaoInexistente(){
		try{
			sistema.getFontesDeSons(123456);
		}catch(SessaoException e){
			assertEquals("Sessão inexistente", e.getMessage());
		}
	}
	
	@Test
	public void testGetFontesDeSons() throws CadastroException, UsuarioException, sistemaEncerradoException, SessaoException, LoginException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		sistema.criarUsuario("steve", "StEvE", "Steve", "steve@apple.com");
		
		int idSessao = sistema.abrirSessao("mark", "MaRk");
		assertEquals(0,sistema.getFontesDeSons(idSessao).size());
		sistema.seguirUsuario(idSessao, "steve");
		
		assertEquals(1,sistema.getFontesDeSons(idSessao).size());
	}
	
	@Test
	public void testGetListaDeSeguidoresSessaoInexistente(){
		try{
			sistema.getListaDeSeguidores(123456);
		}catch(SessaoException e){
			assertEquals("Sessão inexistente", e.getMessage());
		}
	}
	
	@Test
	public void testGetListaDeSeguidores() throws CadastroException, UsuarioException, sistemaEncerradoException, LoginException, SessaoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		sistema.criarUsuario("steve", "StEvE", "Steve", "steve@apple.com");
		
		int idSessao = sistema.abrirSessao("mark", "MaRk");
		assertEquals(0,sistema.getListaDeSeguidores(idSessao).size());
		sistema.seguirUsuario(idSessao, "steve");
		
		assertEquals(1,sistema.usuario("steve").getListaDeSeguidores().size());
	}
	
	@Test
	public void testGetNumeroDeSeguidoresSessaoInexistente(){
		try{
			sistema.getNumeroDeSeguidores(123456);
		}catch(SessaoException e){
			assertEquals("Sessão inexistente", e.getMessage());
		}
	}
	
	@Test
	public void testGetNumeroDeSeguidores() throws CadastroException, UsuarioException, sistemaEncerradoException, LoginException, SessaoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		sistema.criarUsuario("steve", "StEvE", "Steve", "steve@apple.com");
		
		int idSessao = sistema.abrirSessao("mark", "MaRk");
		assertEquals(0,sistema.getNumeroDeSeguidores(idSessao));
		sistema.seguirUsuario(idSessao, "steve");
		
		assertEquals(1,sistema.usuario("steve").getNumeroDeSeguidores());
	}
	
	@Test
	public void testUsuarioIdSessao() throws CadastroException, UsuarioException, sistemaEncerradoException, LoginException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		sistema.criarUsuario("steve", "StEvE", "Steve", "steve@apple.com");
		int idSessao = sistema.abrirSessao("mark", "MaRk");
		assertEquals("mark@face.com", sistema.usuario(idSessao).getEmail());
		int idSessao2 = sistema.abrirSessao("steve", "StEvE");
		assertEquals("steve@apple.com", sistema.usuario(idSessao2).getEmail());
	}
	
	@Test
	public void testUsuarioLogin() throws CadastroException, UsuarioException, sistemaEncerradoException, LoginException, SessaoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		sistema.criarUsuario("steve", "StEvE", "Steve", "steve@apple.com");
		assertEquals("mark@face.com", sistema.usuario("mark").getEmail());
		assertEquals("steve@apple.com", sistema.usuario("steve").getEmail());
	}
	
	
	@Test
	public void testEncerraSessao() throws CadastroException, UsuarioException, sistemaEncerradoException, LoginException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		int idSessao = sistema.abrirSessao("mark", "MaRk");
		assertTrue(sistema.sessaoAberta(idSessao));
		sistema.encerrarSessao("mark");
		assertTrue(!sistema.sessaoAberta(idSessao));
	}
	
	@Test
	public void testSessaoAberta() throws CadastroException, UsuarioException, sistemaEncerradoException, LoginException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		int idSessao = 0;
		assertFalse(sistema.sessaoAberta(idSessao));
		idSessao = sistema.abrirSessao("mark", "MaRk");
		assertTrue(sistema.sessaoAberta(idSessao));
	}
	
	@Test
	public void testSeguirUsuarioLoginInvalido() throws CadastroException, UsuarioException, sistemaEncerradoException,  SessaoException, LoginException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		sistema.criarUsuario("steve", "StEvE", "Steve", "steve@apple.com");
		int idSessao = sistema.abrirSessao("mark", "MaRk");
		
		try{
			sistema.seguirUsuario(idSessao, "mark");
		}catch(LoginException e){
			assertEquals("Login inválido", e.getMessage());
		}
		
	}
	
	@Test
	public void testSeguirUsuarioLoginInexistente() throws CadastroException, UsuarioException, sistemaEncerradoException,  SessaoException, LoginException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		sistema.criarUsuario("steve", "StEvE", "Steve", "steve@apple.com");
		int idSessao = sistema.abrirSessao("mark", "MaRk");
		
		try{
			sistema.seguirUsuario(idSessao, "jose");
		}catch(LoginException e){
			assertEquals("Login inexistente", e.getMessage());
		}
	}
	
	@Test
	public void testSeguirUsuario() throws CadastroException, UsuarioException, sistemaEncerradoException, LoginException, SessaoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		sistema.criarUsuario("steve", "StEvE", "Steve", "steve@apple.com");
		int idSessao = sistema.abrirSessao("mark", "MaRk");
		assertEquals(0, sistema.usuario("mark").getListaDeSeguindo().size());
		sistema.seguirUsuario(idSessao, "steve");
		assertEquals(1, sistema.usuario("mark").getListaDeSeguindo().size());
	}
	
	@Test
	public void testSeguirUsuarioSessaoInexistente() throws CadastroException, UsuarioException, sistemaEncerradoException,  SessaoException, LoginException{
		try{
			sistema.seguirUsuario(1237, "jose");
		}catch(SessaoException e){
			assertEquals("Sessão inexistente", e.getMessage());
		}
	}
	
	@Test
	public void testGetVisaoDeSonsSessaoInexistente() throws SomException{
		try{
			sistema.getVisaoDosSons(123456);
		}catch(SessaoException e){
			assertEquals("Sessão inexistente", e.getMessage());
		}
	}
	
	@Test
	public void testGetVisaoDosSons() throws CadastroException, UsuarioException, sistemaEncerradoException, SessaoException, LoginException, SomException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		sistema.criarUsuario("steve", "StEvE", "Steve", "steve@apple.com");		
		int idSessao = sistema.abrirSessao("mark", "MaRk");
		sistema.postarSom(idSessao, "musica1");
		sistema.postarSom(idSessao, "musica2");
		sistema.postarSom(idSessao, "musica3");
		int idSessao2 = sistema.abrirSessao("steve", "StEvE");
		assertEquals(0,sistema.getVisaoDosSons(idSessao2).size());
		sistema.seguirUsuario(idSessao2, "mark");
		assertEquals(3,sistema.getVisaoDosSons(idSessao2).size());
	}
	
	
	@Test
	public void testFavoritarSomSessaoInexistente() throws SomException{
		try{
			sistema.favoritarSom(123456,9876);
		}catch(SessaoException e){
			assertEquals("Sessão inexistente", e.getMessage());
		}
	}
	
	@Test
	public void testFavoritarSomComSomInexistente() throws SomException, CadastroException, UsuarioException, sistemaEncerradoException, LoginException{
		
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		int idSessao = sistema.abrirSessao("mark", "MaRk");
		try{
			sistema.favoritarSom(idSessao,9876);
		}catch(SessaoException e){
			fail();
		}catch(SomException e){
			assertEquals("Som inexistente", e.getMessage());
		}
	}
	
	@Test
	public void testFavoritarSom() throws SomException, CadastroException, UsuarioException, sistemaEncerradoException, LoginException, SessaoException{
		
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		int idSessao = sistema.abrirSessao("mark", "MaRk");
		int idSom = sistema.postarSom(idSessao, "musica1");
		sistema.favoritarSom(idSessao,idSom);
		assertEquals(idSom,sistema.usuario("mark").getSonsFavoritos().get(0).hashCode());
		
	}
	
	@Test
	public void testGetSonsFavoritos() throws SomException, CadastroException, UsuarioException, sistemaEncerradoException, LoginException, SessaoException{
		
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		int idSessao = sistema.abrirSessao("mark", "MaRk");
		int idSom = sistema.postarSom(idSessao, "musica1");
		sistema.favoritarSom(idSessao,idSom);
		assertEquals(1,sistema.usuario("mark").getSonsFavoritos().size());
		
	}
	
	
	@Test
	public void testGetNumFavoritosEmComum() throws CadastroException, UsuarioException, sistemaEncerradoException, LoginException, SomException, SessaoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		sistema.criarUsuario("steve", "StEvE", "Steve", "steve@apple.com");		
		
		int idSessao = sistema.abrirSessao("mark", "MaRk");
		int som1 = sistema.postarSom(idSessao, "musica1");
		int som2 = sistema.postarSom(idSessao, "musica2");
		int som3 = sistema.postarSom(idSessao, "musica3");
		
		int idSessao2 = sistema.abrirSessao("steve", "StEvE");
		int som4 = sistema.postarSom(idSessao2, "musica4");
		int som5 = sistema.postarSom(idSessao2, "musica5");
		int som6 = sistema.postarSom(idSessao2, "musica6");
		
		
		
		int idUsuario1 = Integer.parseInt(sistema.getIdUsuario(idSessao));
		int idUsuario2 = Integer.parseInt(sistema.getIdUsuario(idSessao2));
		
		sistema.favoritarSom(idSessao, som1);
		sistema.favoritarSom(idSessao, som2);
		sistema.favoritarSom(idSessao, som5);
		
		sistema.favoritarSom(idSessao2, som4);
		sistema.favoritarSom(idSessao2, som6);
		
		assertEquals(0,sistema.getNumFavoritosEmComum(idSessao, idUsuario2));
		
		sistema.favoritarSom(idSessao2, som1);
		sistema.favoritarSom(idSessao2, som2);
		
		assertEquals(2,sistema.getNumFavoritosEmComum(idSessao, idUsuario2));
		assertEquals(2,sistema.getNumFavoritosEmComum(idSessao2, idUsuario1));
		
		
	}
	
	@Test
	public void testGetNumFontesEmComum() throws CadastroException, UsuarioException, sistemaEncerradoException, LoginException, SomException, SessaoException{
		sistema.criarUsuario("mark", "MaRk", "Mark", "mark@face.com");
		sistema.criarUsuario("steve", "StEvE", "Steve", "steve@apple.com");	
		sistema.criarUsuario("raiff", "gruffs", "Raiff", "bo");
		sistema.criarUsuario("igls", "iglfreire", "Iglesson", "bi");
		sistema.criarUsuario("fagner", "bifah", "Fagner", "be");
		sistema.criarUsuario("leo", "lheo", "Leonardo", "ba");
		
		int idSessao = sistema.abrirSessao("mark", "MaRk");
		int idSessao2 = sistema.abrirSessao("steve", "StEvE");
		
		sistema.seguirUsuario(idSessao, "raiff");
		sistema.seguirUsuario(idSessao, "fagner");
		sistema.seguirUsuario(idSessao2, "leo");
		
		int idUsuario1 = Integer.parseInt(sistema.getIdUsuario(idSessao));
		int idUsuario2 = Integer.parseInt(sistema.getIdUsuario(idSessao2));
		
		assertEquals(0,sistema.getNumFontesEmComum(idSessao, idUsuario2));
		
		
		sistema.seguirUsuario(idSessao2, "raiff");
		sistema.seguirUsuario(idSessao2, "fagner");
		assertEquals(2,sistema.getNumFontesEmComum(idSessao,idUsuario2));
		assertEquals(2,sistema.getNumFontesEmComum(idSessao2, idUsuario1));
		
		
	}
	
	
	
	
	
}
