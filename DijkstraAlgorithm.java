import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

class Edge {
    Vertex u, v;
    int weight;

    public Edge(Vertex u, Vertex v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }
}

class Vertex implements Comparable<Vertex> {
    int data;
    int distance;
    Vertex predecessor;
    List<Edge> adjacencies;

    public Vertex(int data) {
        this.data = data;
        this.distance = Integer.MAX_VALUE;
        this.predecessor = null;
        this.adjacencies = new ArrayList<>();
    }

    public int compareTo(Vertex other) {
        return Integer.compare(this.distance, other.distance);
    }
}

class Graph {
    List<Vertex> vertices;

    public Graph(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void addEdge(Vertex u, Vertex v, int weight) {
        Edge e = new Edge(u, v, weight);
        u.adjacencies.add(e);
    }
}

public class DijkstraAlgorithm {

    public static void initializeSingleSource(Graph graph, Vertex source) {
        for (Vertex v : graph.vertices) {
            v.distance = Integer.MAX_VALUE;
            v.predecessor = null;
        }
        source.distance = 0;
    }

    public static void relax(Vertex u, Vertex v, int weight) {
        if (v.distance > u.distance + weight) {
            v.distance = u.distance + weight;
            v.predecessor = u;
        }
    }

    public static List<Vertex> dijkstra(Graph graph, Vertex source) {
        initializeSingleSource(graph, source);
        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        List<Vertex> visited = new ArrayList<>();

        queue.add(source);

        while (!queue.isEmpty()) {
            Vertex u = queue.poll();
            visited.add(u);

            for (Edge e : u.adjacencies) {
                Vertex v = e.v;
                relax(u, v, e.weight);
                if (!visited.contains(v) && !queue.contains(v)) {
                    queue.add(v);
                }
            }
        }
        return visited;
    }

    public static List<Integer> getShortestPathTo(Vertex target) {
        List<Integer> path = new ArrayList<>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.predecessor) {
            path.add(0, vertex.data);
        }
        return path;
    }

    public static void main(String[] args) {
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            vertices.add(new Vertex(i));
        }

        Graph graph = new Graph(vertices);
        graph.addEdge(vertices.get(0), vertices.get(1), 10);
        graph.addEdge(vertices.get(0), vertices.get(3), 5);
        graph.addEdge(vertices.get(1), vertices.get(2), 1);
        graph.addEdge(vertices.get(1), vertices.get(3), 2);
        graph.addEdge(vertices.get(2), vertices.get(4), 4);
        graph.addEdge(vertices.get(3), vertices.get(1), 3);
        graph.addEdge(vertices.get(3), vertices.get(2), 9);
        graph.addEdge(vertices.get(3), vertices.get(4), 2);
        graph.addEdge(vertices.get(4), vertices.get(0), 7);
        graph.addEdge(vertices.get(4), vertices.get(2), 6);

        List<Vertex> S = dijkstra(graph, vertices.get(0));
        System.out.println("Vertex | Dist | Path");
        System.out.println("---------------");
        for (Vertex v : S) {
            List<Integer> path = getShortestPathTo(v);
            System.out.println(" " + v.data + "     | " + v.distance + "    | " + String.join("->", path.stream().map(Object::toString).collect(java.util.stream.Collectors.toList())));
        }
    }
}
