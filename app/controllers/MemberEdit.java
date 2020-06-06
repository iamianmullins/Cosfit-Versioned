package controllers;
import controllers.Accounts;
import models.Member;
import play.Logger;
import play.mvc.Controller;

import java.util.Date;

public class MemberEdit extends Controller {
    public static void index() {
        Logger.info("Rendering MemberEdit");
        Member member = Accounts.getLoggedInMember();
        render("memberedit.html", member);
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