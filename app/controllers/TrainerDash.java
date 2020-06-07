package controllers;

import models.Measurement;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.List;
import java.util.Collections;

/**
 * Renders trainer Dashboars,
 * passes logged in trainer
 * memberlist,
 * gym analytics
 */
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

    /**
     * Finds member by id,
     * removed member from member list
     * saves and deleted member from db
     */
    public static void deleteMember(Long id) {
        List<Member> memberList = Member.findAll();
        Member member = Member.findById(id);
        memberList.remove(member);
        member.save();
        member.delete();
        Logger.info("Deleting " + member.firstname + " " + member.lastname);
        redirect("/trainerdashboard");
    }

}
