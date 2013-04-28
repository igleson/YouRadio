package sessao;

import java.io.Serializable;

import excessoes.LoginException;

public interface Sessao extends Serializable {

	int abrirSessao(String login, String senha) throws LoginException;

	String getLogin();

}
