package controllers;

import models.User;
import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.*;
import play.mvc.*;
import play.data.*;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.register;
import views.html.game;

public class Application extends Controller {

	public static Result index() {
		String username = session().get("email");
		User loggedUser = null;
		if (username != null)
			loggedUser = User.find.byId(username);
		return ok(index.render(Form.form(Login.class), loggedUser));
	}

	@Security.Authenticated(Secured.class)
	public static Result logout() {
		session().clear();
		flash("success", "Wylogowano pomyślnie.");
		return redirect(routes.Application.index());
	}

	@Security.Authenticated(Secured.class)
	public static Result logged() {
		flash("success", "Zalogowano.");
		String username = request().username();
		User loggedUser = null;
		if (username != null)
			loggedUser = User.find.byId(username);
		return ok(index.render(Form.form(Login.class), loggedUser));
	}

	public static Result register() {
		String username = request().username();
		User loggedUser = null;
		if (username != null)
			loggedUser = User.find.byId(username);
		return ok(register.render(Form.form(Register.class),
				Form.form(Login.class), loggedUser));
	}

	public static Result addNewUser() {
		String username = request().username();
		User loggedUser = null;
		if (username != null)
			loggedUser = User.find.byId(username);
		Form<Register> registerForm = Form.form(Register.class)
				.bindFromRequest();
		if (registerForm.hasErrors()) {
			flash("error", "Proszę poprawić dane.");
			return badRequest(register.render(registerForm,
					Form.form(Login.class), loggedUser));
		}
		Register registerNew = registerForm.get();
		if (User.find.where().eq("email", registerNew.email).findUnique() != null) {
			flash("error", "Podany email istnieje już w bazie.");
			return badRequest(register.render(registerForm,
					Form.form(Login.class), loggedUser));
		}
		if (User.find.where().eq("name", registerNew.name).findUnique() != null) {
			flash("error", "Podana nazwa użytkownika istnieje już w bazie.");
			return badRequest(register.render(registerForm,
					Form.form(Login.class), loggedUser));
		}
		if (!registerNew.password.equals(registerNew.repeatedPassword)) {
			flash("error", "Hasła nie zgadzają się ze sobą.");
			return badRequest(register.render(registerForm,
					Form.form(Login.class), loggedUser));
		}
		models.User newUser = new User(registerNew.email, registerNew.password,
				registerNew.name);
		newUser.save();
		flash("success", "Dodano nowego użytkownika: " + registerNew.name);
		return redirect(routes.Application.index());
	}

	public static class Register {
		public String email;
		public String name;
		public String password;
		public String repeatedPassword;
	}

	public static class Login {
		public String email;
		public String password;
	}

	public static Result login() {
		String username = request().username();
		User loggedUser = null;
		if (username != null)
			loggedUser = User.find.byId(username);
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			session().clear();
			flash("error", "Nieprawidłowe dane.");
			return badRequest(index.render(Form.form(Login.class), loggedUser));
		}
		Login newLoggedUser = loginForm.get();
		if (User.find.where().eq("email", newLoggedUser.email)
				.eq("password", newLoggedUser.password).findUnique() == null) {
			session().clear();
			flash("error", "Zły użytkownik lub hasło");
			return badRequest(index.render(Form.form(Login.class), loggedUser));
		}
		session().clear();
		session("email", newLoggedUser.email);
		return redirect(routes.Application.logged());
	}

}
