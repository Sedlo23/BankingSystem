package Building;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Building
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 16.10.19
 */
public class LocalBank extends GenericBank {

    private int size=60;

    public LocalBank(Point2D position, Color color) {
        super(position, color);
    }

    @Override
    public void draw(Graphics2D graphics2D)
    {

        graphics2D.setColor(this.getColor());
        graphics2D.fillOval((int)(this.getPosition().getX()-(size/2)),(int)(this.getPosition().getY()-(size/2)),(int)((size/2)),(int)((size/2)));
        System.out.println("-");

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

    public void setColor(Color color) {
        super.setColor(color);
    }
}
