package models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class View {
	final static int HEIGHT = 250, WIDTH = 250;
    final static int X_CENTER = WIDTH / 2;
    final static int Y_CENTER = HEIGHT / 2;
    public static String render(List<Point> points, double r, String oldPath){
        if(points == null){
        	points = new ArrayList<>();
        }
        Date date = new Date();
        String tempDir = "/home/s207529/public_html/";
        String imgPath = "images/areas_" + points.size() + date.toString().replaceAll("\\s", "") + ".png";
        //System.err.println("r: " + r);
        int pixelsInOne = (int)((double)100 / r);
        BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g2 = img.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        g2.setColor(new Color(61, 181, 230));
        g2.fillRect(X_CENTER - 50, Y_CENTER - 100, 50, 100);
        int[] xPoints = {X_CENTER, X_CENTER, X_CENTER + 50};
        int[] yPoints = {Y_CENTER + 100, Y_CENTER, Y_CENTER};
        g2.fillPolygon(xPoints, yPoints, 3);
        g2.fillArc(X_CENTER - 100, Y_CENTER - 100, 200, 200, 0, 90);
        g2.setColor(Color.BLACK);
        g2.drawLine(0, Y_CENTER, WIDTH, Y_CENTER);
        g2.drawLine(X_CENTER, 0, X_CENTER, HEIGHT);
        int[] xPoints1 = {WIDTH, WIDTH - 7, WIDTH - 7};
        int[] yPoints1 = {Y_CENTER, Y_CENTER - 5, Y_CENTER + 5};
        g2.fillPolygon(xPoints1, yPoints1, 3);
        int[] xPoints2 = {X_CENTER, X_CENTER - 5, X_CENTER + 5};
        int[] yPoints2 = {0, 7, 7};
        g2.fillPolygon(xPoints2, yPoints2, 3);
        int onScreenR = 100;
        g2.drawLine(X_CENTER-10, Y_CENTER-onScreenR, X_CENTER+10, Y_CENTER-onScreenR);
        g2.drawLine(X_CENTER-5, Y_CENTER-onScreenR/2, X_CENTER+5, Y_CENTER-onScreenR/2);
        g2.drawLine(X_CENTER-10, Y_CENTER+onScreenR, X_CENTER+10, Y_CENTER+onScreenR);
        g2.drawLine(X_CENTER-5, Y_CENTER+onScreenR/2, X_CENTER+5, Y_CENTER+onScreenR/2);
        
        g2.drawLine(X_CENTER-onScreenR, Y_CENTER-10, X_CENTER-onScreenR, Y_CENTER+10);
        g2.drawLine(X_CENTER-onScreenR/2, Y_CENTER-5, X_CENTER-onScreenR/2, Y_CENTER+5);
        g2.drawLine(X_CENTER+onScreenR, Y_CENTER-10, X_CENTER+onScreenR, Y_CENTER+10);
        g2.drawLine(X_CENTER+onScreenR/2, Y_CENTER-5, X_CENTER+onScreenR/2, Y_CENTER+5);

        g2.drawString("-R", X_CENTER-onScreenR-7, Y_CENTER-11);
        g2.drawString("-R/2", X_CENTER-onScreenR/2-7, Y_CENTER-11);
        g2.drawString("R", X_CENTER+onScreenR-5, Y_CENTER-11);
        g2.drawString("R/2", X_CENTER+onScreenR/2-7, Y_CENTER-11);

        g2.drawString("R", X_CENTER+11, Y_CENTER-onScreenR+5);
        g2.drawString("R/2", X_CENTER+11, Y_CENTER-onScreenR/2+5);
        g2.drawString("-R", X_CENTER+13, Y_CENTER+onScreenR+4);
        g2.drawString("-R/2", X_CENTER+11, Y_CENTER+onScreenR/2+4);

        g2.drawString("x", WIDTH-7, Y_CENTER-onScreenR/15-1);
        g2.drawString("y", X_CENTER+onScreenR/15+1, 10);
        for(int i = 0; i < points.size(); ++i){
            Point p = points.get(i);
            if(p.x != null && p.y != null && 
                    p.x != Double.NaN && p.y != Double.NaN){
                double x = p.x;
                double y = p.y;
                if(Point.isEntryBool(x, y, r)){
                    g2.setColor(Color.GREEN);
                } else {
                   g2.setColor(new Color(236,25,70)); 
                }
                if(Math.abs(x * pixelsInOne) < 123 && Math.abs(y * pixelsInOne) < 123){
                    g2.fillRect(X_CENTER + (int)(x * (double)pixelsInOne) - 1, Y_CENTER - (int)(y * (double)pixelsInOne) - 1, 3, 3);
                }
            }
        }
        //System.err.println("End draw");
        File f = new File(tempDir + imgPath);
        try {                    
            ImageIO.write(img, "png", f);
            if(oldPath != null){
                new File(tempDir + oldPath).delete();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
        return imgPath;
    }
}
 