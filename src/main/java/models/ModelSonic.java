package models;

import iface.WorkWithFile;
import impl.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ModelSonic {

    Date date = new Date();

    double levelProgressBar = 0.0;

    double deathProgressBar = 1;

    double timee;

    boolean isVisibleEatPic = false;

    boolean isVisibleRunPic = false;

    boolean isVisibleCryPic = false;

    boolean isVisibleDeathPic = false;

    boolean rain = false;

    boolean snow = false;

    WorkWithFile workWithFile = new WorkWithFileSonic();
    MoveToSonic moveToSonic = new MoveToSonic();


    public ModelSonic() {

    }

    public double rehab(double feeling) {
        this.levelProgressBar = feeling;
        return levelProgressBar += 0.1;
    }

    public double getLevelFeeling() {
        return levelProgressBar;
    }

    public double getDeathProgressBar() {
        return deathProgressBar;
    }


    public double loadFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person.txt"))) {
            levelProgressBar = ((Double) ois.readDouble());

            long exitDay = ois.readLong();
            long exitHour = ois.readLong();
            long exitMinutes = ois.readLong();

            Date date1 = new Date();

            if (date1.getDate()-exitDay==0) {
                timee = (levelProgressBar / 0.01) * 1.8 - (date1.getHours() - exitHour) * 60 - (date1.getMinutes() - exitMinutes);

                if (timee > 0)
                    levelProgressBar = ((levelProgressBar / 0.01) * 1.8 - (date1.getHours() - exitHour) * 60
                            - (date1.getMinutes() - exitMinutes)) / 1.8 * 0.01;

                if (timee > 30) deathProgressBar = (30 - (date1.getMinutes() - exitMinutes)) / 0.3 * 0.01;
            } else return -10000;

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        } finally {
            return timee;
        }
    }

    public void saveFile(boolean isBirth, Double feeling, Date date) {
        levelProgressBar = feeling;
        if (isBirth) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.txt"))) {
                oos.writeDouble(levelProgressBar);
                //oos.writeObject(ModelSonic.this);
                oos.writeLong(date.getDate());
                oos.writeLong(date.getHours());
                oos.writeLong(date.getMinutes());
            } catch (Exception ex) {

                System.out.println(ex.getMessage());
            }
        }
    }


}
