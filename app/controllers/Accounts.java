/**
 * Accounts Controller, facilitates session functionality
 * validates login and current user information
 */

package controllers;

import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.Date;

public class Accounts extends Controller {
    public static void signup() {
        render("signup.html");
    }

    public static void login() {
        render("login.html");
    }

    /**
     * New member registration
     * Parameters passed from signup.html form
     */
    public static void register(String firstname, String lastname, String address, Date dateOfBirth, String gender,
                                double height, double startingWeight, int phone, String email, String password) {
        Logger.info("Registering new user " + email);
        double mostRecentWeight = startingWeight;
        Member member = new Member(firstname, lastname, address, dateOfBirth, gender, height, startingWeight, mostRecentWeight, phone, email, password);
        member.save();
        redirect("/");
    }

    /**
     * New member registration
     * Only accessed through admin menu
     */
    public static void registerTrainer(String firstname, String lastname, String email, String password) {
        Logger.info("Registering new trainer " + email);
        Trainer trainer = new Trainer(firstname, lastname, email, password);
        trainer.save();
        redirect("/admin");
    }

    /**
     * Login authentication
     * Parameters passed from login.html form
     */
    public static void authenticate(String email, String password) {
        Logger.info("Attempting to authenticate with " + email + ":" + password);
        if(email.equalsIgnoreCase("admin")&&(password.equalsIgnoreCase("secret"))){
            Admin.index();
        }

        Member member = Member.findByEmail(email);
        if ((member != null) && (member.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Memberid", member.id);
            redirect("/dashboard");
        }
        Trainer trainer = Trainer.findByEmail(email);
        if ((trainer != null) && (trainer.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Trainerid", trainer.id);
            redirect("/trainerdashboard");
        } else {
            Logger.info("Authentication failed");
            redirect("/login");
        }
    }


    /**
     * Logout method
     * Clears current session and redirects to start
     */
    public static void logout() {
        session.clear();
        redirect("/");
    }

    /**
     * Returns current logged in member,
     * retrieves from current session
     * Returns Member
     */
    public static Member getLoggedInMember() {
        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
        } else {
            login();
        }
        return member;
    }

    /**
     * Returns current logged in trainer,
     * retrieves from current session
     * Returns Trainer
     */
    public static Trainer getLoggedInTrainer() {
        Trainer trainer = null;
        if (session.contains("logged_in_Trainerid")) {
            String trainerId = session.get("logged_in_Trainerid");
            trainer = Trainer.findById(Long.parseLong(trainerId));
        } else {
            login();
        }
        return trainer;
    }
}