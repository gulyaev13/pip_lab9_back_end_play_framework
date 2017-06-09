package models;
public class PointJSON{
    public Double x;
    public Double y;
    public Double r;
    public Integer entry;

    public PointJSON(double x, double y, double r, int entry) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.entry = entry;
    }
    public PointJSON(Point p){
    	this.x = p.x;
        this.y = p.y;
        this.r = p.r;
        this.entry = p.entry;
    }
} 