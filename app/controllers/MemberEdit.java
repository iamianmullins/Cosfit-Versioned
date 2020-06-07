package controllers;


import models.Measurement;
import models.Member;
import play.Logger;
import play.mvc.Controller;

import java.util.Collections;
import java.util.List;

/**
 * Renders Member dashboard edit details form
 */
public class MemberEdit extends Controller {
    public static void index() {
        Logger.info("Rendering MemberEdit");
        Member member = Accounts.getLoggedInMember();
        List<Measurement> measurementlist = member.measurementlist;
        Collections.reverse(measurementlist);
        Measurement measurement;
        try {
            measurement = measurementlist.get(0);
        } catch (Exception e) {
            measurement = null;
        }
        render("memberedit.html", member, measurement);
    }

    /**
     * Member dashboard edit details form
     */
    public static void editDetails(String firstname, String lastname, String address,
                                   int phone, String password) {
        Member member = Accounts.getLoggedInMember();
        Logger.info("Updating details for user " + member.email);
        member.setFirstname(firstname);
        member.setLastname(lastname);
        member.setAddress(address);
        member.setPhone(phone);
        member.setPassword(password);
        member.save();
        render("memberedit.html", member);
    }
}