package disMath;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


public class Graph {

    private int QuickFindDelay=10;

    public List<Node> getNodes() {
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

    public void computePaths(Node source) {

        for (Node node:nodes)
        {
            node.clearPath();

        }

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

    public void computePathsQuick(Node source,Node Destination) {

        boolean nodeFound=false;

        int delay=0;

        for (Node node:nodes)
        {
            node.clearPath();

        }

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

                    if (vertexQueue.peek().equals(Destination))
                        nodeFound=true;

                }
            }
            if (nodeFound) {
                if (delay == QuickFindDelay)
                {
                    return;
                }
                else
                    delay++;

            }
        }

    }

    public LinkedList<Edge> getShortens(Node source)
    {
         LinkedList<Edge> edges=new LinkedList<>();

         Node iterator=source;

         while ((iterator!=null&&!iterator.equals(iterator.getPrev())))
         {
                   if (iterator.getPrev()!=null)
                    edges.add(iterator.findPath(iterator.getPrev()));
                    iterator=iterator.getPrev();
         }

         return edges;

    }

    public int getQuickFindDelay() {
        return QuickFindDelay;
    }

    public void setQuickFindDelay(int quickFindDelay) {
        QuickFindDelay = quickFindDelay;
    }


}
