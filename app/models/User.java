package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class User extends Model {
	@Id
	public String email;
	public String name;
	public String password;
	@OneToMany(mappedBy = "owner")
	public List<Game> owningGames;
	@OneToMany(mappedBy = "user")
	public List<GamePlayers> inGames;

	public User(String email, String password, String name) {
		this.email = email;
		this.name = name;
		this.password = password;
	}

	public static User authenticate(String login, String password) {
		return find.where().eq("login", login).eq("password", password).findUnique();
	}

	public static Finder<String, User> find = new Finder<String, User>(
			String.class, User.class);
}
