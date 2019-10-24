package Building;

import disMath.Graph;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Building
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 16.10.19
 */
public class LocalBank extends GenericBank {





    private int size;

    public LocalBank(Point2D position, Color color, String name, int size) {
        super(position, color, name);
        this.size = size;
    }

    public LocalBank(Point2D position, Color color, String name, int moneyAmount, int size) {
        super(position, color, name, moneyAmount);
        this.size = size;
    }

    public LocalBank(Point2D position, Color color, String name, int moneyAmount, Graph parentGraph, int minMoneyAmount, int size) {
        super(position, color, name, moneyAmount, parentGraph,minMoneyAmount);
        this.size = size;
    }


    @Override
    public void draw(Graphics2D graphics2D)
    {
        graphics2D.setColor(this.getColor());

        graphics2D.fillOval((int)(this.getPosition().getX()-(size/2)),(int)(this.getPosition().getY()-(size/2)),(int)((size)),(int)((size)));


        //graphics2D.drawString(this.toString(),(int)(this.getPosition().getX()-(size/2)),(int)(this.getPosition().getY()));

    }

    @Override
    public Point2D getPosition() {
        return super.getPosition();
    }

    public void setPosition(Point2D position) {
        super.setPosition(position);
    }

    @Override
    public Color getColor() {
        return super.getColor();
    }

    @Override
    public Shape getHitBox() {
        return new Ellipse2D.Double((int)(this.getPosition().getX()-(size/2)),(int)(this.getPosition().getY()-(size/2)),(int)((size)),(int)((size))).getBounds2D();
    }

    @Override
    public void setDefaultColor() {
            this.setColor(Color.YELLOW);
    }

    public void setColor(Color color) {
        super.setColor(color);
    }

    @Override
    public String toString() {
        return getName()+super.toString();
    }
}
