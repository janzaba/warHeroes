package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class GamePlayers extends Model {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	@ManyToOne
	public Game game;
	@ManyToOne
	public User user;
	public COUNTRY country;

	public GamePlayers(Game game, User user) {
		this.game = game;
		this.user = user;
	}

	public String toJson() {
		return "{\"email\" : \"" + user.email + "\", \"country\" : \""
				+ country + "\"}";
	}
}
