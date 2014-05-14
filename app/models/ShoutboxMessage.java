package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class ShoutboxMessage extends Model {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	@ManyToOne
	public Game game;
	@ManyToOne
	public User user;
	public String message;
	public Date time;
	public TEAM target;

	public ShoutboxMessage(Game game, User user, String message, Date time,
			TEAM target) {
		this.game = game;
		this.user = user;
		this.message = message;
		this.time = time;
		this.target = target;
	}
	
	public static Finder<Long, ShoutboxMessage> find = new Finder<Long, ShoutboxMessage>(
			Long.class, ShoutboxMessage.class);
}
