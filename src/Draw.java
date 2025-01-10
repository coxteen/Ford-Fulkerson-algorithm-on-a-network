import java.awt.*;
import java.util.ArrayList;

public class Draw {

    private static void drawArrows(Graphics2D g2d, Edge edge) {
        int deltaX = edge.endNode.x - edge.startNode.x;
        int deltaY = edge.endNode.y - edge.startNode.y;

        double angle = Math.atan2(deltaY, deltaX);

        double magnitude = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double[] directionVector = {deltaX / magnitude, deltaY / magnitude};

        int xArrow1 = (int) (edge.endNode.x - edge.ARROW_LENGTH * Math.cos(angle - Math.PI / 6));
        int yArrow1 = (int) (edge.endNode.y - edge.ARROW_LENGTH * Math.sin(angle - Math.PI / 6));
        int xArrow2 = (int) (edge.endNode.x - edge.ARROW_LENGTH * Math.cos(angle + Math.PI / 6));
        int yArrow2 = (int) (edge.endNode.y - edge.ARROW_LENGTH * Math.sin(angle + Math.PI / 6));

        int differenceX = (int) (directionVector[0] * Node.radius / 2);
        int differenceY = (int) (directionVector[1] * Node.radius / 2);

        g2d.setColor(edge.edgeColor);

        g2d.drawLine(
                edge.endNode.x - differenceX,
                edge.endNode.y - differenceY,
                xArrow1 - differenceX,
                yArrow1 - differenceY
        );
        g2d.drawLine(
                edge.endNode.x - differenceX,
                edge.endNode.y - differenceY,
                xArrow2 - differenceX,
                yArrow2 - differenceY
        );
    }

    private static void drawCost(Graphics2D g2d, Edge edge) {
        g2d.setColor(edge.edgeColor);
        int middleX = (edge.endNode.x + edge.startNode.x) / 2;
        int middleY = (edge.endNode.y + edge.startNode.y) / 2;
        g2d.fillRoundRect(
                middleX - edge.rectWidth / 2,
                middleY - edge.rectHeight / 2,
                edge.rectWidth,
                edge.rectHeight,
                edge.rectRadius,
                edge.rectRadius
        );

        Font font = new Font(edge.costFont, Font.BOLD, edge.costFontSize);
        g2d.setColor(edge.costFontColor);
        g2d.setFont(font);
        if (edge.cost < 10) {
            g2d.drawString(String.valueOf(edge.cost), middleX - 3, middleY + 4);
        }
        else {
            g2d.drawString(String.valueOf(edge.cost), middleX - 6, middleY + 3);
        }
    }

    private static void drawEdge(Graphics2D g2d, Edge edge) {
        g2d.setColor(edge.edgeColor);
        g2d.setStroke(new BasicStroke(edge.lineWidth));
        g2d.drawLine(edge.startNode.x, edge.startNode.y, edge.endNode.x, edge.endNode.y);
    }

    private static void drawNode(Graphics2D g2d, Node node, Graph graph) {
        if (node == graph.selectedNode) {
            g2d.setColor(node.selectedNodeColor);
        }
        else if (node == graph.startNode || node == graph.endNode) {
            g2d.setColor(node.startEndNodeColor);
        }
        else {
            g2d.setColor(node.nodeColor);
        }
        g2d.fillOval(node.x - Node.radius / 2, node.y - Node.radius / 2, Node.radius, Node.radius);
    }

    private static void drawNumber(Graphics2D g2d, Node node) {
        Font font = new Font(node.fontName, Font.BOLD, node.fontSize);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        if (node.value < 10) {
            g2d.drawString(String.valueOf(node.value), node.x - 8, node.y + 9);
        }
        else {
            g2d.drawString(String.valueOf(node.value), node.x - 16, node.y + 9);
        }
    }

    private static void drawButton(Graphics2D g2d, RadioButton button) {
        g2d.setStroke(new BasicStroke(button.strokeWidth));
        g2d.drawOval(button.bX - button.radius / 2, button.bY - button.radius / 2, button.radius, button.radius);

        if (button.selected) {
            g2d.setColor(Color.BLACK);
            g2d.fillOval(
                    button.bX - button.radius / 4,
                    button.bY - button.radius / 4,
                    button.radius / 2,
                    button.radius / 2
            );
        }

        g2d.setFont(new Font(button.font, Font.BOLD, button.fontSize));
        g2d.setColor(Color.BLACK);
        g2d.drawString(button.label, button.lX, button.lY);
    }

    public static void draw(Graphics2D g2d, Graph graph, RadioButton button) {
        for (Edge edge : graph.edges) {
            drawEdge(g2d, edge);
            drawArrows(g2d, edge);
            drawCost(g2d, edge);
        }
        for (Node node : graph.nodes) {
            drawNode(g2d, node, graph);
            drawNumber(g2d, node);
        }
        drawButton(g2d, button);
    }
}
