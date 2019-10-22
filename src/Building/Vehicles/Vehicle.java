package Building.Vehicles;

import Building.GenericBank;
import Building.IDrawAble;
import disMath.Edge;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;

/**
 * Building.Vehicles
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 16.10.19
 */
public class Vehicle implements IDrawAble {

    private Point2D Position;

    private double speed;

    private int size=80;

    private Color color = Color.BLUE;

    private LinkedList<Edge> path=new LinkedList<>();

    private Edge currentRoad;

    private int iterator=0;

    private boolean backing=false;

    private boolean onRoad=false;

    private int moneyAmount=0;

    int reamingDistance;

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
            if(currentRoad!=null&&onRoad)   {
                if(!backing) {
            if (reamingDistance<1)
                 {
                if((iterator==0))
                {
                    ((GenericBank)currentRoad.getStart()).vehicleArrive(this);
                    backing=true;
                    return;
                }
                else {
                    iterator--;
                    setCurrentRoad(path.get(iterator));
                }
       }

        double ang = getAngle(((GenericBank)currentRoad.getStart()).getPosition(), ((GenericBank)currentRoad.getEnd()).getPosition());
        double sin = Math.sin(Math.toRadians(ang)) ;
        double cos = Math.cos(Math.toRadians(ang)) ;

        this.setPosition(new Point2D.Double(
                this.getPosition().getX()+getSpeed()*time * cos,
                this.getPosition().getY()+getSpeed()*time *sin));

        reamingDistance-=getSpeed()*time;

                }else
                {
                    if (reamingDistance<1)
                    {
                        if((iterator==path.size()))
                        {
                            onRoad=false;
                            return;
                        }
                        else {
                            setCurrentRoad(path.get(iterator));
                            iterator++;

                        }
                    }

                    double ang = getAngle(((GenericBank)currentRoad.getEnd()).getPosition(), ((GenericBank)currentRoad.getStart()).getPosition());
                    double sin = Math.sin(Math.toRadians(ang)) ;
                    double cos = Math.cos(Math.toRadians(ang)) ;

                    this.setPosition(new Point2D.Double(
                            this.getPosition().getX()+getSpeed()*time * cos,
                            this.getPosition().getY()+getSpeed()*time *sin));

                    reamingDistance-=getSpeed()*time;


                }
}


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

        if(path.size()>0) {
           setPosition(((GenericBank) path.getLast().getEnd()).getPosition());
           setCurrentRoad(path.getLast());
       }

        this.path = path;
    }

    public Edge getCurrentRoad() {
        return currentRoad;
    }

    public void setCurrentRoad(Edge currentRoad) {
        if (!backing)
             setPosition((((GenericBank)currentRoad.getEnd())).getPosition());
        else
            setPosition((((GenericBank)currentRoad.getStart())).getPosition());

        setReamingDistance(currentRoad.getWeight());
        this.currentRoad = currentRoad;
    }

    public int getReamingDistance() {
        return reamingDistance;
    }

    public void setReamingDistance(int reamingDistance) {
        this.reamingDistance = reamingDistance;
    }

    public int unloadMoney()
    {
        int tmp =moneyAmount;
        moneyAmount=0;
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
}
