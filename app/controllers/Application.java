package controllers;

import play.mvc.Result;
import sphere.ShopController;

import java.util.List;

public class Application extends ShopController {

	public static Result test(List<String> names) {
		String name = "";
		for (String s : names) {
			name = name.concat(s);
		}
		return ok(views.html.index.render(name));
	}
}