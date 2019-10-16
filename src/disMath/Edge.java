package disMath;

class Edge {

    private Node start;

    private Node end;

    private int weight;

    private Object carriedObject;

    public Edge(Node start, Node end, int weight) {
        this.setStart(start);
        this.setEnd(end);
        this.setWeight(weight);
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Object getCarriedObject() {
        return carriedObject;
    }

    public void setCarriedObject(Object carriedObject) {
        this.carriedObject = carriedObject;
    }

    @Override
    public String toString() {
        return this.getStart()+" -> ( "+this.getWeight()+" ) -> "+this.getEnd();
    }
}
