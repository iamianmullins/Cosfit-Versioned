package controllers;

import play.*;
import play.mvc.*;

/**
 * About Controller, simply renders about HTML
 */
public class About extends Controller {
    public static void index() {
        Logger.info("Rendering about");
        render("about.html");
    }
}
