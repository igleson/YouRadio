package projeto.user;

public class Tag {
	
	private Usuario dono;
	private Integer id;
	private String nomeDaTag;
	
	public Tag(Usuario dono, String nomeDaTag) {
		this.dono = dono;
		this.nomeDaTag = nomeDaTag;
		id = this.hashCode();
	}
	
	public Usuario getDono() {
		return dono;
	}

	public Integer getId() {
		return id;
	}
	
	public String getNomeDaTag(){
		return nomeDaTag;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dono == null) ? 0 : dono.hashCode());
		result = prime * result + ((nomeDaTag == null) ? 0 : nomeDaTag.hashCode());
		return Math.abs(result);
	}
}
