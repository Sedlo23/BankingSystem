package Building;

import Building.Vehicles.Vehicle;
import DataStructure.Order;
import disMath.Edge;
import disMath.Graph;
import disMath.Node;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

/**
 * Building
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 16.10.19
 */
public abstract class GenericBank extends Node implements IDrawAble
{
    private boolean moneyOnTheWay=false;

    private Point2D position;

    private Color color;

    private String name;

    private int moneyAmount;

    private GenericBank responsibleBank;

    private ArrayList<Vehicle> vehicleList=new ArrayList<>();

    private LinkedList<Edge> pathToRespBank;

    private Graph parentGraph;

    private Stack<Order> orderQueue=new Stack<>();

    public GenericBank(Point2D position, Color color,String name) {
        super(name);
        this.position = position;
        this.color = color;
        this.moneyAmount=10000;
    }

    public GenericBank(Point2D position, Color color,String name,int moneyAmount) {
        super(name);
        this.position = position;
        this.color = color;
        this.moneyAmount=moneyAmount;
    }

    public GenericBank(Point2D position, Color color,String name,int moneyAmount,Graph parentGraph) {
        super(name);
        this.position = position;
        this.color = color;
        this.moneyAmount=moneyAmount;
        this.parentGraph=parentGraph;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public Color getColor() {
      if (moneyAmount<200)
        return Color.RED;
      else
          return Color.GREEN;

    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void vehicleArrive(Vehicle vehicle)
    {
        moneyAmount+=vehicle.unloadMoney();
        moneyOnTheWay=false;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public ArrayList<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public GenericBank getResponsibleBank() {
        return responsibleBank;
    }

    public void setResponsibleBank(GenericBank responsibleBank) {
        this.responsibleBank = responsibleBank;
    }

    public void bankingActivity(double time)
    {
        Random random=new Random();

        int ran=random.nextInt(100);

        if(ran<moneyAmount)
            moneyAmount-=ran;
        else
            if (!moneyOnTheWay)
            {
                responsibleBank.getOrderQueue().add(new Order(10000,this));
                moneyOnTheWay=true;
            }



        for (Vehicle vehicle:vehicleList)
        {

        if (!vehicle.isOnRoad())
        {
            if (!orderQueue.isEmpty())
                if(orderQueue.lastElement().getMoneyAmount()<moneyAmount)
                {
                    vehicle.setPath(orderQueue.lastElement().getSender().getPathToRespBank());
                    vehicle.setMoneyAmount(orderQueue.lastElement().getMoneyAmount());
                    moneyAmount-=orderQueue.lastElement().getMoneyAmount();
                    orderQueue.remove(orderQueue.lastElement());

                }
        }
        }
    }

    @Override
    public String toString() {
        return " "+moneyAmount+"$, Dep:"+getResponsibleBank().getName() ;
    }

    public LinkedList<Edge> getPathToRespBank() {
        if (pathToRespBank==null){
            parentGraph.computePaths(responsibleBank);
            setPathToRespBank(parentGraph.getShortens(this));
        }
            return pathToRespBank;

    }

    public void setPathToRespBank(LinkedList<Edge> pathToRespBank) {

        this.pathToRespBank = pathToRespBank;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public void setVehicleList(ArrayList<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public Graph getParentGraph() {
        return parentGraph;
    }

    public void setParentGraph(Graph parentGraph) {
        this.parentGraph = parentGraph;
    }

    public Stack<Order> getOrderQueue() {
        return orderQueue;
    }


}
