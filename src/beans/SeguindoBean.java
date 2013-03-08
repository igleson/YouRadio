package beans;

public class SeguindoBean {
	private String nome;
	private String desde;
	
	public SeguindoBean(String nome) {
		this.nome = nome;
		this.desde = "00-00-0000";
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDesde() {
		return desde;
	}
	public void setDesde(String post) {
		this.desde = post;
	}
}