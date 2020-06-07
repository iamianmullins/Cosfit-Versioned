package controllers;

import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class Admin extends Controller {
    public static void index() {
        Logger.info("Rendering Admin");
        Member member = Accounts.getLoggedInMember();
        if (member == null) {
            List<Member> memberList = Member.findAll();
            List<Trainer> trainerList = Trainer.findAll();
            int memberCount = GymUtility.numberOfMembers();
            int trainerCount = GymUtility.numberOfTrainers();
            render("admin.html", memberList, trainerList, memberCount, trainerCount);
        }
        else{
            Logger.info("Access to admin denied");
            Start.index();
        }
    }

    public static void deleteMember(Long id) {
        List<Member> memberList = Member.findAll();
        Member member = Member.findById(id);
        memberList.remove(member);
        member.save();
        member.delete();
        Logger.info("Deleting " + member.firstname + " " + member.lastname);
        redirect("/admin");
    }

    public static void deleteTrainer(Long id) {
        List<Trainer> memberList = Trainer.findAll();
        Trainer trainer = Trainer.findById(id);
        memberList.remove(trainer);
        trainer.save();
        trainer.delete();
        Logger.info("Deleting " + trainer.firstname + " " + trainer.lastname);
        redirect("/admin");
    }


}
