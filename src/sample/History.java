package sample;

import java.util.Date;

public class History {
    String score;
    String date;
    String status;
    String time;

    History(String score1, String status1, String time1, String date1) {
        score = score1;
        status = status1;
        time = time1;
        date = date1;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getScore() {
        return score;
    }

    public String getStatus() {
        return status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
