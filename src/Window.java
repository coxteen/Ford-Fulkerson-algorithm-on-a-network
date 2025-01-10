import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Window extends JPanel implements MouseListener, MouseMotionListener {

    private final Graph graph = new Graph();

    RadioButton button = new RadioButton(30, 30, "Ford-Fulkerson", 60, 36);

    private boolean dragging = false;
    private Node draggedNode = null;

    private int dragOffsetX, dragOffsetY;
    private int initialPositionX, initialPositionY;

    public Window() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Draw.draw(g2d, graph, button);
    }

    private int insertCost() {
        String cost = JOptionPane.showInputDialog(
                null,
                "Enter flux : ",
                "Input box",
                JOptionPane.PLAIN_MESSAGE
        );
        return Integer.parseInt(cost);
    }

    private void leftClickAction(MouseEvent e){
        if (button.isClicked(e.getX(), e.getY())) {
            if (!button.selected) {
                if (graph.startNode == null || graph.endNode == null) {
                    JOptionPane.showMessageDialog(
                            null,
                            "You need to select a start and an end node!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                FordFulkerson fordFulkerson = new FordFulkerson(graph);
                int maxFlow = fordFulkerson.findMaxFlow(graph.startNode, graph.endNode);
                ArrayList<Edge> flowEdges = fordFulkerson.getMaxFlowEdges();
                graph.markSelectedEdges(flowEdges);
                JOptionPane.showMessageDialog(
                        null,
                        maxFlow,
                        "Maximum flow",
                        JOptionPane.PLAIN_MESSAGE
                );

            }
            else {
                graph.resetSelectedEdges();
                graph.selectedNode = null;
                graph.startNode = null;
                graph.endNode = null;
            }
            button.switchButtonState();
            return;
        }
        for (Node node : graph.nodes) {
            if (node.isClicked(e.getX(), e.getY())) {
                if (graph.selectedNode == null) {
                    graph.selectedNode = node;
                } else if (node != graph.selectedNode) {
                    graph.addEdge(graph.selectedNode, node, insertCost());
                    graph.selectedNode = null;
                } else {
                    graph.selectedNode = null;
                }
                return;
            }
        }
        graph.addNode(e.getX(), e.getY());
        graph.selectedNode = null;
    }

    private void scrollClickAction(MouseEvent e){
        for (Node node : graph.nodes) {
            if (node.isClicked(e.getX(), e.getY())) {
                if (graph.startNode == null) {
                    graph.startNode = node;
                    return;
                }
                else if (graph.endNode == null && graph.startNode != node) {
                    graph.endNode = node;
                    return;
                }
            }
        }
    }

    private void rightClickAction(MouseEvent e){
        for (Node node : graph.nodes) {
            if (node.isClicked(e.getX(), e.getY())) {
                graph.deleteNode(node);
                return;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClickAction(e);
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            scrollClickAction(e);
        }
        else if (e.getButton() == MouseEvent.BUTTON3) {
            rightClickAction(e);
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (Node node : graph.nodes) {
            if (node.isClicked(e.getX(), e.getY())) {
                dragging = true;
                draggedNode = node;
                dragOffsetX = e.getX() - node.x;
                dragOffsetY = e.getY() - node.y;

                initialPositionX = node.x;
                initialPositionY = node.y;
                return;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (dragging && draggedNode != null && graph.isOverlapping(draggedNode)) {
            draggedNode.x = initialPositionX;
            draggedNode.y = initialPositionY;
        }
        dragging = false;
        draggedNode = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragging && draggedNode != null) {
            draggedNode.x = e.getX() - dragOffsetX;
            draggedNode.y = e.getY() - dragOffsetY;
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
}
