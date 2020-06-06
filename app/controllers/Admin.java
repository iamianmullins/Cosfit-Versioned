package controllers;

import models.Measurement;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class Admin extends Controller
{
    public static void index()
    {
        Logger.info("Rendering Admin");
        Member member = Accounts.getLoggedInMember();
        Trainer trainer = Accounts.getLoggedInTrainer();
        List<Measurement> measurementlist=null;
        List<Member> memberList=null;
        if (member!=null) {
            measurementlist = Measurement.findAll();
        }
        else if (trainer!=null) {
            memberList = Member.findAll();
        }
        render("admin.html", memberList, measurementlist);
    }
}
