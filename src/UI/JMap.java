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
import java.util.PriorityQueue;

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


    @Deprecated
    public void populateGraph()
    {
            for (int i=0;i<10;i++)
                this.graph.addNode(new LocalBank(new Point2D.Double(100+Math.random()*1000,10+Math.random()*1000),Color.YELLOW));

            for (Node node1:this.graph.getNodes())
                for (Node node2:this.graph.getNodes()) {
                    node1.getConnections().add(new Road((GenericBank) node1,(GenericBank) node2,(int)(Math.random()*10),Color.BLACK));
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

        for (Node node:graph.getNodes())
        {
            for (Edge edge:node.getConnections())
                ((Road)edge).draw(graphics2D);

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


            y.addPoint((int) ((e.getX()- 2)), (int) ((e.getY() - 2)));

            y.addPoint((int) ((e.getX() + 2)), (int) ((e.getY() + 2)));


            for (Node node:graph.getNodes())
            {

                if (((GenericBank)node).getHitBox().intersects(y.getBounds2D()))
                    System.out.println(node.toString());

                for (Edge edge:node.getConnections())
                  if (((Road)edge).getHitBox().intersects(y.getBounds()))
                  {
                      ((Road)edge).setColor(Color.RED);
                      System.out.println(edge.toString());
                  }




            }

        }

    }


    public void computePathsVisulation(Node source,int delay) throws InterruptedException {
        source.setMinDistance(0);

        PriorityQueue<Node> vertexQueue = new PriorityQueue<>();

        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Node u = vertexQueue.poll();

            for (Edge e : u.getConnections())
            {
                Node v = e.getEnd();
                double weight = e.getWeight();
                double distanceThroughU = u.getMinDistance() + weight;
                if (distanceThroughU < v.getMinDistance()) {
                    vertexQueue.remove(v);
                    v.setMinDistance( distanceThroughU) ;
                    v.setPrev(u);
                    vertexQueue.add(v);

                    timer.wait(delay);
                }
            }
        }

    }



}
