package Building;

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
public class CentralBank extends GenericBank {

    private final int priority=2;

    private int size=200;

    public CentralBank(Point2D position, Color color) {
        super(position, color,"NONE");
    }

    public CentralBank(Point2D position, Color color, String name) {
        super(position, color,name);
    }

    @Override
    public void draw(Graphics2D graphics2D)
    {
        graphics2D.setColor(this.getColor());

        graphics2D.fillOval((int)(this.getPosition().getX()-(size/2)),(int)(this.getPosition().getY()-(size/2)),(int)((size)),(int)((size)));

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
            this.setColor(Color.BLUE);
    }

    public void setColor(Color color) {
        super.setColor(color);
    }

    @Override
    public String toString() {
        return "LocalBank"+super.toString();
    }


}
