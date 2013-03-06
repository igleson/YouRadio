
package beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
 @ManagedBean
 @SessionScoped
public class TableBean{
	private List<User> carsSmall;
	private User user;
	
	
	public TableBean(){
		setCarsSmall(new ArrayList<User>());
		carsSmall.add(new User("Raiff","som 1"));
		carsSmall.add(new User("Igleson","som 2"));
		carsSmall.add(new User("Leo","som 3"));
		user = new User("Fagner","");
	}


	public List<User> getCarsSmall() {
		return carsSmall;
	}


	public void setCarsSmall(List<User> carsSmall) {
		this.carsSmall = carsSmall;
	}
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user = user;
	}
}
