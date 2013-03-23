package sessao;

import excessoes.LoginException;

public interface Sessao {

	int abrirSessao(String login, String senha) throws LoginException;

	String getLogin();

}
