package controllers;

import models.Measurement;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.Collections;
import java.util.List;

public class ViewMember extends Controller {

    /**
     * Finds member by id,
     * removed member from member list
     * saves and deleted member from db
     */
    public static void index(Long id) {
        Logger.info("Rendering View Member");
        Member member = Member.findById(id);
        String BMICategory = GymUtility.determineMemberBMICategory(id);
        String idealBW = isIdealBodyWeight(id);
        double getBMI = getBMI(id);
        List<Measurement> measurementlist = member.measurementlist;
        Collections.reverse(measurementlist);
        render("viewmember.html", member, measurementlist, BMICategory, idealBW, getBMI);
    }

    public static void editComment(Long id, Long measurementid, String comment) {
        Logger.info("Updating Assessment Comment");
        Trainer trainer = Accounts.getLoggedInTrainer();
        String trainerName = trainer.firstname + " " + trainer.lastname;
        Member member = Member.findById(id);
        Measurement measurement = Measurement.findById(measurementid);
        measurement.setComment(trainerName + ":  " + comment);
        member.save();
        measurement.save();
        Logger.info("Editing " + measurement.dte + "Comment: " + comment);
        index(id);
    }


    public static String isIdealBodyWeight(Long id) {
        Member member = Member.findById(id);
        Measurement measurement;
        if (member.measurementlist.size() > 0) {
            measurement = member.measurementlist.get(0);
        } else {
            measurement = null;
        }
        String isIdeal;
        Boolean ideal = GymUtility.isIdealBodyWeight(member, measurement);
        if (ideal) {
            isIdeal = "Ideal";
        } else {
            isIdeal = "Not Ideal";
        }
        Logger.info("Getting BMI Category");
        return isIdeal;
    }

    public static double getBMI(Long id) {
        Member member = Member.findById(id);
        Measurement measurement;
        if (member.measurementlist.size() > 0) {
            measurement = member.measurementlist.get(0);
        } else {
            measurement = null;
        }
        double BMI = GymUtility.calculateBMI(member, measurement);
        Logger.info("Getting BMI");
        return BMI;
    }
}
