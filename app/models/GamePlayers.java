package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class GamePlayers extends Model {
	@ManyToOne
	public Game game;
	@ManyToOne
	public User user;

	public GamePlayers(Game game, User user) {
		this.game = game;
		this.user = user;
	}
}
