package controllers;

import java.util.List;

import models.COUNTRY;
import models.Game;
import models.GamePlayers;
import models.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.creategame;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.Application.Login;

@Security.Authenticated(Secured.class)
public class GamesManager extends Controller {

	@BodyParser.Of(BodyParser.Json.class)
	public static Result ajaxGamesList() {
		List<Game> games = Game.find.all();
		String result = "{\"data\": [";
		for (Game game : games) {
			result += game.toJson();
			if (games.indexOf(game) != games.size() - 1)
				result += ", ";
		}
		result += "] }";
		JsonNode jsonResult = Json.newObject();
		jsonResult = Json.parse(result);
		response().setHeader("content-type", "application/json");
		return ok(jsonResult);
	}

	@BodyParser.Of(BodyParser.Json.class)
	public static Result createGame() {
		String username = request().username();
		User loggedUser = null;
		loggedUser = User.find.byId(username);
		Game newGame = new Game(loggedUser);
		newGame.save();
		session("newGameId", newGame.id.toString());
		return ok(creategame.render(Form.form(Login.class), loggedUser,
				newGame.id));
	}

	@BodyParser.Of(BodyParser.Json.class)
	public static Result getGame(Long id) {
		String username = request().username();
		User loggedUser = null;
		loggedUser = User.find.byId(username);
		if (Game.find.where().eq("id", id.toString()).findUnique() != null)
			return ok(creategame.render(Form.form(Login.class), loggedUser, id));
		else {
			flash("error", "Gra o podanym id nie istnieje");
			return ok(index.render(Form.form(Login.class), loggedUser));
		}
	}

	@BodyParser.Of(BodyParser.Json.class)
	public static Result ajaxPlayersList(Long id) {
		Game game = Game.find.byId(id);
		JsonNode jsonResult = Json.newObject();
		System.out.println(game.playersInJsonForm());
		jsonResult = Json.parse(game.playersInJsonForm());
		response().setHeader("content-type", "application/json");
		return ok(jsonResult);
	}

	@BodyParser.Of(BodyParser.Json.class)
	public static Result ajaxAddPlayer(Long id) {
		JsonNode json = request().body().asJson();
		String username = request().username();
		User user = null;
		user = User.find.byId(username);
		Game game = Game.find.byId(id);
		String stringCountry = json.findPath("country").textValue();
		COUNTRY country = COUNTRY.valueOf(stringCountry);
		JsonNode jsonResult = Json.newObject();
		for (GamePlayers player : game.gamePlayers) {
			if (player.country == country) {
				jsonResult = Json.parse("{\"KO\" : \"KO\"}");
				return ok(jsonResult);
			}
		}
		GamePlayers player = new GamePlayers(game, user);
		player.country = country;
		player.save();
		jsonResult = Json.parse("{\"OK\" : \"OK\"}");
		return ok(jsonResult);
	}

}
