import java.util.*;

public class FordFulkerson {

    private final Graph graph;
    private final Map<Edge, Integer> flow;
    private final Map<Node, Map<Node, Integer>> residualGraph;

    public FordFulkerson(Graph graph) {
        this.graph = graph;
        this.flow = new HashMap<>();
        this.residualGraph = new HashMap<>();

        for (Edge edge : graph.edges) {
            flow.put(edge, 0);
        }
    }

    private void initializeResidualGraph() {

        for (Edge edge : graph.edges) {
            residualGraph.putIfAbsent(edge.startNode, new HashMap<>());
            residualGraph.get(edge.startNode).put(edge.endNode, edge.cost);

            residualGraph.putIfAbsent(edge.endNode, new HashMap<>());
            residualGraph.get(edge.endNode).put(edge.startNode, 0);
        }
    }

    private boolean bfs(Node source, Node sink, Map<Node, Node> parent) {
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();

        queue.add(source);
        visited.add(source);
        parent.put(source, null);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (Map.Entry<Node, Integer> neighborEntry : residualGraph.getOrDefault(current, new HashMap<>()).entrySet()) {
                Node neighbor = neighborEntry.getKey();
                int residualCapacity = neighborEntry.getValue();

                if (!visited.contains(neighbor) && residualCapacity > 0) {
                    parent.put(neighbor, current);
                    visited.add(neighbor);
                    queue.add(neighbor);

                    if (neighbor == sink) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int findMaxFlow(Node source, Node sink) {
        initializeResidualGraph();
        Map<Node, Node> parent = new HashMap<>();
        int maxFlow = 0;

        while (bfs(source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (Node current = sink; current != source; current = parent.get(current)) {
                Node prev = parent.get(current);
                pathFlow = Math.min(pathFlow, residualGraph.get(prev).get(current));
            }

            for (Node current = sink; current != source; current = parent.get(current)) {
                Node prev = parent.get(current);

                residualGraph.get(prev).merge(current, -pathFlow, Integer::sum);
                residualGraph.get(current).merge(prev, pathFlow, Integer::sum);

                Node finalCurrent = current;
                Edge edge = graph.edges.stream()
                        .filter(e -> e.startNode == prev && e.endNode == finalCurrent)
                        .findFirst()
                        .orElse(null);

                if (edge != null) {
                    flow.merge(edge, pathFlow, Integer::sum);
                }
            }

            maxFlow += pathFlow;
        }
        return maxFlow;
    }

    public ArrayList<Edge> getMinCutEdges(Node source) {
        Set<Node> reachable = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();

        queue.add(source);
        reachable.add(source);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            for (Map.Entry<Node, Integer> neighborEntry : residualGraph.getOrDefault(current, new HashMap<>()).entrySet()) {
                Node neighbor = neighborEntry.getKey();
                int residualCapacity = neighborEntry.getValue();

                if (residualCapacity > 0 && !reachable.contains(neighbor)) {
                    reachable.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        ArrayList<Edge> minCutEdges = new ArrayList<>();
        for (Edge edge : graph.edges) {
            boolean startReachable = reachable.contains(edge.startNode);
            boolean endReachable = reachable.contains(edge.endNode);

            if (startReachable && !endReachable) {
                minCutEdges.add(edge);
            }
        }
        return minCutEdges;
    }
}