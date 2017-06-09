package models;

import play.db.jpa.Model;

import javax.persistence.*;

@Entity
@Table (name = "points9", schema = "S207529")
public class Point extends Model{
    public Double x;
    public Double y;
    public Double r;
    public Integer entry;
    public String login;

    public Point(double x, double y, double r, String login) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.entry = isEntry(x, y, r);
        this.login = login;
    }
    public Point(double x, double y, double r, int entry, String login) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.entry = entry;
        this.login = login;
    }
    public static boolean isEntryBool(double x, double y, double r){
    	if(x < 0){
    		if(x >= -r/2 && y <= r && y >= 0)
    			return true;
    		return false;
    	} else {
    		if(y >= 0){
    			if(x * x + y * y <= r * r)
    				return true;
    			return false;
    		} else {
    			if(y >= 2 * x - r)
    				return true;
    			return false;
    		}
    	}
    }
    public static int isEntry(double x, double y, double r){
    	return isEntryBool(x, y, r) ? 1 : 0;
    }
    public void checkEntry(){
    	entry = isEntry(x, y, r);
    }
}
