package Vehicles;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Vehicles
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 16.10.19
 */
public abstract class Vehicle {

    private Color color = Color.green;

    private Point2D Position;

    private int size=10;

    private double speed;

    void draw(Graphics2D graphics2D)
    {

        graphics2D.setStroke(new BasicStroke(getSize()));

        graphics2D.setColor(getColor());

        graphics2D.drawLine((int)getPosition().getX()-1,(int)getPosition().getX()-1,(int)getPosition().getX()+1,(int)getPosition().getX()+1);
        graphics2D.drawLine((int)getPosition().getX()+1,(int)getPosition().getX()-1,(int)getPosition().getX()-1,(int)getPosition().getX()+1);

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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
}
