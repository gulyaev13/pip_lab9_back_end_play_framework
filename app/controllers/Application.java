package controllers;

import play.*;
import play.mvc.*;
import play.mvc.results.*;
import com.google.gson.*;
import java.util.*;

import models.*;
import services.*;

public class Application extends Controller {

	private static final String falseJSON = "{\"value\" : \"false\" }";
	private static final String trueJSON = "{\"value\" : \"true\" }";

    public static void index() {
        render();
    }

    public static void addPoint(){
    	Double x = params.get("x", Double.class);
		Double y = params.get("y", Double.class);
		Double r = params.get("r", Double.class);
        String login = session.get("login");
		System.out.println("x: " + x + "\ny: " + y + "\nr: " + r);
        if(login != null){
            Point p = new Point(x, y, r, login);
            p.save();
            renderJSON("{\"x\": " + p.x + ", \"y\": " + p.y + ", \"r\": " + p.r + ", \"entry\": " + p.entry + "}");
        }
    }

    public static void getPoints(){
        if(session.get("login") != null){
            List<Point> points1 = Point.find("byLogin", session.get("login")).fetch();
            List<PointJSON> points2 = new ArrayList<>();
            points1.forEach(p->{points2.add(new PointJSON(p));});
            Gson gson = new Gson();
            String pointsJson = gson.toJson(points2);
            System.out.println(pointsJson);
            renderJSON(pointsJson);
        }
    }
    public static void registrate(){
    	String login = params.get("login");
    	String password = params.get("password");
    	System.out.println("login: " + login + "\npassword: " + password);
        User user = User.find("byLogin", login).first();
        System.out.println("User: " + user);
    	if(user != null){
    		System.out.println("registrate failed");
            forbidden();
       	} else {
            new User(login, password).save();
    		System.out.println("registrate complete");
    		ok();
    	}
    }
    public static void login(){
    	String login = params.get("login");
    	String password = params.get("password");
    	System.out.println("login: " + login + "\npassword: " + password);
        User user = User.find("byLoginAndPassHash", login, password).first();
    	if(user != null){
    		System.out.println("login complete");
    		session.put("login", login);
            new JMSMessaging().SendMessage("User: " + login + " logined");
            ok();
    	} else {
    		System.out.println("login failed");
            forbidden();
    	}
    }
    public static void is_login(){
    	if(session.get("login") == null){
    		System.out.println("You aren't logined");
            forbidden();
    	} else {
    		System.out.println("You are logined");
            System.out.println("login: " + session.get("login"));
            ok();
    	}
    }
    public static void logout(){
    	System.out.println("logout");
    	session.clear();
    }
    public static void delete(){
    	if(session.get("login") == null){
    		System.out.println("delete failed");
    		forbidden();
    	} else {
            List<Point> points = Point.find("byLogin", session.get("login")).fetch();
            points.forEach(Point::delete);
    		System.out.println("delete complete");
            ok();
    	}
    }
    public static void getImage(){
        System.out.println("Get image: " + session.get("login"));
        if(session.get("login") != null){
            List<Point> points = Point.find("byLogin", session.get("login")).fetch();
            double r;
            if(points != null && points.size() > 0){
                r = points.get(points.size() - 1).r;
            } else {
                r = 1;
            }
            String newPath = View.render(points, r, session.get("imgPath"));
            session.put("imgPath", newPath);
            Gson gson = new Gson();
            String imgJson = gson.toJson(newPath);
            System.out.println(imgJson);
            renderJSON(imgJson);
            //renderText(newPath);
        }
    }
    public static void changeRadius(){
        Double r = params.get("r", Double.class);
        if(session.get("login") != null){
            List<Point> points = Point.find("byLogin", session.get("login")).fetch();
            String newPath = View.render(points, r, session.get("imgPath"));
            session.put("imgPath", newPath);
            Gson gson = new Gson();
            String imgJson = gson.toJson(newPath);
            System.out.println(imgJson);
            renderJSON(imgJson);
        }
    }
}