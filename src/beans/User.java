package beans;

public class User {
	private String nome;
	private String post;
	public User(String nome, String post) {

		this.nome = nome;
		this.post = post;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
}
