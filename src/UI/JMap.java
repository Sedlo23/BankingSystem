package UI;

import Building.CentralBank;
import Building.GenericBank;
import Building.LocalBank;
import Building.Road;
import Building.Vehicles.Vehicle;
import disMath.Edge;
import disMath.Graph;
import disMath.Node;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * UI
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 16.10.19
 */
public class JMap extends JPanel implements TreeModel {


    private boolean drawRoad=false;

    private boolean visibility=true;

    private Graph graph;

    private double scale=1;

    private GenericBank rootBank;


    public JMap()
    {
        this(new Graph());


    }

    public JMap(Graph graph)
    {
        super();

        this.graph=graph;

       System.out.println( this.isOptimizedDrawingEnabled());


    }

    @Deprecated
    @SuppressWarnings("Magic numbers, name genarator etc ... just for the sake of being here")
    public void populateGraph()  {

        ArrayList<String> arr = new ArrayList<String>();

        arr.add("nojono");

        File f = new File("out/production/BankingSystem/Data/names.txt");
        if(f.exists() && !f.isDirectory())
        try (BufferedReader br = new BufferedReader(new FileReader("out/production/BankingSystem/Data/names.txt")))
        {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                arr.add(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Random ran=new Random();


       CentralBank centralBank= new CentralBank(
                new Point2D.Double(256,256)
                ,Color.MAGENTA
                ,arr.get(ran.nextInt(arr.size()))
                ,25
        );



        for (int x=0;x<50;x++)
            centralBank.getVehicleList().add(new Vehicle(new Point2D.Double(),100,5,Color.BLUE.darker()));

       setRootBank(centralBank);

       getGraph().addNode(centralBank);

       centralBank.setParentGraph(getGraph());

       for (int x=0;x<10;x++)
           for (int y=0;y<10;y++)
           {
               if (y==4&&x==4)
                   continue;

               LocalBank Bank = new LocalBank(new Point2D.Double(64*x,64*y),Color.RED,arr.get(ran.nextInt(arr.size()))
                       ,15
                       ,getGraph()
                       ,30000
                       ,20);

              Bank.setResponsibleBank(centralBank);


              getGraph().addNode(Bank);

              Bank.setParentGraph(getGraph());



           }




       for (GenericBank node1:centralBank.getDependingBanks())
       {
           for (int x=0;x<5;x++)
               node1.getVehicleList().add(new Vehicle(new Point2D.Double(),100,5,Color.ORANGE));


           for (int y=0;y<7;y++)
           for (int x=0;x<7;x++)   {

           LocalBank Bank = new LocalBank(new Point2D.Double(node1.getPosition().getX()+(x-4)*8+ran.nextInt(10)-5,node1.getPosition().getY()+(y-4)*8+ran.nextInt(10)-5)
                   ,Color.RED,
                   arr.get(ran.nextInt(arr.size())),2000,getGraph(),1000,7);

           Bank.setResponsibleBank(node1);


           getGraph().addNode(Bank);

           Bank.setParentGraph(getGraph());
           }
       }


        //==================================================================
        for (Node node1:this.graph.getNodes())
        {

            int i=5;
            while (node1.getConnections().size()<14)  {


            for (Node node2:this.graph.getNodes())
            {

                  if(!node1.equals(node2)&&!node1.getConnections().contains(node2))
                    if (((GenericBank)node1).getPosition().distance(((GenericBank)node2).getPosition())<i)
                    {
                        node1.getConnections().add(new Road(((GenericBank)node1),((GenericBank)node2),(int)((GenericBank)node1).getPosition().distance(((GenericBank)node2).getPosition()),Color.BLACK));
                        node2.getConnections().add(new Road(((GenericBank)node2),((GenericBank)node1),(int)((GenericBank)node2).getPosition().distance(((GenericBank)node1).getPosition()),Color.BLACK));

            }
            }
            i+=5;
            }
        }
        //==================================================================



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


    @Override
    public void paint(Graphics g) {

        if (isVisible()) {

        super.paint(g);

        Graphics2D graphics2D=(Graphics2D)g;

        computeModelDimensions(graphics2D);


        for (Node node:graph.getNodes())
        {
            if(drawRoad)
            for (Edge edge:node.getConnections())
                ((Road)edge).draw(graphics2D);

        }

        for (Node node:graph.getNodes())
        {

            ((GenericBank) node).draw(graphics2D);

        }

        for (Node node:graph.getNodes())
        {

            for (Vehicle vehicle:  ((GenericBank) node).getVehicleList())
                vehicle.draw(graphics2D);
        }

        }




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

                }

                for (Edge edge:node.getConnections())
                  if (((Road)edge).getHitBox().intersects(y.getBounds()))
                  {
                      System.out.println(edge.toString());
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

    public GenericBank getRootBank() {
        return rootBank;
    }

    public void setRootBank(GenericBank rootBank) {
        this.rootBank = rootBank;
    }

    @Override
    public Object getRoot() {
        return getRootBank();
    }

    @Override
    public Object getChild(Object o, int i) {
        return ((GenericBank)o).getDependingBanks().get(i);
    }

    @Override
    public int getChildCount(Object o) {
        return((GenericBank)o).getDependingBanks().size();
    }

    @Override
    public boolean isLeaf(Object o) {
        return ((GenericBank)o).getDependingBanks().isEmpty();
    }

    @Override
    public void valueForPathChanged(TreePath treePath, Object o) {

    }

    @Override
    public int getIndexOfChild(Object o, Object o1) {
        return ((GenericBank)o).getDependingBanks().indexOf(o1);
    }

    @Override
    public void addTreeModelListener(TreeModelListener treeModelListener) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener treeModelListener) {

    }

    public int outOfMoneyBanks()
    {
        int tmp =0;

        for (Node node:graph.getNodes())
        {
                if (  ((GenericBank) node).getColor().equals(Color.RED))
            tmp++;

        }
        return tmp;
    }

    public int onRoadCars()
    {
        int tmp =0;

        for (Node node:graph.getNodes())
        {
            for (Vehicle vehicle:((GenericBank) node).getVehicleList())
            {
                 if (vehicle.isOnRoad())
                    tmp++;
            }
        }
        return tmp;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isDrawRoad() {
        return drawRoad;
    }

    public void setDrawRoad(boolean drawRoad) {
        this.drawRoad = drawRoad;
    }
}
