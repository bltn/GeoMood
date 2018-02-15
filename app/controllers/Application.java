package controllers;

import play.*;
import play.mvc.*;

import service.NLP;

import javax.inject.*;

import twitter4j.conf.ConfigurationBuilder;
import twitter4j.*;

import java.util.List;
import java.util.ArrayList;

import views.html.*;

public class Application extends Controller {

    @Inject Configuration conf;

    public Result index() {
        return ok("hello");
    }
}