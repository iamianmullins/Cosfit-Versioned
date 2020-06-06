package controllers;

import models.Measurement;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.Date;
import java.util.List;

public class Admin extends Controller {
    public static void index() {
        Logger.info("Rendering Admin");
        Trainer trainer = Accounts.getLoggedInTrainer();
        List<Member> memberList = null;
        List<Trainer> trainerList = null;
        if (trainer != null) {
            memberList = Member.findAll();
            trainerList = Trainer.findAll();
        }
        render("admin.html", memberList, trainerList);
    }

    public static void registerTrainer(String firstname, String lastname, String email, String password) {
        Logger.info("Registering new trainer " + email);
        Trainer trainer = new Trainer(firstname, lastname, email, password);
        trainer.save();
        redirect("/admin");
    }
}
