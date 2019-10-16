package UI;

import Building.GenericBank;
import Building.LocalBank;
import Building.Road;
import disMath.Edge;
import disMath.Graph;
import disMath.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

/**
 * UI
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 16.10.19
 */
public class JMap extends JPanel implements ActionListener {


    private int refreshTime=100;

    private Timer timer;

    private Graph graph;

    private double scale=1;

    public JMap()
    {
        this(new Graph());

        this.timer=new Timer(refreshTime,this);

    }

    public JMap(Graph graph)
    {
        super();

        this.graph=graph;

        this.timer=new Timer(refreshTime,this);

        timer.start();


    }


    public void populateGraph(int numberOfRows,int numberOfColumns) throws CloneNotSupportedException {

        for (int i2=0;i2<numberOfColumns;i2++)
         for (int i=0;i<numberOfRows;i++)
                this.graph.addNode(new LocalBank(new Point2D.Double(i*100+Math.random()*99,i2*100+Math.random()*99),Color.RED,i2+","+i));

        for (Node node1:this.graph.getNodes())
            for (Node node2:this.graph.getNodes())
            {
                if(!node1.equals(node2))
                    if (((GenericBank)node1).getPosition().distance(((GenericBank)node2).getPosition())<150)
                        node1.getConnections().add(new Road(((GenericBank)node1),((GenericBank)node2),(int)((GenericBank)node1).getPosition().distance(((GenericBank)node2).getPosition()),Color.BLACK));
            }


    }

    public void highLightRoad(Node source) {



        for (Node node:graph.getNodes())
            for (Edge edge:node.getConnections())
            {
                ((Road)edge).setDefaultColor();
                ((Road)edge).setDefaultStroke();

            }
        for (Edge edge:graph.getShortens(source))
        {
            ((Road)edge).setColor(Color.BLUE);
            ((Road)edge).setStroke(30);

        }

    }

    public void highLightRoad(Node source,Node destination) {

       graph.computePaths(destination);

        for (Node node:graph.getNodes())
        for (Edge edge:node.getConnections())
            ((Road)edge).setDefaultColor();

        for (Edge edge:graph.getShortens(source))
        {
            ((Road)edge).setColor(Color.BLUE);
            ((Road)edge).setStroke(30);

        }

    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public int getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(int refreshTime) {
        this.refreshTime = refreshTime;
    }

    @Override
    public void paint(Graphics g) {


        super.paint(g);

        Graphics2D graphics2D=(Graphics2D)g;

        computeModelDimensions(graphics2D);


        for (Node node:graph.getNodes())
        {
            for (Edge edge:node.getConnections())
                ((Road)edge).draw(graphics2D);


        }

        for (Node node:graph.getNodes())
        {

            ((GenericBank) node).draw(graphics2D);
        }



    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }

    public void collisionDetection(Point2D e)
    {


        Polygon y=new Polygon();

        if(e!=null) {

            e.setLocation(e.getX()/scale,  e.getY()/scale);

            y.addPoint((int) ((e.getX()- 2)), (int) ((e.getY() - 2)));

            y.addPoint((int) ((e.getX() + 2)), (int) ((e.getY() + 2)));


            for (Node node:graph.getNodes())
            {
                if (((GenericBank)node).getHitBox().intersects(y.getBounds2D()))
                {
                    System.out.println(node.toString());
                    highLightRoad(node);
                }

                for (Edge edge:node.getConnections())
                  if (((Road)edge).getHitBox().intersects(y.getBounds()))
                  {
                      System.out.println(edge.toString());
                  }




            }

        }

    }

    public void collisionDetectionNodeSelection(Point2D e)
    {


        Polygon y=new Polygon();

        if(e!=null) {

            e.setLocation(e.getX()/scale,  e.getY()/scale);

            y.addPoint((int) ((e.getX()- 2)), (int) ((e.getY() - 2)));

            y.addPoint((int) ((e.getX() + 2)), (int) ((e.getY() + 2)));


            for (Node node:graph.getNodes())
            {
                if (((GenericBank)node).getHitBox().intersects(y.getBounds2D()))
                {
                    graph.computePaths(node);
                }

            }

        }

    }

    public  void computeModelDimensions(Graphics2D g) {

        double MinX=0;
        double MinY=0;
        double MaxX=0;
        double MaxY=0;

        for (Node node:graph.getNodes())
        {

            GenericBank genericBank=(GenericBank)node;

            if (genericBank.getPosition().getX() > MaxX)
                MaxX = genericBank.getPosition().getX();

            if (genericBank.getPosition().getY() > MaxY)
                MaxY = genericBank.getPosition().getY();

            if (genericBank.getPosition().getX() < MinX)
                MinX = genericBank.getPosition().getX();

            if (genericBank.getPosition().getY() < MinY)
                MinY = genericBank.getPosition().getY();

        }


        double Width = ( Math.abs(MaxX - MinX))+60;

        double Height = ( Math.abs(MaxY - MinY))+60;

        double scaleX=this.getWidth()/Width;

        double scaleY=this.getHeight()/Height;


        g.translate(-MinX*scaleX, -MinY*scaleY);

        double scale= Math.min(scaleX, scaleY);

        this.scale=scale;

        g.scale(scale, scale);




    }


}
