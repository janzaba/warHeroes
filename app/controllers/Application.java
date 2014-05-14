package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render(""));
	}

	public static Result newGame() {
		return TODO;
	}

	public static Result register() {
		return TODO;
	}
	
	public static Result login() {
		return TODO;
	}

}
