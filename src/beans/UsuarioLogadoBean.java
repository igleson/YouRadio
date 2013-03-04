package beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class UsuarioLogadoBean implements Serializable {

	private static final long serialVersionUID = 5600369132889054255L;
	
	private String nome;

    private List<String> selectedMovies;  
  
    private List<String> selectedOptions;  
  
    private Map<String,String> movies;  

    public UsuarioLogadoBean(){
    	 movies = new HashMap<String, String>();  
         movies.put("Scarface", "Scarface");  
         movies.put("Goodfellas", "Goodfellas");  
         movies.put("Godfather", "Godfather");  
         movies.put("Carlito's Way", "Carlito's Way");  
    }
    public List<String> getSelectedMovies() {  
        return selectedMovies;  
    }  
    public void setSelectedMovies(List<String> selectedMovies) {  
        this.selectedMovies = selectedMovies;  
    }  
  
    public List<String> getSelectedOptions() {  
        return selectedOptions;  
    }  
    public void setSelectedOptions(List<String> selectedOptions) {  
        this.selectedOptions = selectedOptions;  
    }  
  
    public Map<String, String> getMovies() {  
        return movies;  
    }  
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
