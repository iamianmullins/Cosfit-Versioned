package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Member extends Model {
    public String firstname;
    public String lastname;
    public String address;
    public Date dateOfBirth;
    public String gender;
    public double height;
    public double startingWeight;
    public double mostRecentWeight;
    public int phone;
    public String email;
    public String password;


    @OneToMany(cascade = CascadeType.ALL)
    public List<Measurement> measurementlist = new ArrayList<Measurement>();


    public Member(String firstname, String lastname, String address, Date dateOfBirth, String gender,
                  double height, double startingWeight, double mostRecentWeight, int phone, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.height = height;
        this.startingWeight = startingWeight;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public double getBMI() {
        double BMI;
        if (measurementlist.size() > 0) {
            Measurement measurement = measurementlist.get(measurementlist.size() - 1);
            BMI = measurement.weightRecord / (height * height);
        } else {
            BMI = startingWeight / (height * height);
        }
        return BMI;
    }


    public static Member findByEmail(String email) {
        return find("email", email).first();
    }


    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public String getGender() {
        return gender;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMostRecentWeight(double mostRecentWeight) {
        this.mostRecentWeight = mostRecentWeight;
    }

    public double getStartingWeight() {
        return startingWeight;
    }

}