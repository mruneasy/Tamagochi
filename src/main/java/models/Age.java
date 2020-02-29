package models;

import java.io.Serializable;
import java.util.Date;
import java.math.*;

public class Age implements Serializable {
    private int startDay;
    private int startHour;
    private int startMinut;
    private int age;

    public Age() {

    }

    public int getStartDay() {
        return startDay;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinut() {
        return startMinut;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setStartMinut(int startMinut) {
        this.startMinut = startMinut;
    }


    public long getAge() {
        Date date = new Date();
        age = Math.abs(((date.getDate() - startDay)*24*60) - (-(date.getHours() - startHour) * 60 - (date.getMinutes() - startMinut)));
        return age / 60;

    }

}
