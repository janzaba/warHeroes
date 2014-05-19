package controllers;

import java.util.List;

import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import models.Game;

import com.fasterxml.jackson.databind.JsonNode;

@Security.Authenticated(Secured.class)
public class GamesManager extends Controller {

	@BodyParser.Of(BodyParser.Json.class)
	public static Result ajaxGamesList() {
		List<Game> games = Game.find.all();
		String result="{\"data\": [";
		for(Game game : games) {
			result+=game.toJson();
			if (games.indexOf(game)!=games.size()-1)
				result+=", ";
		}
		result+="] }";
		JsonNode jsonResult = Json.newObject();
		jsonResult = Json.parse(result);
		response().setHeader("content-type", "application/json");
		return ok(jsonResult);
	}

}
