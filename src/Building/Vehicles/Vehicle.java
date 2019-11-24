package Building.Vehicles;

import Building.GenericBank;
import Building.IDrawAble;
import Building.Road;
import DataStructure.Order;
import disMath.Edge;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Building.Vehicles
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 16.10.19
 */
public class Vehicle implements IDrawAble {

    private boolean selected=false;

    private double wait=0;

    private double unLoadTime=0.10;

    private double depoTime=0.30;

    private Point2D Position;

    private double speed;

    private int size=80;

    private Color color = Color.BLUE;

    private LinkedList<Edge> path=new LinkedList<>();

    private ArrayList<Order> orders=new ArrayList<>();

    private Edge currentRoad;

    private int iterator=0;

    private boolean backing=false;

    private boolean onRoad=false;

    private int moneyAmount=0;

    double reamingDistance;

    public Vehicle(Point2D position, double speed) {
        Position = position;
        this.speed = speed;
    }

    public Vehicle(Point2D position, double speed, int size) {
        Position = position;
        this.speed = speed;
        this.size = size;
    }

    public Vehicle(Point2D position, double speed, int size, Color color) {
        Position = position;
        this.speed = speed;
        this.size = size;
        this.color = color;
    }

    public double getAngle(Point2D start, Point2D end) {
        double angle = (float) Math.toDegrees(Math.atan2(start.getY() - end.getY(), start.getX() - end.getX()));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    public void move(double time)
    {


        if (getWait()<=0) {
            if(currentRoad!=null&&onRoad)   {
                if(!backing) {
                    if (reamingDistance<0)
                    {
                        if((iterator==0))
                        {
                            ((GenericBank)currentRoad.getStart()).vehicleArrive(this);

                            this.setPath(((GenericBank)path.getLast().getEnd()).getPathToBank((GenericBank)currentRoad.getStart()));



                            backing=true;

                            return;
                        }
                        else {

                            for (Order order:orders)
                            {
                                if(order.getSender().equals(currentRoad.getStart()))
                                 ((GenericBank)currentRoad.getStart()).vehicleArrive(this,order.getMoneyAmount());

                            }

                            iterator--;
                            if (iterator<0)
                                return;

                            setCurrentRoad(path.get(iterator));
                        }
                    }

                    double ang = getAngle(((GenericBank)currentRoad.getStart()).getPosition(), ((GenericBank)currentRoad.getEnd()).getPosition());
                    double sin = Math.sin(Math.toRadians(ang)) ;
                    double cos = Math.cos(Math.toRadians(ang)) ;

                    Point2D tmpPoint= new Point2D.Double(
                            this.getPosition().getX()+getSpeed()*time * cos,
                            this.getPosition().getY()+getSpeed()*time *sin);


                    reamingDistance-=tmpPoint.distance(this.getPosition());


                    this.setPosition(tmpPoint);


                }
                else
                {
                    if (reamingDistance<0)
                    {
                        if((iterator==0))
                        {
                            onRoad=false;
                            setWait(depoTime);
                            return;
                        }
                        else {

                            iterator--;
                            if (iterator<0)
                                return;
                            setCurrentRoad(path.get(iterator));

                        }
                    }

                    double ang = getAngle(((GenericBank)currentRoad.getStart()).getPosition(), ((GenericBank)currentRoad.getEnd()).getPosition());

                    double sin = Math.sin(Math.toRadians(ang)) ;
                    double cos = Math.cos(Math.toRadians(ang)) ;

                    Point2D tmpPoint= new Point2D.Double(
                            this.getPosition().getX()+getSpeed()*time *cos,
                            this.getPosition().getY()+getSpeed()*time *sin);



                    reamingDistance-=tmpPoint.distance(this.getPosition());


                    this.setPosition(tmpPoint);


                }
            }
        }else setWait(wait-time);


    }

    public Color getColor() {
        return color;
    }

    @Override
    public Shape getHitBox() {
        return null;
    }

    @Override
    public void setDefaultColor() {

    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        if(onRoad){
        graphics2D.setColor(this.getColor());

        graphics2D.fillOval((int)(this.getPosition().getX()-(size/2)),(int)(this.getPosition().getY()-(size/2)),(int)((size)),(int)((size)));

        if (selected)
        {
            String str = this.toString();
            Color textColor = Color.WHITE;
            Color bgColor = Color.BLACK;
            int x = (int)this.getPosition().getX();
            int y = (int)this.getPosition().getY();

            FontMetrics fm = graphics2D.getFontMetrics();
            Rectangle2D rect = fm.getStringBounds(str, graphics2D);

            graphics2D.setColor(bgColor);
            graphics2D.fillRect(x,
                    y - fm.getAscent(),
                    (int) rect.getWidth(),
                    (int) rect.getHeight());

            graphics2D.setColor(textColor);
            graphics2D.drawString(str, x, y);


        }
}
    }

    public Point2D getPosition() {
        return Position;
    }

    public void setPosition(Point2D position) {
        Position = position;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public LinkedList<Edge> getPath() {
        return path;
    }

    public void setPath(LinkedList<Edge> path) {

        onRoad=true;
        backing=false;
        iterator=path.size()-1;

        if(path.size()>0)
        {
           setPosition(((GenericBank) path.getLast().getEnd()).getPosition());
           setCurrentRoad(path.getLast());
        }


        for (Edge edge:path)
            ((Road)edge).setVisible(true);



        this.path = path;
    }

    public Edge getCurrentRoad() {
        return currentRoad;
    }

    public void setCurrentRoad(Edge currentRoad) {

        setPosition((((GenericBank)currentRoad.getEnd())).getPosition());


        setReamingDistance(currentRoad.getWeight());
        this.currentRoad = currentRoad;
    }

    public double getReamingDistance()
    {
        return reamingDistance;
    }

    public void setReamingDistance(double reamingDistance) {
        this.reamingDistance = reamingDistance;
    }

    public int unloadMoney()
    {
        int tmp =moneyAmount;
        moneyAmount=0;
        setWait(unLoadTime);
        return tmp;
    }

    public int unloadMoney(int Amount)
    {
        int tmp =Amount;
        moneyAmount-=Amount;
        setWait(unLoadTime);
        return tmp;
    }

    public boolean isOnRoad() {
        return onRoad;
    }

    public void setOnRoad(boolean onRoad) {
        this.onRoad = onRoad;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public double getWait() {
        return wait;
    }

    public void setWait(double wait) {
        if (wait>0)
        this.wait = wait;
        else this.wait=0;
    }


    @Override
    public String toString() {
        return "Vehicle" +
                "moneyAmount=" + moneyAmount +
                '}';
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
