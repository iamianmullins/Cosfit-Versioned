package models;

import com.google.gson.internal.$Gson$Preconditions;
import play.db.jpa.Model;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Measurement extends Model {


    public String dte;
    public double previousWeight;
    public double weightRecord;
    public double chestMeasurement;
    public double abdominalMeasurement;
    public double thighMeasurement;
    public double upperArmMeasurement;
    public double waistMeasurement;
    public String comment;

    public Measurement(double previousWeight, double weightRecord, double chestMeasurement, double abdominalMeasurement, double thighMeasurement, double waistMeasurement, double upperArmMeasurement, String comment) {
        String dteString = ("" + new Date());
        dteString = dteString.substring(0, 11) + " - " + dteString.substring(11, 20);
        this.dte = dteString;
        this.previousWeight = previousWeight;
        this.weightRecord = weightRecord;
        this.chestMeasurement = chestMeasurement;
        this.abdominalMeasurement = abdominalMeasurement;
        this.thighMeasurement = thighMeasurement;
        this.upperArmMeasurement = upperArmMeasurement;
        this.waistMeasurement = waistMeasurement;
        this.comment = comment;
    }

    public double getWeightRecord() {
        return weightRecord;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}