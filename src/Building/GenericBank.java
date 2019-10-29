package Building;

import Building.Vehicles.Vehicle;
import DataStructure.Order;
import disMath.Edge;
import disMath.Graph;
import disMath.Node;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Building
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 16.10.19
 */
public abstract class GenericBank extends Node implements IDrawAble, TreeModel
{
    private boolean moneyOnTheWay=false;

    private boolean QuickPathFinding=true;

    private Point2D position;

    private Color color;

    private boolean isSelected=false;

    private int minMoneyAmount;

    private int moneyAmount;

    private GenericBank responsibleBank;

    private ArrayList<GenericBank> dependingBanks =new ArrayList<>();

    private ArrayList<Vehicle> vehicleList=new ArrayList<>();

    private LinkedList<Edge> pathToRespBank;

    private Graph parentGraph;

    private int MoneySpending=1000;

    private LinkedList<Order> orderQueue=new LinkedList<>();

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

    public GenericBank(Point2D position, Color color,String name,int moneyAmount,Graph parentGraph,int minMoneyAmount) {
        super(name);
        this.position = position;
        this.color = color;
        this.moneyAmount=moneyAmount;
        this.parentGraph=parentGraph;
        this.minMoneyAmount=minMoneyAmount;
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
     if (!isSelected)
     {
      if (moneyAmount<minMoneyAmount)
        return Color.RED;
      else
          return Color.GREEN;
     }else
         return Color.YELLOW;

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

        if (this.responsibleBank!=null)
            this.responsibleBank.getDependingBanks().remove(this);

        this.responsibleBank = responsibleBank;

        responsibleBank.getDependingBanks().add(this);
    }

    public void bankingActivity(double time)
    {


        {  Random random=new Random();

            int ran=random.nextInt(MoneySpending);

            ran*=time;

            if(ran<moneyAmount)
                moneyAmount-=ran;

            if (minMoneyAmount>moneyAmount)
                if (!moneyOnTheWay)
                {
                    responsibleBank.getOrderQueue().add(new Order(minMoneyAmount*2,this));
                    moneyOnTheWay=true;
                }



            for (Vehicle vehicle:vehicleList)
            {

                if (!vehicle.isOnRoad())
                {
                    if (!orderQueue.isEmpty())
                        if(orderQueue.getFirst().getMoneyAmount()<moneyAmount)
                        {
                            vehicle.setPath(orderQueue.getFirst().getSender().getPathToRespBank());
                            vehicle.setMoneyAmount(orderQueue.getFirst().getMoneyAmount());
                            moneyAmount-=orderQueue.getFirst().getMoneyAmount();
                            orderQueue.remove(orderQueue.removeFirst());

                        }
                }
            } }







    }

    @Override
    public String toString() {
        return "{" +
                "moneyOnTheWay=" + moneyOnTheWay +
                ", minMoneyAmount=" + minMoneyAmount +
                ", moneyAmount=" + moneyAmount +
                '}';
    }

    public LinkedList<Edge> getPathToRespBank() {
        if (pathToRespBank==null){

            if (QuickPathFinding)
                parentGraph.computePathsQuick(responsibleBank,this);
            else
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

    public LinkedList<Order> getOrderQueue() {
        return orderQueue;
    }

    public ArrayList<GenericBank> getDependingBanks() {
        return dependingBanks;
    }

    public void setDependingBanks(ArrayList<GenericBank> dependingBanks) {
        this.dependingBanks = dependingBanks;
    }

    @Override
    public Object getRoot() {
        return responsibleBank;
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


    public boolean isMoneyOnTheWay() {
        return moneyOnTheWay;
    }

    public void setMoneyOnTheWay(boolean moneyOnTheWay) {
        this.moneyOnTheWay = moneyOnTheWay;
    }

    public int getMoneySpending() {
        return MoneySpending;
    }

    public void setMoneySpending(int moneySpending) {
        MoneySpending = moneySpending;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        for (GenericBank bank:getDependingBanks())
            bank.setSelected(selected);
    }
}
