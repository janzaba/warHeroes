package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class User extends Model {
	@Id
	public String login;
	public String password;
	public String name;
	public String email;
	@OneToMany(mappedBy = "owner")
	public List<Game> owningGames;
	@OneToMany(mappedBy = "user")
	public List<GamePlayers> inGames;

	public User(String login, String password, String name, String email) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public static Finder<String, User> find = new Finder<String, User>(
			String.class, User.class);
}
