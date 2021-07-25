package sample;

import java.io.Serializable;

public class Profile implements Serializable {

    private static final long serialVersionUID = 8922337584987367254L;
    String id;
    int age;
    String first_name, last_name;
    Gender gender;
    String user_name;
    String password;

    Profile(String Id, int age1, String first_name1, String last_name1, Gender gender1, String user_name1, String password1) {
        id = Id;
        age = age1;
        first_name = first_name1;
        last_name = last_name1;
        gender = gender1;
        user_name = user_name1;
        password = password1;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getId() {
        return id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", age=" + age +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", gender=" + gender +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
