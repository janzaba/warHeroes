package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Game extends Model {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	public Date startTime;
	public Date endTime;
	@ManyToOne
	public User owner;
	@ManyToOne
	public User winner;
	@OneToMany(mappedBy = "game")
	public List<GamePlayers> gamePlayers;
	@OneToMany(mappedBy = "game")
	public List<ShoutboxMessage> messages;

	public Game(User owner) {
		this.owner = owner;
		startTime = new Date();
	}

	public static Finder<Long, Game> find = new Finder<Long, Game>(Long.class,
			Game.class);
}
