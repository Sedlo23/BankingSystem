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

    private Boolean visible=false;

    private int stroke=3;

    public Road(GenericBank start, GenericBank end,int weight,Color color) {
        super(start,end,weight);
        this.color = color;

    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Shape getHitBox()
    {
        Polygon tmpPolygon =new Polygon();

        tmpPolygon.addPoint((int)((GenericBank)getStart()).getPosition().getX(),(int)((GenericBank)getStart()).getPosition().getY());
        tmpPolygon.addPoint((int)((GenericBank)getEnd()).getPosition().getX(),(int)((GenericBank)getEnd()).getPosition().getY());

        return  tmpPolygon;

    }

    @Override
    public void draw(Graphics2D graphics2D) {

        if (visible)
        {

            graphics2D.setColor(this.getColor());

            graphics2D.setStroke(new BasicStroke(getStroke()));

            graphics2D.draw(this.getHitBox());

        }
    }

    @Override
    public Point2D getPosition() {
        return null;
    }


    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return "Road{"+getStart().getName()+","+getEnd().getName()+","+getWeight()+"}";
    }

    @Override
    public void setDefaultColor() {
        this.setColor(Color.BLACK);
    }

    public int getStroke() {
        return stroke;
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
    }

    public void setDefaultStroke()
    {
        setStroke(3);
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}

