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
    double getBMI = getBMI();
    String BMICategory = determineBMICategory();
    String idealBW = isIdealBodyWeight();
    render("dashboard.html", member, measurementlist, getBMI, BMICategory, idealBW);
  }

  public static void addMeasurement(double weightRecord,double height, double chestMeasurement, double abdominalMeasurement, double thighMeasurement,
                                    double waistMeasurement, double upperArmMeasurement, String comment) {
    Member member = Accounts.getLoggedInMember();
    Measurement measurement = new Measurement(weightRecord, height, chestMeasurement, abdominalMeasurement, thighMeasurement,
            waistMeasurement,upperArmMeasurement ,comment);
    member.measurementlist.add(measurement);
    measurement.save();
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

  public static double getBMI() {
    Member member = Accounts.getLoggedInMember();
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

  public static String determineBMICategory() {
    Member member = Accounts.getLoggedInMember();
    Measurement measurement;
    if (member.measurementlist.size() > 0) {
      measurement = member.measurementlist.get(0);
    } else {
      measurement = null;
    }
    double BMI = GymUtility.calculateBMI(member, measurement);
    String BMICategory = GymUtility.determineBMICategory(BMI);
    Logger.info("Getting BMI Category");
    return BMICategory;
  }

  public static String isIdealBodyWeight() {
    Member member = Accounts.getLoggedInMember();
    Measurement measurement;
    if (member.measurementlist.size() > 0) {
      measurement = member.measurementlist.get(0);
    } else {
      measurement = null;
    }
    String isIdeal ="";
    if (GymUtility.isIdealBodyWeight(member, measurement)) {
      isIdeal = "Ideal";
    } else if (!GymUtility.isIdealBodyWeight(member, measurement)) {
      isIdeal = "Not Ideal";
    }
    Logger.info("Getting BMI Category");
    return isIdeal;
  }
}
