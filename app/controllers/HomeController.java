package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.home;

public class HomeController extends Controller {

    public Result renderHomePage() {
        return ok(home.render());
    }
}
