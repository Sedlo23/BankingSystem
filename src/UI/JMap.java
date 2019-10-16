package UI;

import Building.GenericBank;
import Building.LocalBank;
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
                this.graph.addNode(new Node(new LocalBank(new Point2D.Double(100+Math.random()*1000,10+Math.random()*1000),Color.YELLOW)));

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
            ((GenericBank)node.getCarriedObject()).draw(graphics2D);


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }




}
