package Graphing_calculator;

import java.awt.*;
import javax.swing.JFrame;
import java.util.ArrayList;

public class GraphingCalculator extends JFrame{

    public GraphingCalculator(int width, int height){
        GraphInputs input = new GraphInputs();
        // Point size = input.getInputs();
        try {
            Thread.sleep(999999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        add("Center", new createCanvas());
    }

    class createCanvas extends Canvas{

        private int maxX;
        private int maxY;
        private double res;

        public void paint(Graphics g){
            Dimension d = this.getSize();
            this.maxX   = d.width - 1;
            this.maxY   = d.height - 1;
            this.res    = 0.1;
            long start = System.nanoTime();
            drawGraph(g);
            g.translate(this.maxX / 2, this.maxY / 2);
            calculatePoints(20, g);
            System.out.println("Process Time: " + (System.nanoTime() - start)/1_000_000 + "ms");
        }

        public void drawGraph(Graphics g){
            int midX = this.maxX/2;
            int midY = this.maxY/2;
            g.setColor(Color.BLACK);
            g.drawLine(0, midY, this.maxX, midY);
            g.drawLine(midX, 0, midX, this.maxY);
            double bigger = bigger(this.maxX, this.maxY);
            for (int i = 0; i < bigger; i+=bigger/10)
                g.drawArc(midX-i, midY-i, i*2, i*2, 0, 360);
        }

        public void calculatePoints(int scale, Graphics g){
            ArrayList<Point> yPoints = new ArrayList<Point>();
            for (double x = -(this.maxX/2); x < this.maxX; x+= 0.1/this.res){
                double y = -(
                    (Math.sin(x) + Math.cos(x)) * Math.sin(x * x)
                );
                yPoints.add(new Point(x * scale, y * scale * 10));
            }
            drawResults(yPoints, g);
        }
        
        public void drawResults(ArrayList<Point> yPoints, Graphics g){   
            g.setColor(Color.BLACK);   
            drawPoints(yPoints, g);
            g.setColor(Color.RED);
            drawLines(yPoints, g);
            g.setColor(Color.GRAY);
            drawRect(yPoints, g);
        }

        public void drawPoints(ArrayList<Point> yPoints, Graphics g){
            for (int i = 1; i < yPoints.size(); i++){ 
                int circleSize = 10;
                g.drawArc((int) yPoints.get(i).getX()-(circleSize/2), (int) yPoints.get(i).getY()-(circleSize/2), circleSize, circleSize, 0, 360);
            }
        }

        public void drawLines(ArrayList<Point> yPoints, Graphics g){
            for (int i = 1; i < yPoints.size(); i++){ 
                g.drawLine((int) yPoints.get(i-1).getX(), (int) yPoints.get(i-1).getY(), (int) yPoints.get(i).getX(), (int) yPoints.get(i).getY());
            }
        }

        public void drawRect(ArrayList<Point> yPoints, Graphics g){
            for (int i = 1; i < yPoints.size(); i++){ 
                double pointOneX = yPoints.get(i-1).getX();
                double pointOneY = yPoints.get(i-1).getY();
                double pointTwoX = yPoints.get(i).getX();
                double height = yPoints.get(i-1).getY();
                double width = bigger(pointOneX, pointTwoX) - smaller(pointOneX, pointTwoX);
                if (pointOneY < 0){
                    height = -height;
                }
                else{
                    height = pointOneY;
                    pointOneY = 0;
                }
                g.drawRect((int) pointOneX, (int) (pointOneY - 1), (int) width, (int) height);
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