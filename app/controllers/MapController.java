package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.map_visualisation;

public class MapController extends Controller {
    public Result renderMapVisualisation() { return ok(map_visualisation.render()); }
}
