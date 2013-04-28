package sessao;

import excessoes.LoginException;
import gerenciadorDeDados.DadosDoSistema;

public class SessaoNormal implements Sessao{
	
	private static final long serialVersionUID = 1L;
	private DadosDoSistema dados;
	private String login;

	public SessaoNormal(){
		this.dados = DadosDoSistema.getInstance();
	}
	
	@Override
	public int abrirSessao(String login, String senha) throws LoginException{
		if (login == null || login.equals(""))
			throw new LoginException("Login inválido");
		if (senha == null || senha.equals(""))
			throw new LoginException("Senha inválida");
		if (!this.dados.contemUsuario(login))
			throw new LoginException("Usuário inexistente");
		if (this.dados.senhaValida(login, senha)){
			this.login = login;
			this.dados.adicionaSessao(this);
		}
		else throw new LoginException("Login inválido");
		return this.hashCode();
	}

	@Override
	public String getLogin() {
		return login;
	}

	@Override
	public int hashCode() {
		if(login == null) return 0;
		else return login.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SessaoNormal)) return false;
		else{
			return this.hashCode() == ((SessaoNormal)obj).hashCode();
		}
	}

	
}
