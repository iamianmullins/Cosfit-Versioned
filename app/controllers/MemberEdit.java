package controllers;
import controllers.Accounts;
import models.Measurement;
import models.Member;
import play.Logger;
import play.mvc.Controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MemberEdit extends Controller {
    public static void index() {
        Logger.info("Rendering MemberEdit");
        Member member = Accounts.getLoggedInMember();
        List<Measurement> measurementlist = member.measurementlist;
        Collections.reverse(measurementlist);
        Measurement measurement;
        try {
            measurement = measurementlist.get(0);
        }
        catch (Exception e){
            measurement =null;
        }
        render("memberedit.html", member, measurement);
    }

    public static void editDetails(String firstname, String lastname,
                                   int phone, String password) {
        Member member = Accounts.getLoggedInMember();
        Logger.info("Updating deails for user " + member.email);
        member.setFirstname(firstname);
        member.setLastname(lastname);
        member.setPhone(phone);
        member.setPassword(password);
        member.save();
        render("memberedit.html", member);
    }
}