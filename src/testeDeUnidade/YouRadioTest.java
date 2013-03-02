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

import projeto.perfil.Som;
import projeto.sistem.YouRadio;

public class YouRadioTest {

	YouRadio sistema;
	
	Som som02;
	Som som03;
	Som som04;
	Som som05;
	
	
	@Before
	public void setup(){
		sistema = new YouRadio();
		
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
			assertEquals("" +
					"]Já existe um usuário com este email", e.getMessage());
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
		int sessao = sistema.abrirSessao("mark", "MaRk");
		int som = sistema.postarSom(sessao, "musica1", "22/02/2013");
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
	
}
