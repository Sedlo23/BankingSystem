package disMath;
import java.util.ArrayList;
import java.util.List;


public class Node implements Comparable{

    private Object carriedObject;

    private List<Edge> connections=new ArrayList<>();

    private double minDistance = Double.POSITIVE_INFINITY;

    private Node prev;

    public Node(String carriedObject) {
        this.carriedObject = carriedObject;
    }

    public void addEdge(Node node,int weight)
    {
        this.getConnections().add(new Edge(this,node,weight));
    }

    private Object getCarriedObject() {
        return carriedObject;
    }

    public void setCarriedObject(String carriedObject) {
        this.carriedObject = carriedObject;
    }

    public List<Edge> getConnections() {
        return connections;
    }

    public void setConnections(List<Edge> connections) {
        this.connections = connections;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return this.getCarriedObject().toString();
    }

    @Override
    public int compareTo(Object o) {
        return Double.compare(minDistance, ((Node) o).getMinDistance());
    }
}
