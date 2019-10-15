package disMath;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


class Graph {

    private List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node)
    {
        this.getNodes().add(node);
    }

    private List<Node> nodes=new ArrayList<>();

    public void computePaths(Node source)
    {
        source.setMinDistance(0);

        PriorityQueue<Node> vertexQueue = new PriorityQueue<>();

        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Node u = vertexQueue.poll();

            for (Edge e : u.getConnections())
            {
                Node v = e.getEnd();
                double weight = e.getWeight();
                double distanceThroughU = u.getMinDistance() + weight;
                if (distanceThroughU < v.getMinDistance()) {
                    vertexQueue.remove(v);

                    v.setMinDistance( distanceThroughU) ;
                    v.setPrev(u);
                    vertexQueue.add(v);
                }
            }
        }

    }









}
