package Building;

import disMath.Edge;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Building
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 16.10.19
 */
public class Road extends Edge implements IDrawAble {

    private Color color;

    private Integer x1,x2,y1,y2;


    public Road(GenericBank start, GenericBank end,int weight,Color color) {
        super(start,end,weight);
        this.color = color;
        x1=(int)start.getPosition().getX();
        y1=(int)start.getPosition().getY();
        x2 =(int)end.getPosition().getX();
        y2=(int)end.getPosition().getY();

    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Shape getHitBox()
    {
        Polygon tmpPolygon =new Polygon();

        tmpPolygon.addPoint(x1,y1);
        tmpPolygon.addPoint(x2,y2);

        return  tmpPolygon;

    }

    @Override
    public void draw(Graphics2D graphics2D) {

        graphics2D.setColor(this.getColor());

       graphics2D.draw(this.getHitBox());


    }

    @Override
    public Point2D getPosition() {
        return new Point2D.Double(((x1+x2)/2),((y1+y2))/2);
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return "Road{"+getStart().getName()+","+getEnd().getName()+","+getWeight()+"}";
    }
}
