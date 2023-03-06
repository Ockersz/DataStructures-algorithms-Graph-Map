import java.util.*;
class Edge {
    private String destination;
    private int distance;

    public Edge(String destination, int distance) {
        this.destination = destination;
        this.distance = distance;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}

class Graph {
    private Map<String, List<Edge>> graph;
    private Edge edge;

    public Graph() {
        this.graph = new HashMap<>();
    }

    public Graph(Edge edge) {
        this.edge = edge;
        this.graph = new HashMap<>();
    }


    public void addVertex(String vertex) {
        graph.put(vertex, new ArrayList<>());
    }

    public void addEdge(String source, String destination, int distance) {
        if (!graph.containsKey(source)) {
            addVertex(source);
        }
        if (!graph.containsKey(destination)) {
            addVertex(destination);
        }
        graph.get(source).add(new Edge(destination, distance));
        graph.get(destination).add(new Edge(source, distance)); // add edge in the opposite direction
    }

    public void printGraph() {
        for (String vertex : graph.keySet()) {
            List<Edge> edges = graph.get(vertex);
            StringBuilder sb = new StringBuilder();
            sb.append(vertex).append(": ");
            for (Edge edge : edges) {
                sb.append("(").append(edge.getDestination()).append(", ").append(edge.getDistance()).append(") ");
            }
            System.out.println(sb);
        }
    }

    public List<String> shortestPath(String startVertex, String endVertex) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(vertex -> distances.getOrDefault(vertex, Integer.MAX_VALUE)));

        for (String vertex : graph.keySet()) {
            if (vertex.equals(startVertex)) {
                distances.put(vertex, 0);
            } else {
                distances.put(vertex, Integer.MAX_VALUE);
            }
            previous.put(vertex, null);
            priorityQueue.offer(vertex);
        }

        while (!priorityQueue.isEmpty()) {
            String currentVertex = priorityQueue.poll();
            if (currentVertex.equals(endVertex)) {
                break;
            }
            for (Edge edge : graph.get(currentVertex)) {
                int distance = distances.get(currentVertex) + edge.getDistance();
                if (distance < distances.get(edge.getDestination())) {
                    distances.put(edge.getDestination(), distance);
                    previous.put(edge.getDestination(), currentVertex);
                    priorityQueue.remove(edge.getDestination());
                    priorityQueue.offer(edge.getDestination());
                }
            }
        }

        List<String> shortestPath = new ArrayList<>();
        String currentVertex = endVertex;
        while (currentVertex != null) {
            shortestPath.add(currentVertex);
            currentVertex = previous.get(currentVertex);
        }
        Collections.reverse(shortestPath);

        return shortestPath;
    }

    public int shortestDistance(String startVertex, String endVertex) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(vertex -> distances.getOrDefault(vertex, Integer.MAX_VALUE)));

        for (String vertex : graph.keySet()) {
            if (vertex.equals(startVertex)) {
                distances.put(vertex, 0);
            } else {
                distances.put(vertex, Integer.MAX_VALUE);
            }
            priorityQueue.offer(vertex);
        }

        while (!priorityQueue.isEmpty()) {
            String currentVertex = priorityQueue.poll();
            if (currentVertex.equals(endVertex)) {
                break;
            }
            for (Edge edge : graph.get(currentVertex)) {
                int distance = distances.get(currentVertex) + edge.getDistance();
                if (distance < distances.get(edge.getDestination())) {
                    distances.put(edge.getDestination(), distance);
                    priorityQueue.remove(edge.getDestination());
                    priorityQueue.offer(edge.getDestination());
                }
            }
        }

        return distances.getOrDefault(endVertex, -1); // Return -1 if endVertex is unreachable
    }

    public Map<List<String>, Integer> getAllPaths(String startVertex, String endVertex) {
        Map<List<String>, Integer> paths = new HashMap<>();
        List<String> visited = new ArrayList<>();
        visited.add(startVertex);
        getAllPathsUtil(startVertex, endVertex, visited, paths, 0);
        return paths;
    }

    private void getAllPathsUtil(String currentVertex, String endVertex, List<String> visited,
                                 Map<List<String>, Integer> paths, int distance) {
        if (currentVertex.equals(endVertex)) {
            paths.put(new ArrayList<>(visited), distance);
            return;
        }
        for (Edge edge : graph.get(currentVertex)) {
            String destination = edge.getDestination();
            int edgeDistance = edge.getDistance();
            if (!visited.contains(destination)) {
                visited.add(destination);
                getAllPathsUtil(destination, endVertex, visited, paths, distance + edgeDistance);
                visited.remove(destination);
            }
        }
    }


}

class SriLankaCityDistanceCalculator{
    public static void main(String[] args) {
        Graph graph = new Graph();

        graph.addVertex("Colombo");
        graph.addVertex("Kandy");
        graph.addVertex("Jaffna");
        graph.addVertex("Galle");
        graph.addVertex("Trincomalee");
        graph.addVertex("Horana");
        graph.addVertex("Panadura");


        graph.addEdge("Colombo", "Kandy", 150);
        graph.addEdge("Colombo", "Galle", 200);
        graph.addEdge("Colombo", "Horana", 40);
        graph.addEdge("Colombo", "Panadura", 100);
        graph.addEdge("Horana","Panadura", 40);

        System.out.println(graph.shortestPath("Panadura", "Colombo"));
        System.out.println(graph.shortestDistance("Panadura","Colombo"));
        Map<List<String>, Integer> allPaths = graph.getAllPaths("Colombo", "Panadura");
        for (Map.Entry<List<String>, Integer> entry : allPaths.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
