package DataStructure;

/**
 * DataStructure
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 17.10.19
 */
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class Vector2D {
    double x;
    double y;

    public Vector2D(double var1, double var3) {
        this.x = var1;
        this.y = var3;
    }

    public Vector2D(Point2D var1) {
        this.x = var1.getX();
        this.y = var1.getY();
    }

    public Vector2D(Point2D var1, Point2D var2) {
        this.x = var2.getX() - var1.getX();
        this.y = var2.getY() - var1.getY();
    }

    public Vector2D(Vector2D var1) {
        this.x = var1.x;
        this.y = var1.y;
    }

    public Vector2D ortho() {
        return new Vector2D(this.y, -this.x);
    }

    public Vector2D norm() {
        double var1 = this.length();
        return new Vector2D(this.x / var1, this.y / var1);
    }

    public void add(Vector2D var1) {
        this.x += var1.x;
        this.y += var1.y;
    }

    public void add(Point2D var1) {
        this.x += var1.getX();
        this.y += var1.getY();
    }

    public void mult(double var1) {
        this.x *= var1;
        this.y *= var1;
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public void normalize() {
        double var1 = this.length();
        this.x /= var1;
        this.y /= var1;
    }

    public Point2D toPoint2D() {
        return new Double(this.x, this.y);
    }

    public static Vector2D sub(Vector2D var0, Vector2D var1) {
        return new Vector2D(var0.x - var1.x, var0.y - var1.y);
    }

    public static Vector2D add(Vector2D var0, Vector2D var1) {
        return new Vector2D(var0.x - var1.x, var0.y - var1.y);
    }
}
