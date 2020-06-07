package controllers;

import models.Measurement;
import models.Member;
import play.Logger;
import play.mvc.Controller;

import java.util.List;
import java.util.Collections;

public class Dashboard extends Controller {
    public static void index() {
        Logger.info("Rendering Dashboard");
        Member member = Accounts.getLoggedInMember();
        List<Measurement> measurementlist = member.measurementlist;
        Collections.reverse(measurementlist);
        double getBMI = GymUtility.getBMI();
        String BMICategory = GymUtility.determineMemberBMICategory();
        String idealBW = GymUtility.isIdealBodyWeight();
        render("dashboard.html", member, measurementlist, getBMI, BMICategory, idealBW);
    }

    public static void addMeasurement(double weightRecord, double chestMeasurement, double abdominalMeasurement, double thighMeasurement,
                                      double waistMeasurement, double upperArmMeasurement, String comment) {
        Member member = Accounts.getLoggedInMember();
        Measurement previousMeasusurement;
        double previousWeight = 0;
        if (member.measurementlist.size() > 0) {
            int index = member.measurementlist.size();
            previousMeasusurement = member.measurementlist.get(index - 1);
            previousWeight = previousMeasusurement.weightRecord;
            member.setMostRecentWeight(previousWeight);
        } else {
            previousWeight = member.getStartingWeight();
        }
        Measurement measurement = new Measurement(previousWeight, weightRecord, chestMeasurement, abdominalMeasurement, thighMeasurement,
                waistMeasurement, upperArmMeasurement, comment);
        member.measurementlist.add(measurement);
        measurement.save();
        member.save();
        Logger.info("Adding Measurement");
        redirect("/dashboard");
    }

    public static void deleteMeasurement(Long id, Long measurementid) {
        Member member = Member.findById(id);
        Measurement measurement = Measurement.findById(measurementid);
        member.measurementlist.remove(measurement);
        member.save();
        measurement.delete();
        Logger.info("Deleting " + measurement.dte);
        redirect("/dashboard");
    }
}
