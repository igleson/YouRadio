package sessao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import excessoes.LoginException;

public class SessaoFacebook implements Sessao {

	private static final String CLIENT_ID = "509574592440511";
	private static final String CLIENT_SECRET = "52986270e0cf4330bf0b173ce03576da";
	private static final String REDIRECT_URI = "htpp://www.google.com";


	@Override
	public int abrirSessao(String login, String senha) throws LoginException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String accessToken(String code) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(
				"https://graph.facebook.com/oauth/access_token");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("client_id", CLIENT_ID));
		nameValuePairs.add(new BasicNameValuePair("client_secret",
				CLIENT_SECRET));
		nameValuePairs
				.add(new BasicNameValuePair("redirect_uri", REDIRECT_URI));
		nameValuePairs.add(new BasicNameValuePair("code", code));

		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) {
			// TODO olhar exceção
		}
		HttpResponse resp;
		try {
			resp = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			String mensagem = "";
			String linha;
			while ((linha = rd.readLine()) != null) {
				mensagem += linha;
			}
			
			return mensagem;
		} catch (ClientProtocolException e) {
			// TODO olhar exceção
		} catch (IOException e) {
			// TODO olhar exceção
		}
		return "";
	}

	@Override
	public String getLogin() {
		// TODO Auto-generated method stub
		return null;
	}

}
