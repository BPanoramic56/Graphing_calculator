package Graphing_calculator;

import java.awt.*;
import javax.swing.JFrame;
import java.util.ArrayList;


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
            calculatePoints(maxX, maxY, 20, (float) 123, g);
        }

        public void drawGraph(Graphics g, int maxX, int maxY){
            int midX = maxX/2;
            int midY = maxY/2;
            g.setColor(Color.BLACK);
            g.drawLine(0, midY, maxX, midY);
            g.drawLine(midX, 0, midX, maxY);
            double bigger = bigger(maxX, maxY);
            for (int i = 0; i < bigger; i+=bigger/10)
                g.drawArc(midX-i, midY-i, i*2, i*2, 0, 360);
        }

        public void calculatePoints(int maxX, int maxY, int scale, float res, Graphics g){
            ArrayList<Point> yPoints = new ArrayList<Point>();
            for (double x = -(maxX/2); x < maxX; x+= 0.1/res){
                double y = -(
                    x * Math.sin(x)
                );
                Point coordinates = new Point(x * scale, y * scale * 10);
                yPoints.add(coordinates);
            }
            drawResults(yPoints, maxX, maxY, res, g);
        }
        
        public void drawResults(ArrayList<Point> yPoints, int maxX, int maxY, float res, Graphics g){
            System.out.println(1);
            drawPoints(yPoints, maxX, maxY, res, g);
            System.out.println(2);
            drawLines(yPoints, maxX, maxY, res, g);
            System.out.println(3);
            drawRect(yPoints, maxX, maxY, res, g);
            System.out.println(4);
        }

        public void drawPoints(ArrayList<Point> yPoints, int maxX, int maxY, float res, Graphics g){
            for (int i = 0; i < yPoints.size(); i++){
                System.out.println(100 * (double) i/yPoints.size());
                int circleSize = 10;
                g.drawArc((int) yPoints.get(i).getX()-(circleSize/2), (int) yPoints.get(i).getY()-(circleSize/2), circleSize, circleSize, 0, 360);
                try{
                    Thread.sleep(1);
                }
                catch(Exception InterruptedExectuion){
                    System.out.println("Interrupted");
                }
            }
        }

        public void drawLines(ArrayList<Point> yPoints, int maxX, int maxY, float res, Graphics g){
            for (int i = 1; i < yPoints.size(); i++){
                g.drawLine((int) yPoints.get(i-1).getX(), (int) yPoints.get(i-1).getY(), (int) yPoints.get(i).getX(), (int) yPoints.get(i).getY());
                try{
                    Thread.sleep(1);
                }
                catch(Exception InterruptedExectuion){
                    System.out.println("Interrupted");
                }
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
                g.drawRect((int) pointOneX, (int) (pointOneY - 1), (int) width, (int) height);
                // try{
                //     Thread.sleep(10);
                // }
                // catch(Exception InterruptedExectuion){
                //     System.out.println("Interrupted");
                // }
            }
        }

        private double bigger(double num1, double num2){
            return num1 >= num2 ? num1 : num2;
        }

        private double smaller(double num1, double num2){
            return num1 <= num2 ? num1 : num2;
        }

    }

}