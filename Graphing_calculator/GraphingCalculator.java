package Graphing_calculator;
import java.awt.*;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphingCalculator extends JFrame {

    private double scale;
    private double res;
    
    public GraphingCalculator(int width, int height, double scale, double res){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        this.res    = res;
        this.scale  = scale;
        add("Center", new createCanvas());
    }

    public double getRes(){
        return this.res;
    }
    public double getScale(){
        return this.scale;
    }

    class createCanvas extends Canvas{

        private int maxX;
        private int maxY;
        private double scale;
        private double res;
        private boolean[] decision;

        public void paint(Graphics g){
            Dimension d = this.getSize();
            this.maxX   = d.width - 1;
            this.maxY   = d.height - 1;
            this.res    = getRes();
            this.scale  = getScale();
            menu();
            long start = System.nanoTime();
            drawGraph(g);
            g.translate(this.maxX / 2, this.maxY / 2);
            calculatePoints(g);
            System.out.println("Process Time: " + (System.nanoTime() - start)/1_000_000 + "ms");
        }

        private void menu(){
            boolean[] decision = new boolean[3];
            Scanner option = new Scanner(System.in);
            int chosen;
            while (true){
                System.out.println("ENTER - Save and exit");
                System.out.println("1 - Draw Points:    \t[" + (decision[0] == true ? "true" : "false") + "]");
                System.out.println("2 - Draw Lines:     \t[" + (decision[1] == true ? "true" : "false") + "]");
                System.out.println("3 - Draw Rectangles:\t[" + (decision[2] == true ? "true" : "false") + "]");
                chosen = option.nextInt();
                if (chosen == 0)
                    break;
                decision[chosen - 1] = !decision[chosen-1];
            }
            option.close();
            this.decision = decision;
        }

        private void drawGraph(Graphics g){
            int midX = this.maxX/2;
            int midY = this.maxY/2;
            g.setColor(Color.BLACK);
            g.drawLine(0, midY, this.maxX, midY);
            g.drawLine(midX, 0, midX, this.maxY);
            double bigger = bigger(this.maxX, this.maxY);
            for (int i = 0; i < bigger; i+=bigger/10)
                g.drawArc(midX-i, midY-i, i*2, i*2, 0, 360);
        }

        private void calculatePoints(Graphics g){
            ArrayList<Point> yPoints = new ArrayList<Point>();
            for (double x = -(this.maxX/2); x < this.maxX; x+= 0.1/this.res){
                double y = -(
                    Math.sin(Math.cos(x))
                );
                yPoints.add(new Point(x * scale, y * scale * 10));
            }
            drawResults(yPoints, g);
        }
        
        private void drawResults(ArrayList<Point> yPoints, Graphics g){  
            if (decision[0]) 
                drawPoints(yPoints, g);
            if (decision[1])
                drawLines(yPoints, g);
            if (decision[2])
                drawRect(yPoints, g);
        }

        private void drawPoints(ArrayList<Point> yPoints, Graphics g){
            g.setColor(Color.BLACK);   
            for (int i = 1; i < yPoints.size(); i++){ 
                int circleSize = 3;
                g.drawArc((int) yPoints.get(i).getX()-(circleSize/2), (int) yPoints.get(i).getY()-(circleSize/2), circleSize, circleSize, 0, 360);
            }
        }

        private void drawLines(ArrayList<Point> yPoints, Graphics g){
            g.setColor(Color.RED);
            for (int i = 1; i < yPoints.size(); i++){ 
                g.drawLine((int) yPoints.get(i-1).getX(), (int) yPoints.get(i-1).getY(), (int) yPoints.get(i).getX(), (int) yPoints.get(i).getY());
            }
        }

        private void drawRect(ArrayList<Point> yPoints, Graphics g){
            g.setColor(Color.GRAY);
            for (int i = 1; i < yPoints.size(); i++){ 
                double pointOneX = yPoints.get(i-1).getX();
                double pointOneY = yPoints.get(i-1).getY();
                double height = yPoints.get(i-1).getY();
                double width = bigger(pointOneX, yPoints.get(i).getX()) - smaller(pointOneX, yPoints.get(i).getX());
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