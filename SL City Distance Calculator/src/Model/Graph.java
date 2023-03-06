package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class Graph {

    private Map<String, List<Edge>> graph;
    private Edge edge;

    public Graph() {

        try {
            this.graph = new HashMap<>();

            Statement statment1 = DatabaseConnection.getConnection().createStatement();
            ResultSet rst1 = statment1.executeQuery("Select v_name from vertex");

            while (rst1.next()) {
                this.addVertex(rst1.getString(1));
            }

            statment1.clearBatch();

            rst1 = statment1.executeQuery("Select e_source, e_destination, e_distance from edge");

            while (rst1.next()) {
                this.addEdge(rst1.getString(1), rst1.getString(2), rst1.getInt(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

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

    public List<StringBuilder> printGraph(String city) {
        List<StringBuilder> allpaths = new ArrayList<>();
        List<String> visited = new ArrayList<>();
        StringBuilder path = new StringBuilder();
        path.append(city);
        printPaths(city, visited, path, allpaths);
        return allpaths;
    }

    private void printPaths(String city, List<String> visited, StringBuilder path, List<StringBuilder> allpaths) {
        visited.add(city);
        List<Edge> edges = graph.get(city);
        for (Edge edge : edges) {
            String destination = edge.getDestination();
            if (!visited.contains(destination)) {
                StringBuilder newPath = new StringBuilder(path.toString());
                newPath.append(" -> ").append(destination).append(" (").append(edge.getDistance()).append(" km)");
                allpaths.add(newPath);
                printPaths(destination, visited, newPath, allpaths);
            }
        }
        visited.remove(city);
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

    public List<String> getVertices() {
        return new ArrayList<>(graph.keySet());
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
