package disMath;

import java.util.ArrayList;
import java.util.List;


public class Node implements Comparable{

    private String name;

    private List<Edge> connections=new ArrayList<>();

    private double minDistance = Double.POSITIVE_INFINITY;

    private Node prev;

    public Node(String name) {
        this.name = name;
    }

    public void addEdge(Node node,int weight)
    {
        this.getConnections().add(new Edge(this,node,weight));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "{" +
                "name='" + name + '\'' +
                ", connections=" + connections.size() +
                ", minDistance=" + minDistance +
                '}';
    }

    public Edge findPath(Node destination)
    {
        for (Edge edge:getConnections())
        {
            if (edge.getEnd().equals(destination))
                return edge;

        }

        return null;
    }

    @Override
    public int compareTo(Object o) {
        return Double.compare(minDistance, ((Node) o).getMinDistance());
    }


    public void clearPath()
    {

        prev=null;

        minDistance=Double.POSITIVE_INFINITY;
    }
}
