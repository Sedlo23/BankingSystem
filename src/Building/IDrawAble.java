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
public interface IDrawAble {

    public void draw(Graphics2D graphics2D);

    public Point2D getPosition();

    public Color getColor();

    public Shape getHitBox();

}
