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
	public String name;
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
		endTime = null;
		name = "";
		winner = null;
	}

	public int getNumberOfPlayers() {
		return gamePlayers.size();
	}

	public String toJson() {
		// {\"id\" : \"1\", \"name\" : \"Game name\", \"owner\" : \"Kozak\",
		// \"players\" : \"1/8\", \"actions\" : \"\"}
		return "{\"id\" : \"" + id.toString() + "\", \"name\" : \"" + name
				+ "\", \"owner\" : \"" + owner.name + "\", \"players\" : \""
				+ this.getNumberOfPlayers() + "/8\", \"actions\" : \"\"}";
	}

	public String playersInJsonForm() {
		String result = "[";
		for (GamePlayers player : gamePlayers) {
			result += player.toJson();
			if (gamePlayers.indexOf(player) != gamePlayers.size() - 1)
				result += ", ";
		}
		result += "]";
		return result;
	}

	public static Finder<Long, Game> find = new Finder<Long, Game>(Long.class,
			Game.class);
}
