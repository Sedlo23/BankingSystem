package DataStructure;


import javafx.beans.property.SimpleObjectProperty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;


/**
 *
 */
public class JGraph extends JPanel implements ActionListener {

    /**
     *
     */
    Timer timer = new Timer(100, this::actionPerformed);

    /**
     * @return
     */
    public ArrayList<Point2D> getData() {
        return data.get();
    }

    /**
     * @return
     */
    public SimpleObjectProperty<ArrayList<Point2D>> dataProperty() {
        return data;
    }

    /**
     * @param data
     */
    public void setData(ArrayList<Point2D> data) {
        this.data.set(data);
    }

    /**
     *
     */
    private SimpleObjectProperty<ArrayList<Point2D>>data=new SimpleObjectProperty<>(new ArrayList<>());

    /**
     * @return
     */
    public boolean isMovingAvg() {
        return movingAvg;
    }

    /**
     *
     */
    private String X;

    /**
     *
     */
    private String Y;

    /**
     *
     */
    private double maxY = 100;

    /**
     *
     */
    private int tickX = 4;

    /**
     *
     */
    private double maxX = 100;

    /**
     *
     */
    private int movingAvgNum=50;

    /**
     *
     */
    private boolean movingAvg =true;


    /**
     * @param movingAvg
     */
    public void setMovingAvg(boolean movingAvg) {
        this.movingAvg = movingAvg;
    }


    /**
     * @return
     */
    public int getMovingAvgNum() {
        return movingAvgNum;
    }

    /**
     * @param movingAvgNum
     */
    public void setMovingAvgNum(int movingAvgNum) {
        this.movingAvgNum = movingAvgNum;
    }

    /**
     * @return
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * @param maxX
     */
    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    /**
     * @return
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * @param maxY
     */
    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }


    /**
     * @return
     */
    public double getMinY() {
        return minY;
    }

    /**
     * @param minY
     */
    public void setMinY(double minY) {
        this.minY = minY;
    }

    /**
     *
     */
    private double minY=0;

    /**
     * @return
     */
    public int getTickX() {
        return tickX;
    }

    /**
     * @param tickX
     */
    public void setTickX(int tickX) {
        if(tickX>0)
        this.tickX = tickX;
    }

    /**
     * @return
     */
    public int getTickY() {
        return tickY;
    }

    /**
     * @param tickY
     */
    public void setTickY(int tickY) {
        if(tickY>0)
        this.tickY = tickY;
    }

    /**
     *
     */
    private int tickY = 4;

    /**
     * @param x
     * @param y
     */
    public JGraph(String x, String y) {
        super();

        this.X = x;
        this.Y = y;

        timer.start();

    }


    /**
     * @param x
     * @param y
     * @param p
     */
    public JGraph(String x, String y, ArrayList<Point2D> p) {
        super();

        this.X = x;
        this.Y = y;
        this.setData(p);
        timer.start();

    }



    /**
     * @param array
     * @return
     */
    public static double Avg(double[] array) {

        double sum = 0;

        for (double tmp : array)
            sum += tmp;

        return sum / array.length;
    }

    /**
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {


        super.paintComponent(g);


        Graphics2D g2=(Graphics2D)g;

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        g2.translate(0, getHeight());

        g2.setFont(new Font("TimesRoman", Font.PLAIN,12));

        g2.setColor(Color.blue);


        for(int i=0;i<=tickY;i++)
        {

            g2.drawString(Double.toString((int)((maxY)/tickY)*i), 2, -((this.getHeight()-100)/tickY)*i-50);


        }

        for(int i=0;i<=tickX;i++)
        {
            g2.drawString(Double.toString((int)(maxX/tickX)*i), ((this.getWidth()-100)/tickX)*i+50, -10);

        }

        g2.drawString(X, getWidth() - 50, -50);
        g2.drawString(Y, 50,-getHeight()+20);

        g2.translate(+50, -50);

        g2.scale(1, -1);




        g2.scale(((getWidth()-100)/(maxX)),((getHeight()-100)/((maxY))));



        g2.setColor(Color.BLACK);


        for (int i = 1; i < getData().size(); i++) {


            if (movingAvg) {
                if (i > movingAvgNum) {
                    double[] avg = new double[movingAvgNum];
                    double[] avg2 = new double[movingAvgNum];


                    for (int z = 0; z < movingAvgNum; z++)
                        avg[z] = getData().get(i - z).getY();

                    for (int z = 0; z < movingAvgNum; z++)
                        avg2[z] = getData().get(i - z - 1).getY();

                    Point2D tmp1 = new Point2D.Double(getData().get(i - 1).getX(), Avg(avg2));
                    Point2D tmp = new Point2D.Double(getData().get(i).getX(), Avg(avg));

                    g2.draw(new Line2D.Double(tmp1, tmp));

                    maxY = Math.max(getData().get(i).getY(), maxY);

                    maxX = getData().get(i).getX();
                }

            } else {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Point2D tmp1 = new Point2D.Double(getData().get(i - 1).getX(), getData().get(i - 1).getY());
                Point2D tmp = new Point2D.Double(getData().get(i).getX(), getData().get(i).getY());
                g2.draw(new Line2D.Double(tmp1, tmp));

                maxY = Math.max(tmp.getY(), maxY);

                maxX = Math.max(tmp.getX(), maxX);

            }


        }

        g2.setColor(Color.LIGHT_GRAY);


        for(int i=0;i<=tickX;i++)
        {
            g2.draw(new Line2D.Double(i*(maxX/tickX),0,i*(maxX/tickX),maxY));



        }

        for(int i=0;i<=tickY;i++)
        {


            g2.draw(new Line2D.Double(0,i*((maxY)/tickY),maxX,i*((maxY)/tickY)));

        }










    }

    /**
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
