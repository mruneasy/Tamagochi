package models;


import java.io.*;
import java.util.Date;
import java.math.*;

public class Model {

    private Date date = new Date();

    private boolean liveOrDeath;

    private double levelProgressBar = 1;

    private String fileName;

    private double deathProgressBar = 1;

    private double timee;


    private Age age = new Age();


    public Model(String fileName) {
        this.fileName = fileName;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public double getLevelFeeling() {
        return levelProgressBar;
    }

    public void setLevelFeeling(double feeling) {
        this.levelProgressBar = feeling;
    }

    public double getDeathProgressBar() {
        return deathProgressBar;
    }

    public void setDeathProgressBar(double deathprogress) {
        this.deathProgressBar = deathprogress;
    }

    public Age getAge(){
        return age;
    }

    public void setAge(Age age){
        this.age = age;
    }


    public double getTime() {
        return timee;
    }

    public void setTime(double timee) {
        this.timee = timee;
    }



    public boolean loadFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            levelProgressBar = ((Double) ois.readDouble());
            deathProgressBar = (Double)ois.readDouble();
            long exitDay = ois.readLong();
            long exitHour = ois.readLong();
            long exitMinutes = ois.readLong();
            age = (Age) ois.readObject();


            Date date1 = new Date();



            timee = ((levelProgressBar / 0.01) * 1.8) - Math.abs(((date1.getDate() - exitDay)*24*60) + (date1.getHours() - exitHour) * 60
                    - (date1.getMinutes() - exitMinutes)); //был плюс

            System.out.println("Time feeling :"+timee);

            if (timee>0){
                levelProgressBar = ((((levelProgressBar / 0.01) * 1.8) - Math.abs(((date1.getDate() - exitDay)*24*60) + ((date1.getHours() - exitHour) * 60)
                        - (date1.getMinutes() - exitMinutes))) / 1.8 * 0.01) + 0.01;
                liveOrDeath = true;
            }

            if (timee<=0){
                timee = ((levelProgressBar/0.01)*1.8) + ((deathProgressBar / 0.01) * 0.3) - Math.abs(((date1.getDate() - exitDay)*24*60) + ((date1.getHours() - exitHour) * 60)
                        - (date1.getMinutes() - exitMinutes));
                System.out.println("Time killing :"+timee);

                if ( timee > 0 ){
                    deathProgressBar = (((((levelProgressBar/0.01)*1.8) + (deathProgressBar/0.01)*0.3) - Math.abs(((date1.getDate() - exitDay)*24*60) + ((date1.getHours() - exitHour) * 60)
                            - (date1.getMinutes() - exitMinutes))) / 0.3 * 0.01) + 0.01;
                    liveOrDeath = false;
                }
            }

        if (timee<-32) {
            File File = new File(getFileName());
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(0);
        }

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
        return liveOrDeath;
    }

    public double rehab(double feeling) {
        this.levelProgressBar = feeling;
        levelProgressBar += 0.1;
        return levelProgressBar;
    }

    public void saveFile(boolean isBirth) {
        if ( deathProgressBar<=0 ) {return;}

        if (isBirth) {
        Date date1= new Date();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                oos.writeDouble(levelProgressBar);
                oos.writeDouble(deathProgressBar);
                oos.writeLong(date1.getDate());
                oos.writeLong(date1.getHours());
                oos.writeLong(date1.getMinutes());
                oos.writeObject(age);
            } catch (Exception ex) {

                System.out.println(ex.getMessage());
            }
        }
    }


}
