import java.awt.Color;

public class Edge {

    public Node startNode, endNode;

    public int cost;
    public int costFontSize = 13;
    public String costFont = "Arial";

    public int rectWidth = 23;
    public int rectHeight = 23;
    public int rectRadius = 3;

    public Color edgeColor = Color.BLACK;
    public int lineWidth = 3;
    public Color costFontColor = Color.WHITE;

    public final int ARROW_LENGTH = 15;

    public Edge(Node start, Node end, int cost) {
        this.startNode = start;
        this.endNode = end;
        this.cost = cost;
    }

    public void markEdge() {
        lineWidth = 6;
        edgeColor = Color.PINK;
        costFontColor = Color.BLACK;
    }

    public void resetEdge() {
        lineWidth = 3;
        edgeColor = Color.BLACK;
        costFontColor = Color.WHITE;
    }
}
