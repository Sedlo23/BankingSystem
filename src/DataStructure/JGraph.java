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
    private double maxY = 1;

    /**
     *
     */
    private int tickX = 4;

    /**
     *
     */
    private double maxX = 1;

    /**
     *
     */
    private int movingAvgNum=50;

    /**
     *
     */
    private boolean movingAvg =false;


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

            double value= ((double)maxX/(double)tickX)*i;
            g2.drawString( Double.toString(Math.round(value*100d)/100d),((this.getWidth()-100)/tickX)*i+50, -10);

        }

        g2.drawString(X, getWidth() - 50, -50);
        g2.drawString(Y, 50,-getHeight()+20);

        g2.translate(+50, -50);

        g2.scale(1, -1);



        double scX=((getWidth()-100)/(maxX));
        double scY=((getHeight()-100)/((maxY)));



        g2.setColor(Color.BLACK);

        ArrayList<Double> values= new ArrayList<>();

        int lastX=0;

        ArrayList<Point2D> point2DS=new ArrayList<>();

        for (int i = 0; i < getData().size(); i++) {


            if (movingAvg) {
                if (i > movingAvgNum) {
                    double[] avg = new double[movingAvgNum];
                    double[] avg2 = new double[movingAvgNum];


                    for (int z = 0; z < movingAvgNum; z++)
                        avg[z] = getData().get(i - z).getY();

                    for (int z = 0; z < movingAvgNum; z++)
                        avg2[z] = getData().get(i - z - 1).getY();

                    Point2D tmp1 = new Point2D.Double(getData().get(i - 1).getX()*scX, Avg(avg2)*scY);
                    Point2D tmp = new Point2D.Double(getData().get(i).getX()*scX, Avg(avg)*scY);
                    g2.draw(new Line2D.Double(tmp1, tmp));

                    maxY = Math.max(getData().get(i).getY()/scY, maxY);

                    maxX = getData().get(i).getX()/scX;






                }

            } else {


                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


                Point2D tmp = new Point2D.Double(getData().get(i).getX()*scX, getData().get(i).getY()*scY);

                if (lastX<(int)(getData().get(i).getX()*scX)-1)
               {

                   if (values.size()>0)  {
                       double total = 0;

                   for(int o=0; o<values.size(); o++){
                       total = total + values.get(o);
                   }

                       double average = total / values.size();
                       point2DS.add(new Point2D.Double(lastX, average));
                   }

                   point2DS.add(tmp);
                   lastX=(int)(getData().get(i).getX()*scX);
                   values=new ArrayList<>();
               }else
                   values.add(getData().get(i).getY()*scY);


                maxY = Math.max(tmp.getY()/scY, maxY);

                maxX = Math.max(tmp.getX()/scX, maxX);





            }


        }

        g2.setColor(Color.LIGHT_GRAY);


        for(int i=0;i<=tickX;i++)
        {
            g2.draw(new Line2D.Double(i*(maxX/tickX)*scX,0,i*(maxX/tickX)*scX,maxY*scY));



        }

        for(int i=0;i<=tickY;i++)
        {


            g2.draw(new Line2D.Double(0,i*((maxY)/tickY)*scY,maxX*scX,i*((maxY)/tickY)*scY));

        }


        for (int x= 0;x<point2DS.size()-1;x++)
        {

            g2.drawLine((int)point2DS.get(x).getX(),(int)point2DS.get(x).getY(),(int)point2DS.get(x+1).getX(),(int)point2DS.get(x+1).getY());

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
