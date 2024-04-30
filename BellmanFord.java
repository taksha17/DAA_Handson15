import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Edge {
    Vertex u, v;
    int w;

    public Edge(Vertex u, Vertex v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}

class Vertex {
    int data;
    double d;
    Vertex p;

    public Vertex(int data) {
        this.data = data;
        this.d = Double.POSITIVE_INFINITY;
        this.p = null;
    }
}

class Graph {
    List<Vertex> vertices;
    Map<Vertex, List<Vertex>> adj;
    Map<String, Integer> w;

    public Graph(List<Vertex> vertices) {
        this.vertices = vertices;
        this.adj = new HashMap<>();
        this.w = new HashMap<>();
        for (Vertex v : vertices) {
            this.adj.put(v, new java.util.ArrayList<>());
        }
    }

    public void addEdge(Vertex u, Vertex v, int weight) {
        this.adj.get(u).add(v);
        this.w.put(u.data + "," + v.data, weight);
    }

    public void printGraph() {
        System.out.println("\n--- Adjacency List ---");
        for (Vertex v : this.adj.keySet()) {
            System.out.print(v.data + ": ");
            for (Vertex j : this.adj.get(v)) {
                System.out.print(j.data + " ");
            }
            System.out.println();
        }
        System.out.println("--- End of Adjacency List ---\n");
    }
}

public class BellmanFord {
    public static void initializeSingleSource(Graph graph, Vertex s) {
        for (Vertex v : graph.vertices) {
            v.d = Double.POSITIVE_INFINITY;
            v.p = null;
        }
        s.d = 0;
    }

    public static void relax(Vertex u, Vertex v, int w) {
        if (v.d > u.d + w) {
            v.d = u.d + w;
            v.p = u;
        }
    }

    public static boolean bellmanFord(Graph graph, Vertex s) {
        initializeSingleSource(graph, s);
        for (int i = 0; i < graph.vertices.size() - 1; i++) {
            for (Edge e : graph.w.keySet().stream().map(key -> {
                String[] parts = key.split(",");
                Vertex u = graph.vertices.get(Integer.parseInt(parts[0]));
                Vertex v = graph.vertices.get(Integer.parseInt(parts[1]));
                return new Edge(u, v, graph.w.get(key));
            }).collect(java.util.stream.Collectors.toList())) {
                relax(e.u, e.v, e.w);
            }
        }
        for (Edge e : graph.w.keySet().stream().map(key -> {
            String[] parts = key.split(",");
            Vertex u = graph.vertices.get(Integer.parseInt(parts[0]));
            Vertex v = graph.vertices.get(Integer.parseInt(parts[1]));
            return new Edge(u, v, graph.w.get(key));
        }).collect(java.util.stream.Collectors.toList())) {
            if (e.v.d > e.u.d + e.w) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        List<Vertex> vertices = Arrays.asList(new Vertex(0), new Vertex(1), new Vertex(2), new Vertex(3), new Vertex(4));
        Graph graph = new Graph(vertices);
        graph.addEdge(vertices.get(0), vertices.get(1), 6);
        graph.addEdge(vertices.get(0), vertices.get(3), 7);
        graph.addEdge(vertices.get(1), vertices.get(2), 5);
        graph.addEdge(vertices.get(1), vertices.get(3), 8);
        graph.addEdge(vertices.get(1), vertices.get(4), -4);
        graph.addEdge(vertices.get(2), vertices.get(1), -2);
        graph.addEdge(vertices.get(3), vertices.get(2), -3);
        graph.addEdge(vertices.get(3), vertices.get(4), 9);
        graph.addEdge(vertices.get(4), vertices.get(0), 2);
        graph.addEdge(vertices.get(4), vertices.get(2), 7);

        graph.printGraph();
        System.out.println("Successful execution of Bellman-Ford: " + bellmanFord(graph, vertices.get(0)));
    }
}
