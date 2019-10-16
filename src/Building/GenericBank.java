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
public abstract class GenericBank implements IDrawAble
{
    private Point2D position;

    private Color color;

    public GenericBank(Point2D position, Color color) {
        this.position = position;
        this.color = color;
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
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
