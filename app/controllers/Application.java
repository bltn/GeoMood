package controllers;

import play.*;
import play.mvc.*;

import javax.inject.*;

public class Application extends Controller {

    @Inject Configuration conf;

    public Result index() {
        return ok("hello");
    }
}