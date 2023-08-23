package Graphing_calculator;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import java.util.Random;
import java.util.ArrayList;
import java.math.*;
import java.lang.Thread;


public class GraphingCalculator extends JFrame{
    
    public GraphingCalculator(int width, int height){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        add("Center", new createCanvas());
    }

    class createCanvas extends Canvas{

        public void paint(Graphics g){
            Dimension d = this.getSize();
            int maxX = d.width - 1;
            int maxY = d.height - 1;

            drawGraph(g, maxX, maxY);
            g.translate(maxX / 2, maxY / 2);
            calculatePoints(maxX, maxY, 10, (float) 5, g);
        }

        public void drawGraph(Graphics g, int maxX, int maxY){
            int midX = maxX/2;
            int midY = maxY/2;
            g.setColor(Color.BLACK);
            g.drawLine(0, midY, maxX, midY);
            g.drawLine(midX, 0, midX, maxY);
            int bigger = bigger(maxX, maxY);
            //for loop to draw the circles, with center and the middle of the graph
            // for (int i = 0; i < bigger; i+=bigger/10)
            //     g.drawArc(midX-i, midY-i, i*2, i*2, 0, 360);
        }

        public void calculatePoints(int maxX, int maxY, int scale, float res, Graphics g){
            ArrayList<Point> yPoints = new ArrayList<Point>();
            for (double x = -(maxX/2); x < maxX; x+= 0.1/res){
                double y = -(
                    Math.sin(x) * 4 + Math.cos(x)
                );
                Point coordinates = new Point(x * scale, y * scale * 10);
                yPoints.add(coordinates);
            }

            // yPoints.add(new Point(0, Math.sin(0) * Math.cos(0) * 1000 + maxY/2 - 1));

            drawResults(yPoints, maxX, maxY, res, g);
        }
        
        //function to get the bigger dimension axis
        public int bigger(int maxX, int maxY){
            if (maxX > maxY)
                return maxX;
            else
                return maxY;
        }
        
        public double bigger(double maxX, double maxY){
            if (maxX > maxY)
                return maxX;
            else
                return maxY;
        }
        public double smaller(double maxX, double maxY){
            if (maxX > maxY)
                return maxY;
            else
                return maxX;
        }

        public void drawResults(ArrayList<Point> yPoints, int maxX, int maxY, float res, Graphics g){
            drawPoints(yPoints, maxX, maxY, res, g);
            drawLines(yPoints, maxX, maxY, res, g);
            drawRect(yPoints, maxX, maxY, res, g);
        }

        public void drawPoints(ArrayList<Point> yPoints, int maxX, int maxY, float res, Graphics g){
            for (int i = 0; i < yPoints.size(); i++){
                int circleSize = 10;
                g.drawArc((int) yPoints.get(i).getX()-(circleSize/2), (int) yPoints.get(i).getY()-(circleSize/2), circleSize, circleSize, 0, 360);
            }
        }

        public void drawLines(ArrayList<Point> yPoints, int maxX, int maxY, float res, Graphics g){
            for (int i = 1; i < yPoints.size(); i++){
                g.drawLine((int) yPoints.get(i-1).getX(), (int) yPoints.get(i-1).getY(), (int) yPoints.get(i).getX(), (int) yPoints.get(i).getY());
            }
        }

        public void drawRect(ArrayList<Point> yPoints, int maxX, int maxY, float res, Graphics g){
            for (int i = 1; i < yPoints.size(); i++){
                double pointOneX = yPoints.get(i-1).getX();
                double pointOneY = yPoints.get(i-1).getY();
                double pointTwoX = yPoints.get(i).getX();
                double height = pointOneY;
                double width = bigger(pointOneX, pointTwoX) - smaller(pointOneX, pointTwoX);
                if (pointOneY < 0){
                    height = -height;
                }
                else{
                    height = pointOneY;
                    pointOneY = 0;
                }
                // System.out.println("Height: " + height);
                // try{
                //     Thread.sleep(0);
                // }
                // catch (Exception InterruptedExectution){
                //     System.out.println("Interrupted");
                // }
                g.drawRect((int) pointOneX, (int) (pointOneY - 1), (int) width, (int) height);
                // g.drawRect((int) bigger(pointOneY, pointTwoY), (int) pointOneY, (int) width, (int) height);
            }
        }

    }

}