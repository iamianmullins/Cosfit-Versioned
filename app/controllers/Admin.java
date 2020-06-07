package controllers;

import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class Admin extends Controller {
    public static void index() {
        Trainer trainer = Accounts.getLoggedInTrainer();
        Logger.info("Rendering Admin");
        List<Member> memberList = Member.findAll();
        List<Trainer> trainerList = Trainer.findAll();
        int memberCount = GymUtility.numberOfMembers();
        int trainerCount = GymUtility.numberOfTrainers();
        if(trainer!=null) {
            render("admin.html", memberList, trainerList, memberCount, trainerCount);
        }
        render("admin.html");
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
