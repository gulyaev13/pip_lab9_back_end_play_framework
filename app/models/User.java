package models;

import play.db.jpa.Model;

import javax.persistence.*;

@Entity
@Table (name = "users9", schema = "S207529")
public class User extends Model {
    public String login;
    public String passHash;

    public User(String login, String passHash) {
        this.login   =   login;
        this.passHash  =  passHash;
    }
}
