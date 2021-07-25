package sample;

public class MaxRecord implements Comparable<MaxRecord> {
    String username;
    float score;


    MaxRecord(String username1, float score1) {
        username = username1;
        score = score1;

    }

    public float getScore() {
        return score;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public int compareTo(MaxRecord o) {

        return (int) (o.score-score);
    }
}
