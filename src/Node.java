import java.awt.Color;

public class Node {

    public int x, y;
    public static int radius = 60;
    public Color nodeColor = Color.RED;
    public Color selectedNodeColor = Color.GRAY;
    public Color startEndNodeColor = Color.PINK;

    public int value;

    public int fontSize = radius / 2;
    public String fontName = "Arial";

    public Node(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public boolean isClicked(int mouseX, int mouseY) {
        return Math.abs(x - mouseX) < radius / 2 && Math.abs(y - mouseY) < radius / 2;
    }
}
