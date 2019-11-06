package DataStructure;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;


/**
 *
 */
public class JGraph extends JPanel implements MouseListener {


    ArrayList<Point2D> data=new ArrayList<>();

    private static final int MAX_LENGTH = 4;

    /**
     * @return
     */
    public ArrayList<Point2D> getData() {
        return data;
    }

    /**
     * @param data
     */
    public void setData(ArrayList<Point2D> data) {
        this.data=(data);
    }


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
    private int movingAvgNum=5;

    /**
     *
     */
    private boolean movingAvg =false;

    private int selectedFrom =60;

    private int selectedTo=200;

    private boolean selectedPart=false;


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
     *
     */
    private double OffSetX=0;

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

        this.addMouseListener(this);

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

        for (int tt=0;tt<3;tt++)
        {

        super.paintComponent(g);

        Graphics2D g2=(Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.translate(0, getHeight());

        g2.setFont(new Font("TimesRoman", Font.PLAIN,12));

        g2.setColor(Color.blue);


        for(int i=0;i<=tickY;i++)
        {
            double value= ((double)maxY/(double)tickY)*i;
            g2.drawString(formatNum(Math.round(value*100d)/100d), 2, -((this.getHeight()-100)/tickY)*i-50);


        }

        for(int i=0;i<=tickX;i++)
        {

            double value= ((double)maxX/(double)tickX)*i;
            g2.drawString( formatNum(Math.round((value+OffSetX)*100d)/100d),((this.getWidth()-200)/tickX)*i+100, -10);

        }

        g2.drawString(X, getWidth() - 50, -50);

        g2.drawString(Y, 50,-getHeight()+20);

        g2.translate(+100, -50);

        g2.scale(1, -1);

        double scX=((getWidth()-200)/(maxX));
        double scY=((getHeight()-100)/((maxY)));

        g2.setColor(Color.BLACK);

        ArrayList<Point2D> point2DS=new ArrayList<>();

            if (movingAvg) {
                point2DS =getMovingAvgGraph();

            }
            else if(selectedPart)
                {
                point2DS=getSelectedData();
                }
            else
                {
                point2DS=getPlainGraph();
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

        g2.setColor(Color.BLACK);

        for (int x= 0;x<point2DS.size()-1;x++)
        {
            g2.drawLine((int)point2DS.get(x).getX(),(int)point2DS.get(x).getY(),(int)point2DS.get(x+1).getX(),(int)point2DS.get(x+1).getY());
        }


        g2.translate(-100, -50);

        }


    }

    private ArrayList <Point2D> getPlainGraph()
    {
        double scX=((getWidth()-200)/(maxX));
        double scY=((getHeight()-100)/((maxY)));


        ArrayList<Point2D> point2DS=new ArrayList<>();
        for (int i = 0; i < getData().size(); i+=1)
        {
            Point2D tmp = new Point2D.Double(getData().get(i).getX()*scX, getData().get(i).getY()*scY);

            point2DS.add(tmp);

            maxY = Math.max(tmp.getY()/scY, maxY);

            maxX = Math.max(tmp.getX()/scX, maxX);
        }
        return point2DS;
    }

    private ArrayList <Point2D> getMovingAvgGraph()
    {
        double scX=((getWidth()-100)/(maxX));
        double scY=((getHeight()-100)/((maxY)));

        ArrayList<Point2D> point2DS=new ArrayList<>();
        for (int i = 0; i < getData().size(); i++)
        {
            if (i > movingAvgNum) {
                double[] avg = new double[movingAvgNum];
                double[] avg2 = new double[movingAvgNum];


                for (int z = 0; z < movingAvgNum; z++)
                    avg[z] = getData().get(i - z).getY();

                for (int z = 0; z < movingAvgNum; z++)
                    avg2[z] = getData().get(i - z - 1).getY();


                Point2D tmp = new Point2D.Double(getData().get(i).getX()*scX, Avg(avg)*scY);
                point2DS.add(tmp);

                maxY = Math.max(tmp.getY()/scY, maxY);

                maxX = Math.max(tmp.getX()/scX, maxX);

            }
        }
        return point2DS;
    }

    private ArrayList <Point2D> getSelectedData()
    {

        double scX=((getWidth()-100)/(maxX));
        double scY=((getHeight()-100)/((maxY)));

        ArrayList<Point2D> point2DS=new ArrayList<>();

        if (selectedTo<getData().size())
        {
           OffSetX= getData().get(selectedFrom).getX();
         for (int i = selectedFrom; i < selectedTo; i++)
        {
             {

                 Point2D tmp = new Point2D.Double((getData().get(i).getX()-OffSetX)*scX, getData().get(i).getY()*scY);
                point2DS.add(tmp);
                maxY = Math.max(tmp.getY()/scY, maxY);
                maxX = Math.max(tmp.getX()/scX, maxX);
            }
        }
        }
        return point2DS;
    }

    public boolean isSelectedPart() {
        return selectedPart;
    }

    public void setSelectedPart(boolean selectedPart) {
        if (!selectedPart)
            OffSetX=0;

        this.selectedPart = selectedPart;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    private static String formatNum(double number) {
        String out = null;
        for ( int i = 0; i < MAX_LENGTH; i++ ) {
            String format = "%." + i + "G";
            out = String.format(format, number);
            if ( out.length() == MAX_LENGTH ) {
                return out;
            }
        }
        return out; //the best we can do
    }
}
