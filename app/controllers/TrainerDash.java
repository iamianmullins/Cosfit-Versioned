package controllers;

import models.Measurement;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.List;
import java.util.Collections;

public class TrainerDash extends Controller {
    public static void index() {
        Logger.info("Rendering TrainerDashboard");
        Trainer trainer = Accounts.getLoggedInTrainer();
        List<Member> memberList = Member.findAll();
        Collections.reverse(memberList);
        int numberOfMembers = GymUtility.numberOfMembers();
        int numberOfTrainers = GymUtility.numberOfTrainers();
        double averageBmi = GymUtility.averageBmi();
        render("trainerdashboard.html", trainer, memberList, numberOfMembers,
                numberOfTrainers, averageBmi);
    }

    public static void deleteMember(Long id) {
        List<Member> memberList = Member.findAll();
        Member member = Member.findById(id);
        memberList.remove(member);
        member.save();
        member.delete();
        Logger.info("Deleting " + member.firstname + " " + member.lastname );
        redirect("/trainerdashboard");
    }

    public static void viewMember(Long id) {
        Logger.info("Rendering View Member");
        Member member = Member.findById(id);
        String BMICategory = GymUtility.determineBMICategory(id);
        String idealBW = GymUtility.isIdealBodyWeight();
        double getBMI = GymUtility.getBMI();
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
        measurement.setComment(trainerName + ":  "+comment);
        member.save();
        measurement.save();
        Logger.info("Editing " + measurement.dte + "Comment: " + comment);
        viewMember(id);
    }

}
